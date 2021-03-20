/*
 * ZigmaData :: java database frontend
 * Copyright (C) 2019, 2020 : Ravi Shankar ** SQLeonardo :: java database frontend
 * Copyright (C) 2004 nickyb@users.sourceforge.net   
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package com.passion.querybuilder;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.passion.common.gui.BorderLayoutPanel;
import com.passion.common.jdbc.ConnectionHandler;
import com.passion.common.util.I18n;
import com.passion.common.util.OracleDefaultUsers;
import com.passion.querybuilder.beans.Entity;
import com.passion.querybuilder.beans.Tag;
import com.passion.querybuilder.dnd.EntityTransferHandler;
import com.passion.querybuilder.syntax.QueryTokens;



public class ViewObjects extends BorderLayoutPanel implements ItemListener
{
	// to ignore default schemas
		private final String IGNORE = "^(?!sys|jmx|admin|app|sql|null|jdbc|metadata|metastore|runtime|information_|performance_).*$";

	public final static String ALL_TABLE_TYPES = "All";
	private QueryBuilder builder;

	private JList jListObjects;
	public JComboBox jComboBoxSchemas;
	private JComboBox jComboBoxTypes;

	ViewObjects(QueryBuilder builder)
	{
		this.builder = builder;
		initComponents();
	}

	private void initComponents()
	{
		jListObjects = new JList();
		jListObjects.setDragEnabled(true);

		jListObjects.setTransferHandler(new EntityTransferHandler());

		jListObjects.setCellRenderer(new ObjectsListCellRenderer());
		jListObjects.addMouseListener(new ClickHandler());

		JPanel pnlNorth = new JPanel(new GridLayout(0, 2));
		pnlNorth.add(jComboBoxSchemas = new JComboBox());
		jComboBoxSchemas.setToolTipText(I18n.getString("querybuilder.tooltip.schemaFilter", "schema filter"));
		pnlNorth.add(jComboBoxTypes = new JComboBox());
		jComboBoxTypes.setToolTipText(I18n.getString("querybuilder.tooltip.typeFilter", "type filter"));

		setComponentNorth(pnlNorth);
		setComponentCenter(new JScrollPane(jListObjects));
	}

	void onConnectionChanged() throws SQLException
	{
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		jListObjects.setListData(new Vector());

		jComboBoxSchemas.removeItemListener(this);
		jComboBoxTypes.removeItemListener(this);

		jComboBoxSchemas.removeAllItems();
		jComboBoxTypes.removeAllItems();
       //Ram's changes
		jComboBoxSchemas.setEnabled(true);
		jComboBoxTypes.setEnabled(true);

		if (builder.getConnection() != null)
		{
			DatabaseMetaData dbmd = builder.getConnection().getMetaData();
			ResultSet rsTypes = dbmd.getTableTypes();
			while (rsTypes.next())
			{
				String type = rsTypes.getString(1).trim();
				boolean added = false;

				for (int i = 0; !added && i < jComboBoxTypes.getItemCount(); i++)
				{
					Tag t = (Tag)jComboBoxTypes.getItemAt(i);
					added = type.equals(t.getValue().toString());
				}
				if (!added)
				{
					jComboBoxTypes.addItem(new Tag(type, I18n.getString("querybuilder.objetctype." + type, "" + type)));
				}
			}
			rsTypes.close();
			jComboBoxTypes.addItem(new Tag(ALL_TABLE_TYPES, I18n.getString("querybuilder.objetctype.all", "All object types")));

			if (ViewObjects.jdbcUseSchema(dbmd))
			{
				ResultSet rsSchemas = dbmd.getSchemas();
				while (rsSchemas.next()){
					//Ram's changes
					String schema = rsSchemas.getString(1).trim();
				//	System.out.println("rsSchemas.getString(1).trim()::"+ schema );
					
					if (schema.matches(IGNORE) && !(OracleDefaultUsers.getDefault_users().contains(schema))) {
						
						String driverName = ConnectionHandler.driverCat.get(schema);

						if (null != driverName && driverName.toLowerCase().contains("presto")) {
							
							String catalogName = ConnectionHandler.schemaCat.get(schema);
							schema = catalogName + "." + schema;
						}
						jComboBoxSchemas.addItem(schema);
					}
					
				}
				rsSchemas.close();
			}

			jComboBoxSchemas.setSelectedItem(null);
			jComboBoxTypes.setSelectedItem(null);
		}

		jComboBoxSchemas.setEnabled(jComboBoxSchemas.getItemCount() > 0);
		jComboBoxTypes.setEnabled(jComboBoxTypes.getItemCount() > 0);

		jComboBoxSchemas.addItemListener(this);
		jComboBoxTypes.addItemListener(this);

//		if (jComboBoxSchemas.getItemCount() > 0 && QueryBuilder.loadObjectsAtOnce)
//			jComboBoxSchemas.setSelectedIndex(0);

		if (jComboBoxTypes.getItemCount() > 0 && QueryBuilder.loadObjectsAtOnce)
		{
			Tag t = new Tag("TABLE", I18n.getString("querybuilder.objetctype.TABLE", "TABLE"));
			for(int i=0; i<jComboBoxTypes.getItemCount(); i++)
			{
				if(jComboBoxTypes.getItemAt(i).toString().equals(t.toString()))
				{
					jComboBoxTypes.setSelectedIndex(i);
					break;
				}
			}
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	void onModelChanged()
	{
		String schema = builder.getQueryModel().getSchema();
		jComboBoxSchemas.setEnabled(jComboBoxSchemas.isEnabled() && schema == null);
		jComboBoxSchemas.setSelectedItem(schema);
	}

	public void itemStateChanged(ItemEvent ie)
	{
		Object schema = jComboBoxSchemas.getSelectedItem();
		Tag t = (Tag)jComboBoxTypes.getSelectedItem();
	   System.out.println("schema :# " +schema );
		Object type = null;
		if (t != null)
			type = t.getValue();

		if (type != null && (schema != null || !jComboBoxSchemas.isEnabled()))
		{
			//System.out.println("schema :# " +schema + "t" +t.getValue());
			
			this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			Vector vObjects = new Vector();

			String[] tableType;
			if (type.toString().equals(ALL_TABLE_TYPES))
				tableType = null;
			else
				tableType = new String[] { type.toString()};

			try
			{
				DatabaseMetaData dbmd = builder.getConnection().getMetaData();
				ResultSet rsTables = null;

				// Ram's changes
				if (null != schema) {

					String[] table = schema.toString().split("\\.");
					String tab = null;

					if (table.length == 2) {
						tab = table[1].trim();

					} else if (table.length == 1) {
						tab = table[0];
					}

					String catalog = tab == null ? null : ConnectionHandler.schemaCat.get(tab);
					rsTables = dbmd.getTables(catalog, (tab == null ? null : tab.toString()), "%", tableType);

				} else {

				}

				// System.out.println("rsTables" + rsTables.getFetchSize());
				if (rsTables != null) {
					/*
					 * 06/02/2007 (Nicky) if schema is as model level don't
					 * display into diagram-entity
					 */
					if (builder.getQueryModel().getSchema() != null)
						schema = null;

					while (rsTables.next()) {

						Entity entity = new Entity((schema == null ? null : schema.toString()),
								rsTables.getString(3).trim());
						vObjects.addElement(entity);
					}
					rsTables.close();
				} else {
					System.out.println("NULLLLLLLLLLLLLLL");
				}
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
			finally
			{
				jListObjects.setListData(vObjects);
				this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}

	public static boolean jdbcUseSchema(DatabaseMetaData dbmd) throws SQLException
	{
// #293 Query builder: support adding csvjdbc tables without schema 
//		String term = dbmd.getSchemaTerm();
//		return term != null && term.length() > 0;
		Boolean Schem = dbmd.supportsSchemasInTableDefinitions();
		return Schem ;
	}

	private class ClickHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			if (e.getClickCount() == 2)
			{
				Entity selectedItem = (Entity)jListObjects.getSelectedValue();
				QueryTokens.Table token = new QueryTokens.Table(selectedItem.getSchema(), selectedItem.getEntityName());
				DiagramLoader.run(DiagramLoader.DEFAULT, builder, token, true);
			}
		}
	}
}
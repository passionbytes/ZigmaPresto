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

package com.passion.environment.ctrl.explorer;

import java.sql.ResultSet;

import javax.swing.tree.DefaultMutableTreeNode;

import com.passion.common.jdbc.ConnectionAssistant;
import com.passion.environment.Application;
import com.passion.environment.ctrl.MetadataExplorer;
import com.passion.environment.ctrl.define.ColumnsChooser;
import com.passion.querybuilder.QueryBuilder;


public class ViewMetadata extends AbstractViewObjects
{
	private String keycah = null;	
	private String schema = null;	

	protected String getHandlerKey()
	{
		if(ConnectionAssistant.hasHandler(keycah))
		{
			QueryBuilder.identifierQuoteString = ConnectionAssistant.getHandler(keycah).getObject("$identifierQuoteString").toString();
			QueryBuilder.maxColumnNameLength = ((Integer)ConnectionAssistant.getHandler(keycah).getObject("$maxColumnNameLength")).intValue();
		}
		
		return keycah;
	}

	protected String getTableSchema()
	{
		
		return schema;
	}

	protected void list(DefaultMutableTreeNode node)
	{
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
		if(parent.getUserObject() instanceof UoDatasource)
		{
			UoDatasource uoDs = (UoDatasource)parent.getUserObject();
			schema = uoDs.schema;
			System.out.println("ViewMetadata schema:"
					+ "$: "+ schema);
			if(uoDs.color!=null){
				setBackgroundColor(uoDs.color);
			}
		}
		else
		{
			schema	= parent.getUserObject().toString();
		//	System.out.println("ViewMetadata schema:#: "+ schema);
			parent	= (DefaultMutableTreeNode)parent.getParent();
			if(parent.getUserObject() instanceof UoDatasource)
			{
				UoDatasource uoDs = (UoDatasource)parent.getUserObject();
				if(uoDs.color!=null){
					setBackgroundColor(uoDs.color);
				}
			}
		}
		keycah = ((UoDatasource)parent.getUserObject()).getKey();

		String[] tableType;
		if(node.getUserObject().toString().equals(MetadataExplorer.ALL_TABLE_TYPES_LABEL))
			tableType = null;
		else
			tableType = new String[]{node.getUserObject().toString()};

		try
		{
			String dvname = node.getPath()[1].toString();
			String catalog = schema == null ? null : this.getConnection().getCatalog();
			if(schema == null){
				catalog = parent.toString();
			}
			ResultSet rs = this.getConnection().getMetaData().getTables(catalog,schema,"%",tableType);
			ColumnsChooser.list(dvname,"table types",this,rs);
		}
		catch(Exception e)
		{
			Application.println(e,true);
		}
	}
}
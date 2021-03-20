/*
 *
 * Modified by ZigmaDataQB Visual Query Builder :: java database frontend with join definitions
 * Copyright (C) 2012 anudeepgade@users.sourceforge.net
 * 
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

package com.passion.environment.mdi;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.passion.common.util.I18n;
import com.passion.common.util.UriHelper;
import com.passion.environment.Application;
import com.passion.environment.Preferences;


public class MDIMenubar extends JMenuBar implements InternalFrameListener
{
	static final int IDX_ACTIONS	= 1;
	static final int IDX_TOOLS		= 2;
	static final int IDX_WINDOW		= 3;

	public History history;
	private WindowGroup winGroup;
	public JMenu recentQueryMenu;
	public static final String RECENT_QUERIES = "RECENT_QUERIES";
	public static final String RECENT_QUERY_SEPARATOR = "||";

	public MDIMenubar()
	{
		history = new History();
		winGroup = new WindowGroup();
    	
		JMenu menu = add(I18n.getString("application.menu.file","File"));
		menu.setMnemonic('f');
		menu.add(createItem(MDIActions.ACTION_NEW_QUERY));
		menu.add(createItem(MDIActions.ACTION_LOAD_QUERY));

		recentQueryMenu = add(I18n.getString("application.menu.recentQueries","Recent queries"));
		MDIActions.LoadGivenQuery action = new MDIActions.LoadGivenQuery();  

		if(Preferences.containsKey(RECENT_QUERIES)){
			String value = Preferences.getString(RECENT_QUERIES);
			if(value.length()>0){
				if(value.contains(RECENT_QUERY_SEPARATOR)){
					String[] queryNames = value.split("\\|\\|");
					for(int i=0;i<queryNames.length;i++){
						String fileName =queryNames[i];
						File f = new File(fileName);
						if(f.exists()){
							JMenuItem item = new JMenuItem(fileName);
							item.setActionCommand(fileName);
							item.addActionListener(action);
							recentQueryMenu.add(item);
						}
					}
				}else{
					File f = new File(value);
					if(f.exists()){
						JMenuItem item = new JMenuItem(value);
						item.setActionCommand(value);
						item.addActionListener(action);
						recentQueryMenu.add(item);
					}
				}
			}
		}
		menu.add(recentQueryMenu);

		menu.addSeparator();
		menu.add(createItem(new MDIActions.Dummy(I18n.getString("application.menu.print","Print")))).setEnabled(false);
		menu.addSeparator();
		menu.add(createItem(MDIActions.ACTION_EXIT));
        
		menu = add(I18n.getString("application.menu.actions","Actions"));
		menu.setMnemonic('a');
		menu.add("<empty>").setEnabled(false);
        
		menu = add(I18n.getString("application.menu.tools","Tools"));
		menu.setMnemonic('t');
		menu.add(winGroup.add(MDIActions.ACTION_MDI_SHOW_EXPLORER));
		menu.add(winGroup.add(MDIActions.ACTION_MDI_SHOW_EDITOR));
		//menu.add(winGroup.add(MDIActions.ACTION_MDI_SHOW_COMPARER));
		menu.add(winGroup.add(MDIActions.ACTION_MDI_SHOW_HISTORY));
		//menu.add(winGroup.add(MDIActions.ACTION_MDI_SHOW_DATA_COMPARER));
		menu.addSeparator();
		menu.add(createItem(MDIActions.ACTION_SHOW_CONTENT));
		menu.add(createItem(MDIActions.ACTION_SHOW_DEFINITION));
		menu.addSeparator();
		//menu.add(createItem(MDIActions.ACTION_MDI_WRANGLE));
		//menu.addSeparator();
		//menu.add(createItem(MDIActions.ACTION_MDI_VISUALIZE));
		menu.addSeparator();
		menu.add(createItem(MDIActions.ACTION_SHOW_PREFERENCES));
        
        menu = add(I18n.getString("application.menu.window","Window"));
		menu.setMnemonic('w');
        menu.add(createItem(MDIActions.ACTION_MDI_CASCADE));
		menu.add(createItem(MDIActions.ACTION_MDI_TILEH));
        menu.addSeparator();
		menu.add(createItem(MDIActions.ACTION_MDI_CLOSE_ALL));
		menu.addSeparator();
		menu.add("<empty>").setEnabled(false);
		
        menu = add(I18n.getString("application.menu.help","Help"));
        menu.setMnemonic('h');
		menu.add(createItem(MDIActions.ACTION_SHORTCUTS));
		menu.addSeparator();
		menu.add(createItem(MDIActions.ACTION_HOWTOUSE));
		menu.addSeparator();
		menu.add(createItem(MDIActions.ACTION_ABOUT));

		//addLink(I18n.getString("application.menu.donate","VISIT US"), Application.PASSION_URL);
		
		//Ravi Added
		JLabel jLabel1 = new JLabel();
		add(Box.createHorizontalGlue());
		ImageIcon ic = new javax.swing.ImageIcon(getClass().getResource("/images/vqb.png"));
		jLabel1.setIcon(scaleImage(ic,150,150)); // NOI18N
		add(jLabel1);
		JLabel jLabel2 = new JLabel("   ");
		add(jLabel2);


	}
	public ImageIcon scaleImage(ImageIcon icon, int w, int h)
    {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if(icon.getIconWidth() > w)
        {
          nw = w;
          nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if(nh > h)
        {
          nh = h;
          nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
    }

	private void addLink(String linkName,String url){
		JEditorPane content = new JEditorPane();
		content.setContentType("text/html");
		content.setEditable(false);
		content.setToolTipText(url);
		String text = "<html><a href=\""+url+"\">"+linkName+"</a></html>";
		content.setText(text);
		
		content.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		final int fontSize = Preferences.getScaledRowHeight(content.getFont().getSize());
		content.setFont(new Font(content.getFont().getFamily(), Font.BOLD, fontSize));
		
		content.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						UriHelper.openURI(e.getURL().toURI());
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		add(content);
	}
	
	public void addVersionLink(){
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				//String version = getLatestVersionName();
				String version = null;
				if(version!=null){
					addLink(I18n.getString("application.menu.newversion","New version available : ")
							+ version
							,Application.SF_WEB);
				}
			}
		});
	}

	private String getLatestVersionName(){
		
		
		return "2021-03-Prestocon";
	}


	public void addMenuItemAtFirst(String text){
		MDIActions.LoadGivenQuery action = new MDIActions.LoadGivenQuery(); 
		for(int i = 0; i<recentQueryMenu.getItemCount();i++){
			JMenuItem subMenuItem = recentQueryMenu.getItem(i);
			if(text.equals(subMenuItem.getText())){
				recentQueryMenu.remove(subMenuItem);
				break;
			}
		}
		JMenuItem item = new JMenuItem(text);
		item.setActionCommand(text);
		item.addActionListener(action);
		recentQueryMenu.insert(item,0);
	}
	
	private JMenu add(String text)
	{
		return add(new JMenu(text));
	}

	private static JMenuItem createItem(String actionkey)
	{
		return createItem(Application.window.getAction(actionkey));
	}
    
    public static JMenuItem createItem(Action a)
    {
        JMenuItem item = new JMenuItem(a);
        item.setToolTipText(null);
        item.setIcon(null);
        
     if(null != a.getValue(Action.ACCELERATOR_KEY))
        	item.setAccelerator((KeyStroke)a.getValue(Action.ACCELERATOR_KEY));

        return item;
    }
    public static JMenuItem createItemWithoutAccelerator(Action a)
    {
        JMenuItem item = new JMenuItem(a);
        item.setToolTipText(null);
        item.setIcon(null);
        item.setAccelerator(null);

        return item;
    }
    
    Action getActionForShow(String clientName)
    {
    	Action action = null;
    	
        for(int i=6;i<this.getMenu(IDX_WINDOW).getMenuComponentCount();i++)
        {
            JMenuItem item = (JMenuItem)this.getMenu(IDX_WINDOW).getMenuComponent(i);
            if(((ActionShowMDIClient)item.getAction()).getMDIClientName().equals(clientName))
            {
            	action = item.getAction();
            	break;
            }
        }
        
        if(action == null && clientName.equals(ClientMetadataExplorer.DEFAULT_TITLE))
        	action = Application.window.getAction(MDIActions.ACTION_MDI_SHOW_EXPLORER);
        
        if(action == null && clientName.equals(ClientCommandEditor.DEFAULT_TITLE))
        	action = Application.window.getAction(MDIActions.ACTION_MDI_SHOW_EDITOR);
        
        if(action == null && clientName.equals(ClientSchemaComparer.DEFAULT_TITLE))
        	action = Application.window.getAction(MDIActions.ACTION_MDI_SHOW_COMPARER);
        
        return action;
    }
    
    public void internalFrameActivated(InternalFrameEvent ife)
    {
        this.getMenu(IDX_ACTIONS).getMenuComponent(0).setVisible(true);
        
        while(this.getMenu(IDX_ACTIONS).getMenuComponentCount()>1)
            this.getMenu(IDX_ACTIONS).remove(1);
        
        MDIClient client = (MDIClient)ife.getInternalFrame(); 
        history.onSelectionChanged(client);
        
        if(client.getMenuActions()!=null)
        {
	        for(int i=0; i<client.getMenuActions().length; i++)
	        {
	        	if(client.getMenuActions()[i]==null)
					this.getMenu(IDX_ACTIONS).addSeparator();
				else
					this.getMenu(IDX_ACTIONS).add(client.getMenuActions()[i]);
	        }
	
	        this.getMenu(IDX_ACTIONS).getMenuComponent(0).setVisible(false);
        }

		for(int i=0;i<this.getMenu(IDX_TOOLS).getMenuComponentCount();i++)
		{
			if(this.getMenu(IDX_TOOLS).getMenuComponent(i) instanceof JRadioButtonMenuItem)
			{
				JRadioButtonMenuItem item = (JRadioButtonMenuItem)this.getMenu(IDX_TOOLS).getMenuComponent(i);
				if(((MDIActions.AbstractShow)item.getAction()).getMDIClientName().equals(client.getName()))
				{
					item.setSelected(true);
				}
			}
		}
        
		for(int i=0;i<this.getMenu(IDX_WINDOW).getMenuComponentCount();i++)
		{
			if(this.getMenu(IDX_WINDOW).getMenuComponent(i) instanceof JRadioButtonMenuItem)
			{
				JRadioButtonMenuItem item = (JRadioButtonMenuItem)this.getMenu(IDX_WINDOW).getMenuComponent(i);
				if(((MDIActions.AbstractShow)item.getAction()).getMDIClientName().equals(client.getName()))
				{
					item.setSelected(true);
				}
			}
		}
		Application.window.toolbar.onMDIClientActivated(client);
    }
    
    public void internalFrameClosed(InternalFrameEvent ife)
    {
        MDIClient client = (MDIClient)ife.getInternalFrame();
        history.remove(client);
        
        for(int i=6;i<this.getMenu(IDX_WINDOW).getMenuComponentCount();i++)
        {
            JMenuItem item = (JMenuItem)this.getMenu(IDX_WINDOW).getMenuComponent(i);
            if(((ActionShowMDIClient)item.getAction()).getMDIClientName().equals(client.getName()))
            {
            	this.getMenu(IDX_WINDOW).remove(i);
                break;
            }
        }
        this.getMenu(IDX_WINDOW).getMenuComponent(5).setVisible(this.getMenu(IDX_WINDOW).getMenuComponentCount()==6);
        
        
        JInternalFrame nextFrame =  Application.window.getDesktopTopFrame();
        if(nextFrame!=null){
     		Application.window.toolbar.onMDIClientActivated((MDIClient)nextFrame);
        }
        
    }
    
    public void internalFrameOpened(InternalFrameEvent ife)
    {
        MDIClient client = (MDIClient)ife.getInternalFrame();
        history.add(client);
        
        //if(!client.isClosable()) return;
        
        this.getMenu(IDX_WINDOW).add(winGroup.add(client));
        this.getMenu(IDX_WINDOW).getMenuComponent(5).setVisible(false);
    }
    
    public void internalFrameClosing(InternalFrameEvent ife){}
    public void internalFrameDeactivated(InternalFrameEvent ife){}
    public void internalFrameDeiconified(InternalFrameEvent ife){}
    public void internalFrameIconified(InternalFrameEvent ife){}
    
    /* generic action */
	private class ActionShowMDIClient extends MDIActions.AbstractShow
	{
		private String clientname;
        
		ActionShowMDIClient(MDIClient client)
		{
			clientname = client.getName();
			setText(client.getTitle());
		}
        
		public String getMDIClientName(){return clientname;}
	}
	
	private class WindowGroup extends ButtonGroup
	{
		JRadioButtonMenuItem add(MDIClient client)
		{
			return createItem(new ActionShowMDIClient(client));			
		}
		
		JRadioButtonMenuItem add(String actionkey)
		{
			return createItem(Application.window.getAction(actionkey));			
		}
		
		private JRadioButtonMenuItem createItem(Action a)
		{
			JRadioButtonMenuItem item = new JRadioButtonMenuItem(a);
			item.setToolTipText(null);
			item.setIcon(null);
			add(item);
        
			if(a.getValue(Action.ACCELERATOR_KEY)!=null)
				item.setAccelerator((KeyStroke)a.getValue(Action.ACCELERATOR_KEY));

			return item;
		}
	}
	
	public class History
	{
	    private int current = -1;
	    private boolean enabled = false;
	    private Vector sequence = new Vector();
	    
	    void add(MDIClient client)
	    {
	    	enabled = false;
	    	// simpler add new ones at the end
			sequence.add(client);
	    	final int total = sequence.size();
			current= total>0?total-1:current; 
	    }
	    
	    void previous()
	    {
	    	enabled = false;
	    	if(current > 0) --current;
			MDIClient back = (MDIClient)sequence.elementAt(current);
			Action action = Application.window.menubar.getActionForShow(back.getName());
			if(action!=null) action.actionPerformed(null);
	    }
	    
	    void enableSequence()
	    {
	    	enabled = true;
	    }
	    
	    void onSelectionChanged(MDIClient client)
	    {
//	    	if(enabled) add(client);
	    	current = sequence.indexOf(client);
	    	enableActions();
	    }
	    
	    void enableActions()
	    {
	    	Application.window.getAction(MDIActions.ACTION_MDI_GOBACK).setEnabled(current>0);
	    	Application.window.getAction(MDIActions.ACTION_MDI_GOFWD).setEnabled(current<sequence.size()-1);
	    	//Application.window.getAction(MDIActions.ACTION_MDI_WRANGLE).setEnabled(true);
	    	//Application.window.getAction(MDIActions.ACTION_MDI_VISUALIZE).setEnabled(true);
	    }
	    
	    void next()
	    {
	    	enabled = false;	    	
	    	MDIClient forward = (MDIClient)sequence.elementAt(++current);
			Action action = Application.window.menubar.getActionForShow(forward.getName());
			if(action!=null) action.actionPerformed(null);
	    }
	    
	    void remove(MDIClient client)
	    {
	    	enabled = false;
	    	for(int found=-1; (found=sequence.indexOf(client))!=-1;)
	    	{
	    		sequence.removeElementAt(found);
// Ticket #121 retrieve initial behaviour when closing a window
//	    		if(found==current && sequence.size()>0)
//	    			previous();
//	    		else if(found<current)
			if(found<current)
	    			current--;
	    	}
	    	enableActions();
	    }
	}
}
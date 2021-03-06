/*
 *
 * ZigmaDataQB Visual Query Builder :: java database frontend with join definitions
 * Copyright (C) 2012 anudeepgade@users.sourceforge.net
 * 
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

package com.passion.environment.ctrl.comparer.data;

import javax.swing.JScrollPane;

import com.passion.common.gui.AbstractDialogConfirm;
import com.passion.environment.Application;


public class DataComparerDialog extends AbstractDialogConfirm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataComparerDialogTable dataDialogTable;
	private DataComparerCriteriaDialogPane  owner;
	
	public DataComparerDialog(final DataComparerDialogTable.DATA_TYPE dataType,
			final DataComparerCriteriaDialogPane owner){
		super(Application.window,dataType.name().toLowerCase());
		this.owner = owner;
		this.dataDialogTable = new DataComparerDialogTable(dataType);
		getContentPane().add(new JScrollPane(dataDialogTable));
	}
	
	
	public DataComparerDialogTable.DATA_TYPE getDataType(){
		return dataDialogTable.getDataType();
	}
	
	public void addColumns(String[] columns){
		dataDialogTable.addColumns(columns);
	}

	protected boolean onConfirm(){
		owner.setText(dataDialogTable.getQueryTokensAsString());
		return true;
	}

	protected void onOpen(){
		
	}

}

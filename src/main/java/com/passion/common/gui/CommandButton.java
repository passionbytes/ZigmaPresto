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

package com.passion.common.gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;

import com.passion.environment.Preferences;

public class CommandButton extends JButton
{
	public final static Dimension CUSTOM_DIMENSION = Preferences.getScaledDimension(82,22);
    
	public CommandButton(Action a)
	{
	    super(a);
	    setAllSize();
	}
	
	public CommandButton(String text)
	{
	    super(text);
	    setAllSize();
	}
	
	public CommandButton(String text, ActionListener l)
	{
	    this(text);
	    addActionListener(l);
	}
	
	public void setAllSize(Dimension allSize)
	{
		Dimension size = this.getPreferredSize();
		
		size.height = allSize.height;
		size.width = size.width > allSize.width ? size.width : allSize.width;
		
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}
	
	private void setAllSize()
	{
		setAllSize(CUSTOM_DIMENSION);
	}
}
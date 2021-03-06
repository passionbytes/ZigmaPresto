/*
 * ZigmaData :: java database frontend
 * Copyright (C) 2019, 2020 : PassionBytes 
 * SQLeonardo :: java database frontend
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

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;

public abstract class AbstractMaskChooser extends JFileChooser
{
	public AbstractMaskChooser(int type, int mode, boolean showAllFiles)
	{
		setBorder(null);
		setDialogType(type);
		setFileSelectionMode(mode);
		setControlButtonsAreShown(false);
		setAcceptAllFileFilterUsed(showAllFiles);
	}
	
	protected void fireApproveSelection()
	{
		if(this.getUI() instanceof BasicFileChooserUI)
		{
			((BasicFileChooserUI)this.getUI()).getApproveSelectionAction().actionPerformed(null);
		}
	}
	
	public File getSelectedFile()
	{
		fireApproveSelection();
		return super.getSelectedFile();
	}
	
	public short getPerformType()
	{
		return ((AbstractFileFilter)getFileFilter()).getPerformType();
	}
	
	public static abstract class AbstractFileFilter extends FileFilter
	{
		String description;
		String[] extensions;
		
		public AbstractFileFilter(String description, String[] extensions)
		{
			this.description = description;
			this.extensions = extensions;
		}
		
		public boolean accept(File file)
		{
			if(file.isDirectory()) return true;
			
			for(int i=0; i<extensions.length; i++)
				if(file.getName().toLowerCase().endsWith(extensions[i].toLowerCase()))
					return true;

			return false;
		}
		
		public String getDescription()
		{
			return description;
		}
		
		public abstract short getPerformType();
	}
}
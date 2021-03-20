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

import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.passion.common.gui.AbstractDialogModal;
import com.passion.environment.Application;


public class DialogAbout extends AbstractDialogModal
{
	public DialogAbout()
	{
		super(Application.window, Application.PROGRAM + ".about [" + Application.WEB + "]");
		setResizable(false);
		
		JTextArea txt = new JTextArea();
		txt.setBorder(new CompoundBorder(LineBorder.createGrayLineBorder(), new EmptyBorder(2,2,2,2)));
		txt.setEditable(false);
		txt.setOpaque(false);
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
		
		txt.append("ZigmaDataQB Visual Query Builder for PrestoDB:: PrestoDB frontend\n");
		txt.append("Modifed from SQLLeo : https://sqleo.sourceforge.io/ ");
		txt.append("Version : "+Application.MAJOR + "." +Application.MINOR + "\n");
		txt.append("Copyright (C) "+Application.MAJOR + Application.AUTHOR + "\n");
		txt.append("Project website: "+Application.WEB+"\n");
		txt.append("\n");
		

		getContentPane().add(txt);
	}

	protected void onOpen()
	{
	}
}

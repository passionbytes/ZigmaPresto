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

package com.passion.querybuilder.syntax;

import com.passion.querybuilder.syntax.QueryTokens._TableReference;

public class DerivedTable extends SubQuery implements _TableReference
{
	private static long counter = 0;
	
	public final String getAlias()
	{
		return alias == null ? alias = "SUBQUERY_" + (++counter) : alias;
	}
	
	public String toString(boolean wrap, int offset)
	{
		return super.toString(wrap,offset) + SQLFormatter.SPACE + this.getAlias();
	}	
}

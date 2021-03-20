/*
 *
 * Modified by ZigmaDataDB Visual Query Builder :: java database frontend with join definitions
 * Copyright (C) 2012 anudeepgade@users.sourceforge.net
 * 
 * ZigmaData :: java database frontend
 * Copyright (C) 2019, 2020 ** SQLeonardo :: java database frontend
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

package com.passion.environment;

public interface _Version
{
	 public static final String PROGRAM = "PrestoDB-QueryBuilder";
	    public static final String AUTHOR = " RaviShankar";

	    public static final String MAJOR = "2021";
	    public static final String MINOR = "03-24";
	    public static final String SRC = "_git";
	    public static final String WEB = "https://prestodb.io";
	    public static final String SVN_BUILD_XML_FILE = "https://prestodb.io";
	    public static final String SF_WEB = "https://prestodb.io";
	    public static final String PASSION_URL = "https://prestodb.io";
	    //public static final String VERSION_TRACK = "http://www.google-analytics.com/collect?v=1&tid=UA-38580100-2&cid=555&t=pageview&dt=Version&dp=%2Fversion_"+MAJOR+"."+MINOR.replace("+","%2B")+SRC;

	    //unicode characters for (c) is \u00a9
	    public static String PASSION_IMAGE_WATERMARK = "\u00a9 PassionBytes " + MAJOR;
}
/*
 * ZigmaDataQB Visual Query Builder :: java database frontend with join definitions
 * Copyright (C) 2016 edinhojorge@users.sourceforge.net
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
package com.passion.common.jdbc.wrapper;

import java.sql.SQLException;
import java.util.ArrayList;

import com.passion.common.jdbc.interceptor.SqlCommandInterceptor;
import com.passion.common.util.I18n;

public abstract class AbstractWrapper {

	private ArrayList<SqlCommandInterceptor> sqlCommandInterceptorList = new ArrayList<SqlCommandInterceptor>();

	public AbstractWrapper(ArrayList<SqlCommandInterceptor> sqlCommandInterceptorList) {
		if (sqlCommandInterceptorList != null)
			this.sqlCommandInterceptorList = sqlCommandInterceptorList;
	}
	
	public AbstractWrapper() {

	}
	
	protected ArrayList<SqlCommandInterceptor> getSqlCommandInterceptorList() {
		return sqlCommandInterceptorList;
	}

	public void addStatementInterceptor(SqlCommandInterceptor interceptor){
		if (interceptor != null)
			sqlCommandInterceptorList.add(interceptor);
	}
		
	protected void isUpdatable(String sqlStatement) throws SQLException {
		if (sqlCommandInterceptorList.size() > 0){
			for (SqlCommandInterceptor statementInterceptor : sqlCommandInterceptorList) {
				if (statementInterceptor.allowUpdate(sqlStatement) == false)
					throw new SQLException(I18n.getString("application.message.readonly","ZigmaDataQB connection is readonly and do not permit this operation"));
			}
		}
	}

}

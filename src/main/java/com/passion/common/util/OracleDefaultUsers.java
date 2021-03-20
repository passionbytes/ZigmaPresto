/**
 * 
 */
package com.passion.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZigmaDataQB
 *
 */
public class OracleDefaultUsers {

	
	private static List<String> default_users;
	static {
		default_users = new ArrayList<String>();
		setDefault_users();

	}

	/**
	 * @return the default_users
	 */
	public static List<String> getDefault_users() {

		return default_users;
	}

	/**
	 * @param default_users
	 *            the default_users to set
	 */
	public final static void setDefault_users() {

		default_users.add("ANONYMOUS");
		default_users.add("CTXSYS");
		default_users.add("DBSNMP");
		default_users.add("EXFSYS");
		default_users.add("LBACSYS");
		default_users.add("MDSYS");
		default_users.add("MGMT_VIEW");
		default_users.add("OLAPSYS");
		default_users.add("OWBSYS");
		default_users.add("ORDPLUGINS");
		default_users.add("ORDSYS");
		default_users.add("OUTLN");
		default_users.add("SI_INFORMTN_SCHEMA");
		default_users.add("SYS");
		default_users.add("SYSMAN");
		default_users.add("SYSTEM");
		default_users.add("TSMSYS");
		default_users.add("WK_TEST");
		default_users.add("WKSYS");
		default_users.add("WKPROXY");
		default_users.add("WMSYS");
		default_users.add("XDB");
		default_users.add("APEX_050100");
		default_users.add("APEX_040000");
		default_users.add("APEX_INSTANCE_ADMIN_USER");
		default_users.add("APEX_LISTENER");
		default_users.add("APEX_PUBLIC_USER");
		default_users.add("APEX_REST_PUBLIC_USER");
		default_users.add("AUDSYS");
		default_users.add("DBSFWUSER");
		default_users.add("DBJSON");
		default_users.add("DIP");
		default_users.add("DVF");
		default_users.add("DVSYS");
		default_users.add("FLOWS_FILES");
		default_users.add("GGSYS");
		default_users.add("GSMUSER");
		default_users.add("GSMADMIN_INTERNAL");
		default_users.add("GSMCATUSER");
		default_users.add("HR");
		default_users.add("HRREST");
		default_users.add("MDDATA");
		default_users.add("MYDBA");
		default_users.add("OBE");
		default_users.add("OJVMSYS");
		default_users.add("ORDDATA");
		default_users.add("ORDS_METADATA");
		default_users.add("ORDS_PUBLIC_USER");
		default_users.add("PDBADMIN");
		default_users.add("REMOTE_SCHEDULER_AGENT");
		default_users.add("ORACLE_OCM");
		default_users.add("RESTFUL");
		default_users.add("SCOTT");
		default_users.add("SPATIAL_CSW_ADMIN_USR");
		default_users.add("XDBEXT");default_users.add("XDBPM");
		default_users.add("XFILES");
		default_users.add("XS$NULL");
		
	
	}

	/**
	 * @param default_users
	 *            the default_users to set
	 * @return
	 */

}

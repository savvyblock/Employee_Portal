package net.esc20.txeis.EmployeeAccess.domainobject;

public class TxeisPreference {
	
	private String prefname;
	private String prefvalue;
	private String preftyp ;
	
	public TxeisPreference(String pPrefname, String pPrefvalue) {
		
		prefname = pPrefname;
		prefvalue = pPrefvalue;
		//to get the type. 
		//for example, in database pref_name is defined as "ldap_url"
		//so the type is "ldap"
		int li_occur ;
		li_occur = pPrefname.indexOf('_');
		preftyp = pPrefname.substring(0,li_occur).toUpperCase();
	}
	
	public String getPrefname() {
		return prefname;
	}
	
	public String getPrefvalue() {
		return prefvalue;
	}

	public String getPreftyp() {
		return preftyp;
	}


}
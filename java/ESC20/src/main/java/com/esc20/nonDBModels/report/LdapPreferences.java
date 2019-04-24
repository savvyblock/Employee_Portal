package com.esc20.nonDBModels.report;

public class LdapPreferences {

	private String ldapUrl = null;
	private String ldapBase = null;
	private String ldapPwd = null;
	private String ldapUsr = null;
	private String ldapUserAttribute = null;
	private String ldapSearchBase = null;
	private String ldapobjectCategory = null;
	
	private boolean ldapPooled = false;
	private boolean ldapIgnorePartialResultException = false;

	public LdapPreferences(String ldapUsr, String ldapPwd, boolean ldapPooled,
			String ldapUrl, String ldapBase, String ldapSearchBase, 
			String ldapUserAttribute, boolean ldapIgnorePartialResultException,String ldapobjectCategory) {
		
		this.ldapUsr = ldapUsr;
		this.ldapPwd = ldapPwd;
		this.ldapPooled = ldapPooled;
		this.ldapUrl = ldapUrl;
		this.ldapBase = ldapBase;
		this.ldapSearchBase = ldapSearchBase;
		this.ldapUserAttribute = ldapUserAttribute;
		this.ldapIgnorePartialResultException = ldapIgnorePartialResultException;
		this.ldapobjectCategory = ldapobjectCategory;
	}

	
	public String getObjectCategory() {
		return this.ldapobjectCategory;
	}


	public String getLdapBase() {
		return this.ldapBase;
	}

	public String getLdapUserAttribute() {
		return this.ldapUserAttribute;
	}
	
	public boolean getLdapIgnorePartialResultException() {
		return this.ldapIgnorePartialResultException;
	}

	public String getLdapPwd() {
		return this.ldapPwd;
	}

	public String getLdapSearchBase() {
		return this.ldapSearchBase;
	}

	public String getLdapUrl() {
		return this.ldapUrl;
	}

	public String getLdapUsr() {
		return this.ldapUsr;
	}

	public boolean isLdapPooled() {
		return this.ldapPooled;
	}
	
	public boolean arePropertiesSet() {
		return (ldapPwd.trim().length() > 0 && ldapUrl.trim().length() > 0 && 
				ldapUsr.trim().length() > 0 && ldapUserAttribute.trim().length() > 0 &&
				ldapSearchBase.trim().length() > 0 && ldapobjectCategory.length() >0 );
	}
}

package com.esc20.nonDBModels;

import java.io.Serializable;

public class W2Option implements Serializable {
	private static final long serialVersionUID = 7384987436816489316L;
	
	private String trs;
	private String hlth;
	private String caf;
	private String nta;
	private String txa;
	private String tfb;
	public String getTrs() {
		return trs;
	}
	public void setTrs(String trs) {
		this.trs = trs;
	}
	public String getHlth() {
		return hlth;
	}
	public void setHlth(String hlth) {
		this.hlth = hlth;
	}
	public String getCaf() {
		return caf;
	}
	public void setCaf(String caf) {
		this.caf = caf;
	}
	public String getNta() {
		return nta;
	}
	public void setNta(String nta) {
		this.nta = nta;
	}
	public String getTxa() {
		return txa;
	}
	public void setTxa(String txa) {
		this.txa = txa;
	}
	public String getTfb() {
		return tfb;
	}
	public void setTfb(String tfb) {
		this.tfb = tfb;
	}
	
}

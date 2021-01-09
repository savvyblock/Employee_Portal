package com.esc20.nonDBModels;

import java.io.Serializable;

public class W2Print implements Serializable, Cloneable
{
	private static final long serialVersionUID = 7384987436816489315L;
	
	private String ssn;
	private String tgross;
	private String tgross3;
	private String whold;
	private String whold3;
	private String ein;
	private String fgross;
	private String fgross3;
	private String ftax;
	private String ftax3;
	private String ename;
	private String eaddress;
	private String ecityst;
	private String mgross;
	private String mgross3;
	private String mtax;
	private String mtax3;
	private String eic;
	private String eic3;
	private String dcare;
	private String dcare3;
	private String empname;
	private String empaddress;
	private String empcityst;
	private String code1201;
	private String amt1201;
	private String code1202;
	private String amt1202;
	private String code1203;
	private String amt1203;
	private String code1204;
	private String amt1204;
	private String code1205;
	private String amt1205;
	private String code1206;
	private String amt1206;
	private String code1207;
	private String amt1207;
	private String code1208;
	private String amt1208;
	private String code1209;
	private String amt1209;
	private String code31201;
	private String amt31201;
	private String code31202;
	private String amt31202;
	private String code31203;
	private String amt31203;
	private String code31204;
	private String amt31204;
	private String code31205;
	private String amt31205;
	private String code31206;
	private String amt31206;
	private String code31207;
	private String amt31207;
	private String code1401;
	private String amt1401;
	private String code1402;
	private String amt1402;
	private String code1403;
	private String amt1403;
	private String code1404;
	private String amt1404;
	private String code1405;
	private String amt1405;
	private String code1406;
	private String amt1406;
	private String code1407;
	private String amt1407;
	private String code1408;
	private String amt1408;
	private String code1409;
	private String amt1409; //EPSLA
	private String code1410;
	private String amt1410;
	private String code1411;
	private String amt1411;
	private String code31401;
	private String amt31401;
	private String code31402;
	private String amt31402;
	private String code31403;
	private String amt31403;
	private String code31404;
	private String amt31404;
	private String code31405;
	private String amt31405;
	private String code31406;
	private String amt31406;
	private String code31407;
	private String amt31407;
	private String code1210;
	private String amt1210;
	private String code1211;
	private String amt1211;
	
	private String statemp;
	private String retplan;
	private String thrdsick;
	private String url;

	private String copy;
	
	public String geturl() {
		return url;
	}
	public void seturl(String url) {
		this.url = url;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getTgross() {
		return tgross;
	}
	public void setTgross(String tgross) {
		this.tgross = tgross;
	}
	public String getTgross3() {
		return tgross3;
	}
	public void setTgross3(String tgross3) {
		this.tgross3 = tgross3;
	}
	public String getWhold() {
		return whold;
	}
	public void setWhold(String whold) {
		this.whold = whold;
	}
	public String getWhold3() {
		return whold3;
	}
	public void setWhold3(String whold3) {
		this.whold3 = whold3;
	}
	public String getEin() {
		return ein;
	}
	public void setEin(String ein) {
		this.ein = ein;
	}
	public String getFgross() {
		return fgross;
	}
	public void setFgross(String fgross) {
		this.fgross = fgross;
	}
	public String getFgross3() {
		return fgross3;
	}
	public void setFgross3(String fgross3) {
		this.fgross3 = fgross3;
	}
	public String getFtax() {
		return ftax;
	}
	public void setFtax(String ftax) {
		this.ftax = ftax;
	}
	public String getFtax3() {
		return ftax3;
	}
	public void setFtax3(String ftax3) {
		this.ftax3 = ftax3;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getEaddress() {
		return eaddress;
	}
	public void setEaddress(String eaddress) {
		this.eaddress = eaddress;
	}
	public String getEcityst() {
		return ecityst;
	}
	public void setEcityst(String ecityst) {
		this.ecityst = ecityst;
	}
	public String getMgross() {
		return mgross;
	}
	public void setMgross(String mgross) {
		this.mgross = mgross;
	}
	public String getMgross3() {
		return mgross3;
	}
	public void setMgross3(String mgross3) {
		this.mgross3 = mgross3;
	}
	public String getMtax() {
		return mtax;
	}
	public void setMtax(String mtax) {
		this.mtax = mtax;
	}
	public String getMtax3() {
		return mtax3;
	}
	public void setMtax3(String mtax3) {
		this.mtax3 = mtax3;
	}
	public String getEic() {
		return eic;
	}
	public void setEic(String eic) {
		this.eic = eic;
	}
	public String getEic3() {
		return eic3;
	}
	public void setEic3(String eic3) {
		this.eic3 = eic3;
	}
	public String getDcare() {
		return dcare;
	}
	public void setDcare(String dcare) {
		this.dcare = dcare;
	}
	public String getDcare3() {
		return dcare3;
	}
	public void setDcare3(String dcare3) {
		this.dcare3 = dcare3;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmpaddress() {
		return empaddress;
	}
	public void setEmpaddress(String empaddress) {
		this.empaddress = empaddress;
	}
	public String getEmpcityst() {
		return empcityst;
	}
	public void setEmpcityst(String empcityst) {
		this.empcityst = empcityst;
	}
	public String getCode1201() {
		return code1201;
	}
	public void setCode1201(String code1201) {
		this.code1201 = code1201;
	}
	public String getAmt1201() {
		return amt1201;
	}
	public void setAmt1201(String amt1201) {
		this.amt1201 = amt1201;
	}
	public String getCode1202() {
		return code1202;
	}
	public void setCode1202(String code1202) {
		this.code1202 = code1202;
	}
	public String getAmt1202() {
		return amt1202;
	}
	public void setAmt1202(String amt1202) {
		this.amt1202 = amt1202;
	}
	public String getCode1203() {
		return code1203;
	}
	public void setCode1203(String code1203) {
		this.code1203 = code1203;
	}
	public String getAmt1203() {
		return amt1203;
	}
	public void setAmt1203(String amt1203) {
		this.amt1203 = amt1203;
	}
	public String getCode1204() {
		return code1204;
	}
	public void setCode1204(String code1204) {
		this.code1204 = code1204;
	}
	public String getAmt1204() {
		return amt1204;
	}
	public void setAmt1204(String amt1204) {
		this.amt1204 = amt1204;
	}
	public String getCode1205() {
		return code1205;
	}
	public void setCode1205(String code1205) {
		this.code1205 = code1205;
	}
	public String getAmt1205() {
		return amt1205;
	}
	public void setAmt1205(String amt1205) {
		this.amt1205 = amt1205;
	}
	public String getCode1206() {
		return code1206;
	}
	public void setCode1206(String code1206) {
		this.code1206 = code1206;
	}
	public String getAmt1206() {
		return amt1206;
	}
	public void setAmt1206(String amt1206) {
		this.amt1206 = amt1206;
	}
	public String getCode1207() {
		return code1207;
	}
	public void setCode1207(String code1207) {
		this.code1207 = code1207;
	}
	public String getAmt1207() {
		return amt1207;
	}
	public void setAmt1207(String amt1207) {
		this.amt1207 = amt1207;
	}
	public String getCode31201() {
		return code31201;
	}
	public void setCode31201(String code31201) {
		this.code31201 = code31201;
	}
	public String getAmt31201() {
		return amt31201;
	}
	public void setAmt31201(String amt31201) {
		this.amt31201 = amt31201;
	}
	public String getCode31202() {
		return code31202;
	}
	public void setCode31202(String code31202) {
		this.code31202 = code31202;
	}
	public String getAmt31202() {
		return amt31202;
	}
	public void setAmt31202(String amt31202) {
		this.amt31202 = amt31202;
	}
	public String getCode31203() {
		return code31203;
	}
	public void setCode31203(String code31203) {
		this.code31203 = code31203;
	}
	public String getAmt31203() {
		return amt31203;
	}
	public void setAmt31203(String amt31203) {
		this.amt31203 = amt31203;
	}
	public String getCode31204() {
		return code31204;
	}
	public void setCode31204(String code31204) {
		this.code31204 = code31204;
	}
	public String getAmt31204() {
		return amt31204;
	}
	public void setAmt31204(String amt31204) {
		this.amt31204 = amt31204;
	}
	public String getCode31205() {
		return code31205;
	}
	public void setCode31205(String code31205) {
		this.code31205 = code31205;
	}
	public String getAmt31205() {
		return amt31205;
	}
	public void setAmt31205(String amt31205) {
		this.amt31205 = amt31205;
	}
	public String getCode31206() {
		return code31206;
	}
	public void setCode31206(String code31206) {
		this.code31206 = code31206;
	}
	public String getAmt31206() {
		return amt31206;
	}
	public void setAmt31206(String amt31206) {
		this.amt31206 = amt31206;
	}
	public String getCode31207() {
		return code31207;
	}
	public void setCode31207(String code31207) {
		this.code31207 = code31207;
	}
	public String getAmt31207() {
		return amt31207;
	}
	public void setAmt31207(String amt31207) {
		this.amt31207 = amt31207;
	}
	public String getCode1401() {
		return code1401;
	}
	public void setCode1401(String code1401) {
		this.code1401 = code1401;
	}
	public String getAmt1401() {
		return amt1401;
	}
	public void setAmt1401(String amt1401) {
		this.amt1401 = amt1401;
	}
	public String getCode1402() {
		return code1402;
	}
	public void setCode1402(String code1402) {
		this.code1402 = code1402;
	}
	public String getAmt1402() {
		return amt1402;
	}
	public void setAmt1402(String amt1402) {
		this.amt1402 = amt1402;
	}
	public String getCode1403() {
		return code1403;
	}
	public void setCode1403(String code1403) {
		this.code1403 = code1403;
	}
	public String getAmt1403() {
		return amt1403;
	}
	public void setAmt1403(String amt1403) {
		this.amt1403 = amt1403;
	}
	public String getCode1404() {
		return code1404;
	}
	public void setCode1404(String code1404) {
		this.code1404 = code1404;
	}
	public String getAmt1404() {
		return amt1404;
	}
	public void setAmt1404(String amt1404) {
		this.amt1404 = amt1404;
	}
	public String getCode1405() {
		return code1405;
	}
	public void setCode1405(String code1405) {
		this.code1405 = code1405;
	}
	public String getAmt1405() {
		return amt1405;
	}
	public void setAmt1405(String amt1405) {
		this.amt1405 = amt1405;
	}
	public String getCode1406() {
		return code1406;
	}
	public void setCode1406(String code1406) {
		this.code1406 = code1406;
	}
	public String getAmt1406() {
		return amt1406;
	}
	public void setAmt1406(String amt1406) {
		this.amt1406 = amt1406;
	}
	public String getCode1407() {
		return code1407;
	}
	public void setCode1407(String code1407) {
		this.code1407 = code1407;
	}
	public String getAmt1407() {
		return amt1407;
	}
	public void setAmt1407(String amt1407) {
		this.amt1407 = amt1407;
	}
	public String getCode1409() {
		return code1409;
	}
	public void setCode1409(String code1409) {
		this.code1409 = code1409;
	}
	public String getCode1410() {
		return code1410;
	}
	public void setCode1410(String code1410) {
		this.code1410 = code1410;
	}
	public String getCode1411() {
		return code1411;
	}
	public void setCode1411(String code1411) {
		this.code1411 = code1411;
	}
	public String getAmt1409() {
		return amt1409;
	}
	public void setAmt1409(String amt1409) {
		this.amt1409 = amt1409;
	}
	public String getAmt1410() {
		return amt1410;
	}
	public void setAmt1410(String amt1410) {
		this.amt1410 = amt1410;
	}
	public String getAmt1411() {
		return amt1411;
	}
	public void setAmt1411(String amt1411) {
		this.amt1411 = amt1411;
	}
	public String getCode31401() {
		return code31401;
	}
	public void setCode31401(String code31401) {
		this.code31401 = code31401;
	}
	public String getAmt31401() {
		return amt31401;
	}
	public void setAmt31401(String amt31401) {
		this.amt31401 = amt31401;
	}
	public String getCode31402() {
		return code31402;
	}
	public void setCode31402(String code31402) {
		this.code31402 = code31402;
	}
	public String getAmt31402() {
		return amt31402;
	}
	public void setAmt31402(String amt31402) {
		this.amt31402 = amt31402;
	}
	public String getCode31403() {
		return code31403;
	}
	public void setCode31403(String code31403) {
		this.code31403 = code31403;
	}
	public String getAmt31403() {
		return amt31403;
	}
	public void setAmt31403(String amt31403) {
		this.amt31403 = amt31403;
	}
	public String getCode31404() {
		return code31404;
	}
	public void setCode31404(String code31404) {
		this.code31404 = code31404;
	}
	public String getAmt31404() {
		return amt31404;
	}
	public void setAmt31404(String amt31404) {
		this.amt31404 = amt31404;
	}
	public String getCode31405() {
		return code31405;
	}
	public void setCode31405(String code31405) {
		this.code31405 = code31405;
	}
	public String getAmt31405() {
		return amt31405;
	}
	public void setAmt31405(String amt31405) {
		this.amt31405 = amt31405;
	}
	public String getCode31406() {
		return code31406;
	}
	public void setCode31406(String code31406) {
		this.code31406 = code31406;
	}
	public String getAmt31406() {
		return amt31406;
	}
	public void setAmt31406(String amt31406) {
		this.amt31406 = amt31406;
	}
	public String getCode31407() {
		return code31407;
	}
	public void setCode31407(String code31407) {
		this.code31407 = code31407;
	}
	public String getAmt31407() {
		return amt31407;
	}
	public void setAmt31407(String amt31407) {
		this.amt31407 = amt31407;
	}
	public String getStatemp() {
		return statemp;
	}
	public void setStatemp(String statemp) {
		this.statemp = statemp;
	}
	public String getRetplan() {
		return retplan;
	}
	public void setRetplan(String retplan) {
		this.retplan = retplan;
	}
	public String getThrdsick() {
		return thrdsick;
	}
	public void setThrdsick(String thrdsick) {
		this.thrdsick = thrdsick;
	}
	public String getCode1208() {
		return code1208;
	}
	public void setCode1208(String code1208) {
		this.code1208 = code1208;
	}
	public String getAmt1208() {
		return amt1208;
	}
	public void setAmt1208(String amt1208) {
		this.amt1208 = amt1208;
	}
	//For calendar year >= 2010
	public String getCode1209() {
		return code1209;
	}
	//For calendar year >= 2010
	public void setCode1209(String code1209) {
		this.code1209 = code1209;
	}
	//For calendar year >= 2010
	public String getAmt1209() {
		return amt1209;
	}
	//For calendar year >= 2010
	public void setAmt1209(String amt1209) {
		this.amt1209 = amt1209;
	}
	public String getCode1408() {
		return code1408;
	}
	public void setCode1408(String code1408) {
		this.code1408 = code1408;
	}
	public String getAmt1408() {
		return amt1408;
	}
	public void setAmt1408(String amt1408) {
		this.amt1408 = amt1408;
	}
	public String getCopy() {
		return copy;
	}
	public void setCopy(String copy) {
		this.copy = copy;
	}
	
	//For calendar year >= 2016
	public String getAmt1210() {
		return amt1210;
	}
	public void setAmt1210(String amt1210) {
		this.amt1210 = amt1210;
	}
	public String getAmt1211() {
		return amt1211;
	}
	public void setAmt1211(String amt1211) {
		this.amt1211 = amt1211;
	}
	
	public String getCode1210() {
		return code1210;
	}
	public void setCode1210(String code1210) {
		this.code1210 = code1210;
	}
	
	public String getCode1211() {
		return code1211;
	}
	public void setCode1211(String code1211) {
		this.code1211 = code1211;
	}
	
	public Object clone() {
		W2Print p = new W2Print();
		p.seturl(this.url);
		p.setAmt1201(this.getAmt1201());
		p.setAmt1202(this.getAmt1202());
		p.setAmt1203(this.getAmt1203());
		p.setAmt1204(this.getAmt1204());
		p.setAmt1205(this.getAmt1205());
		p.setAmt1206(this.getAmt1206());
		p.setAmt1207(this.getAmt1207());
		p.setAmt1208(this.getAmt1208());
		p.setAmt1209(this.getAmt1209());
		p.setAmt1210(this.getAmt1210());
		p.setAmt1211(this.getAmt1211());
		p.setAmt1401(this.getAmt1401());
		p.setAmt1402(this.getAmt1402());
		p.setAmt1403(this.getAmt1403());
		p.setAmt1404(this.getAmt1404());
		p.setAmt1405(this.getAmt1405());
		p.setAmt1406(this.getAmt1406());
		p.setAmt1407(this.getAmt1407());
		p.setAmt1408(this.getAmt1408());
		p.setAmt1409(this.getAmt1409());
		p.setAmt1410(this.getAmt1410());
		p.setAmt1411(this.getAmt1411());
		p.setCode1201(this.getCode1201());
		p.setCode1202(this.getCode1202());
		p.setCode1203(this.getCode1203());
		p.setCode1204(this.getCode1204());
		p.setCode1205(this.getCode1205());
		p.setCode1206(this.getCode1206());
		p.setCode1207(this.getCode1207());
		p.setCode1208(this.getCode1208());
		p.setCode1209(this.getCode1209());
		p.setCode1210(this.getCode1210());
		p.setCode1211(this.getCode1211());
		p.setCode1401(this.getCode1401());
		p.setCode1402(this.getCode1402());
		p.setCode1403(this.getCode1403());
		p.setCode1404(this.getCode1404());
		p.setCode1405(this.getCode1405());
		p.setCode1406(this.getCode1406());
		p.setCode1407(this.getCode1407());
		p.setCode1408(this.getCode1408());
		p.setCode1409(this.getCode1409());
		p.setCode1410(this.getCode1410());
		p.setCode1411(this.getCode1411());
		p.setCopy(this.getCopy());
		p.setDcare(this.getDcare());
		p.setEaddress(this.getEaddress());
		p.setEcityst(this.getEcityst());
		p.setEic(this.getEic());
		p.setEin(this.getEin());
		p.setEmpaddress(this.getEmpaddress());
		p.setEmpcityst(this.getEmpcityst());
		p.setEmpname(this.getEmpname());
		p.setEname(this.getEname());
		p.setFgross(this.getFgross());
		p.setFtax(this.getFtax());
		p.setMgross(this.getMgross());
		p.setMtax(this.getMtax());
		p.setRetplan(this.getRetplan());
		p.setSsn(this.getSsn());
		p.setStatemp(this.getStatemp());
		p.setTgross(this.getTgross());
		p.setThrdsick(this.getThrdsick());
		p.setWhold(this.getWhold());

		return p;
	}
}

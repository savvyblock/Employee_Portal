package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;

public class EarningsPrint implements Serializable {

	private static final long serialVersionUID = 5826115229256954251L;
	
	private String dname;   //district name
		
	private String bhr_emp_demo_name_l = "";
	private String bhr_emp_demo_name_gen = "";
	private String gen_code_descr = "";
	private String bhr_emp_demo_name_f = "";
	private String bhr_emp_demo_name_m = "";
	private String bhr_pay_hist_chk_nbr = "";
	private String bhr_pay_hist_dt_of_pay = "";
	private String bthr_pay_dates_dt_payper_beg = "";
	private String bthr_pay_dates_dt_payper_end = "";
	private String bhr_pay_hist_marital_stat_tax = "";
	private int bhr_pay_hist_nbr_tax_exempts = 0;
	private String bhr_emp_demo_addr_nbr = "";
	private String bhr_emp_demo_addr_str = "";
	private String bhr_emp_demo_addr_apt = "";
	private String bhr_emp_demo_addr_city = "";
	private String bhr_emp_demo_addr_st = "";
	private String bhr_emp_demo_addr_zip = "";
	private String bhr_emp_demo_addr_zip4 = "";
	private String bhr_emp_pay_pay_campus = "";
	private String bhr_emp_job_campus_id = "";
	private String bhr_emp_pay_pay_freq = "";
	private String bhr_emp_pay_emp_nbr = "";
	private String bhr_pay_hist_dt_of_pay1 = "";
	private String cal_year = "";
	private String bhr_pay_hist_void_or_iss = "";
	private String bhr_pay_hist_adj_nbr;
	private String bhr_emp_job_campus_id_displayvalue = "";
	private String bhr_emp_pay_pay_campus_displayvalue = "";
	
	public String getBhr_emp_demo_name_l() {
		return bhr_emp_demo_name_l;
	}
	public void setBhr_emp_demo_name_l(String bhr_emp_demo_name_l) {
		this.bhr_emp_demo_name_l = bhr_emp_demo_name_l;
	}
	public String getBhr_emp_demo_name_gen() {
		return bhr_emp_demo_name_gen;
	}
	public void setBhr_emp_demo_name_gen(String bhr_emp_demo_name_gen) {
		this.bhr_emp_demo_name_gen = bhr_emp_demo_name_gen;
	}
	public String getGen_code_descr() {
		return gen_code_descr;
	}
	public void setGen_code_descr(String gen_code_descr) {
		this.gen_code_descr = gen_code_descr;
	}
	public String getBhr_emp_demo_name_f() {
		return bhr_emp_demo_name_f;
	}
	public void setBhr_emp_demo_name_f(String bhr_emp_demo_name_f) {
		this.bhr_emp_demo_name_f = bhr_emp_demo_name_f;
	}
	public String getBhr_emp_demo_name_m() {
		return bhr_emp_demo_name_m;
	}
	public void setBhr_emp_demo_name_m(String bhr_emp_demo_name_m) {
		this.bhr_emp_demo_name_m = bhr_emp_demo_name_m;
	}
	public String getBhr_pay_hist_chk_nbr() {
		return bhr_pay_hist_chk_nbr;
	}
	public void setBhr_pay_hist_chk_nbr(String bhr_pay_hist_chk_nbr) {
		this.bhr_pay_hist_chk_nbr = bhr_pay_hist_chk_nbr;
	}
	public String getBhr_pay_hist_dt_of_pay() {
		return bhr_pay_hist_dt_of_pay;
	}
	public void setBhr_pay_hist_dt_of_pay(String bhr_pay_hist_dt_of_pay) {
		this.bhr_pay_hist_dt_of_pay = bhr_pay_hist_dt_of_pay;
	}
	public String getBthr_pay_dates_dt_payper_beg() {
		return bthr_pay_dates_dt_payper_beg;
	}
	public void setBthr_pay_dates_dt_payper_beg(
			String bthr_pay_dates_dt_payper_beg) {
		this.bthr_pay_dates_dt_payper_beg = bthr_pay_dates_dt_payper_beg;
	}
	public String getBthr_pay_dates_dt_payper_end() {
		return bthr_pay_dates_dt_payper_end;
	}
	public void setBthr_pay_dates_dt_payper_end(
			String bthr_pay_dates_dt_payper_end) {
		this.bthr_pay_dates_dt_payper_end = bthr_pay_dates_dt_payper_end;
	}
	public String getBhr_pay_hist_marital_stat_tax() {
		return bhr_pay_hist_marital_stat_tax;
	}
	public void setBhr_pay_hist_marital_stat_tax(
			String bhr_pay_hist_marital_stat_tax) {
		this.bhr_pay_hist_marital_stat_tax = bhr_pay_hist_marital_stat_tax;
	}
	public int getBhr_pay_hist_nbr_tax_exempts() {
		return bhr_pay_hist_nbr_tax_exempts;
	}
	public void setBhr_pay_hist_nbr_tax_exempts(
			int bhr_pay_hist_nbr_tax_exempts) {
		this.bhr_pay_hist_nbr_tax_exempts = bhr_pay_hist_nbr_tax_exempts;
	}
	public String getBhr_emp_demo_addr_nbr() {
		return bhr_emp_demo_addr_nbr;
	}
	public void setBhr_emp_demo_addr_nbr(String bhr_emp_demo_addr_nbr) {
		this.bhr_emp_demo_addr_nbr = bhr_emp_demo_addr_nbr;
	}
	public String getBhr_emp_demo_addr_str() {
		return bhr_emp_demo_addr_str;
	}
	public void setBhr_emp_demo_addr_str(String bhr_emp_demo_addr_str) {
		this.bhr_emp_demo_addr_str = bhr_emp_demo_addr_str;
	}
	public String getBhr_emp_demo_addr_apt() {
		return bhr_emp_demo_addr_apt;
	}
	public void setBhr_emp_demo_addr_apt(String bhr_emp_demo_addr_apt) {
		this.bhr_emp_demo_addr_apt = bhr_emp_demo_addr_apt;
	}
	public String getBhr_emp_demo_addr_city() {
		return bhr_emp_demo_addr_city;
	}
	public void setBhr_emp_demo_addr_city(String bhr_emp_demo_addr_city) {
		this.bhr_emp_demo_addr_city = bhr_emp_demo_addr_city;
	}
	public String getBhr_emp_demo_addr_st() {
		return bhr_emp_demo_addr_st;
	}
	public void setBhr_emp_demo_addr_st(String bhr_emp_demo_addr_st) {
		this.bhr_emp_demo_addr_st = bhr_emp_demo_addr_st;
	}
	public String getBhr_emp_demo_addr_zip() {
		return bhr_emp_demo_addr_zip;
	}
	public void setBhr_emp_demo_addr_zip(String bhr_emp_demo_addr_zip) {
		this.bhr_emp_demo_addr_zip = bhr_emp_demo_addr_zip;
	}
	public String getBhr_emp_demo_addr_zip4() {
		return bhr_emp_demo_addr_zip4;
	}
	public void setBhr_emp_demo_addr_zip4(String bhr_emp_demo_addr_zip4) {
		this.bhr_emp_demo_addr_zip4 = bhr_emp_demo_addr_zip4;
	}
	public String getBhr_emp_pay_pay_campus() {
		return bhr_emp_pay_pay_campus;
	}
	public void setBhr_emp_pay_pay_campus(String bhr_emp_pay_pay_campus) {
		this.bhr_emp_pay_pay_campus = bhr_emp_pay_pay_campus;
	}
	public String getBhr_emp_job_campus_id() {
		return bhr_emp_job_campus_id;
	}
	public void setBhr_emp_job_campus_id(String bhr_emp_job_campus_id) {
		this.bhr_emp_job_campus_id = bhr_emp_job_campus_id;
	}
	public String getBhr_emp_pay_pay_freq() {
		return bhr_emp_pay_pay_freq;
	}
	public void setBhr_emp_pay_pay_freq(String bhr_emp_pay_pay_freq) {
		this.bhr_emp_pay_pay_freq = bhr_emp_pay_pay_freq;
	}
	public String getBhr_emp_pay_emp_nbr() {
		return bhr_emp_pay_emp_nbr;
	}
	public void setBhr_emp_pay_emp_nbr(String bhr_emp_pay_emp_nbr) {
		this.bhr_emp_pay_emp_nbr = bhr_emp_pay_emp_nbr;
	}
	public String getBhr_pay_hist_dt_of_pay1() {
		return bhr_pay_hist_dt_of_pay1;
	}
	public void setBhr_pay_hist_dt_of_pay1(String bhr_pay_hist_dt_of_pay1) {
		this.bhr_pay_hist_dt_of_pay1 = bhr_pay_hist_dt_of_pay1;
	}
	public String getCal_year() {
		return cal_year;
	}
	public void setCal_year(String cal_year) {
		this.cal_year = cal_year;
	}
	public String getBhr_pay_hist_void_or_iss() {
		return bhr_pay_hist_void_or_iss;
	}
	public void setBhr_pay_hist_void_or_iss(String bhr_pay_hist_void_or_iss) {
		this.bhr_pay_hist_void_or_iss = bhr_pay_hist_void_or_iss;
	}
	public String getBhr_pay_hist_adj_nbr() {
		return bhr_pay_hist_adj_nbr;
	}
	public void setBhr_pay_hist_adj_nbr(String bhr_pay_hist_adj_nbr) {
		this.bhr_pay_hist_adj_nbr = bhr_pay_hist_adj_nbr;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getBhr_emp_job_campus_id_displayvalue() {
		return bhr_emp_job_campus_id_displayvalue;
	}
	public void setBhr_emp_job_campus_id_displayvalue(
			String bhr_emp_job_campus_id_displayvalue) {
		this.bhr_emp_job_campus_id_displayvalue = bhr_emp_job_campus_id_displayvalue;
	}
	public String getBhr_emp_pay_pay_campus_displayvalue() {
		return bhr_emp_pay_pay_campus_displayvalue;
	}
	public void setBhr_emp_pay_pay_campus_displayvalue(
			String bhr_emp_pay_pay_campus_displayvalue) {
		this.bhr_emp_pay_pay_campus_displayvalue = bhr_emp_pay_pay_campus_displayvalue;
	}

}

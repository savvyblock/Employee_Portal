package com.esc20.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.nonDBModels.EarningsBank;
import com.esc20.nonDBModels.EarningsDeductions;
import com.esc20.nonDBModels.EarningsInfo;
import com.esc20.nonDBModels.EarningsJob;
import com.esc20.nonDBModels.EarningsLeave;
import com.esc20.nonDBModels.EarningsOther;
import com.esc20.nonDBModels.EarningsOvertime;
import com.esc20.nonDBModels.EarningsSupplemental;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayDate;
import com.esc20.util.StringUtil;

@Repository
public class EarningsDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
	public Integer getRestrictEarnings()
	{
		try{
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT isnull(max_days, 0) as max_days from bhr_eap_opt"); 
			Query q = session.createQuery(sql.toString());
			List<Object> result = q.list();
			
			if(result!=null && result.size()!=0)
				return (Integer) result.get(0);
			else
				return null;
		}
		catch(Exception e){
			return null;
		}
	}
	
	public List<PayDate> getAvailablePayDates(String employeeNumber) {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT hist.id.payFreq, hist.id.dtOfPay, hist.id.voidOrIss, hist.id.adjNbr, hist.id.chkNbr "); 
			sql.append("FROM BhrPayHist hist ");
			sql.append("WHERE hist.id.empNbr = :employeeNumber ");   //jf20120831 fix CTRS substring
			sql.append("ORDER BY hist.id.dtOfPay DESC ");

			Query q = session.createQuery(sql.toString());
	        q.setParameter("employeeNumber", employeeNumber);

			List <Object[]> result = q.list();
			
			List<PayDate> payDates = new ArrayList<PayDate>();
			PayDate payDate;
			
			for(Object[] item: result) {
				payDate = new PayDate(item[0],item[1],item[2],item[3],item[4]);
				payDates.add(payDate);
			}
			return payDates;
	}

	public EarningsInfo getEarningsInfo(String employeeNumber, PayDate payDate) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT hist.payCampus, demo.campusName, hist.id.chkNbr, dates.dtPayperEnd, hist.maritalStatTax, hist.nbrTaxExempts, dates.dtPayperBeg ");
		sql.append("FROM BhrPayHist hist, CrDemo demo, BthrPayDates dates "
				+ "where hist.payCampus = demo.id.campusId AND demo.id.schYr = SUBSTR(hist.id.dtOfPay,0,5) and ");
		sql.append("hist.id.dtOfPay = dates.id.dtOfPay AND hist.id.payFreq = dates.id.payFreq and ");
		sql.append("hist.id.empNbr=:employeeNumber AND hist.id.payFreq = :frequency AND hist.id.dtOfPay = :dtOfPay AND hist.payCampus != '' "
				+ "AND hist.id.chkNbr = :chkNbr AND hist.id.voidOrIss = :voidOrIss AND hist.id.adjNbr = :adjNbr");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
        Object[] res = (Object[]) q.uniqueResult();
        
        EarningsInfo info = new EarningsInfo(res[0],res[1],res[2],res[3],res[4],res[5],res[6]);
		return info;
	}

	public EarningsDeductions getEarningsDeductions(String employeeNumber, PayDate payDate, String checkNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT hist.stdGross, hist.grossPayTot, hist.absDedAmt, hist.absDedCoded, hist.nontrsNonpayBusAllow, hist.whTax, hist.medTax, ");
		sql.append("hist.extraDutyGross, hist.ovtmGross, hist.absDedRefund, hist.taxedBenefits, hist.eicAmt, hist.nontrsSuppl, (hist.nontrsBusAllow + hist.nontrsReimbrExcess ) as nontrsTaxPymtAmt, ");
		sql.append("(hist.nontrsReimbrBase + hist.nontrsNontaxBusAllow) as nontrsNontaxPymtAmt, hist.trsSupplComp, hist.ficaTax, hist.trsSalaryRed, (hist.trsDeposit - hist.trsSalaryRed) as trsInsAmt, ");
		sql.append("sum(case when isnull(deducCd.bthrDeducAbbrevTypCd.dedAbbrevTyp, '') = 'WH' then 0 when deductHist.refundFlg = 'Y' then (deductHist.dedAmt*-1) else deductHist.dedAmt end ) as totAddlDed, ");
		sql.append("hist.netPay, hist.nontrsNontaxNonpayAllow, hist.whGross, hist.ficaGross, hist.medGross ");
		sql.append("FROM BhrPayHist hist,BhrPayDeductHist deductHist, BthrDeducCd deducCd "
				+ "where hist.id.empNbr = deductHist.id.empNbr AND hist.id.dtOfPay = deductHist.id.dtOfPay AND hist.id.payFreq = deductHist.id.payFreq AND hist.id.chkNbr = deductHist.id.chkNbr AND ");
		sql.append("deductHist.id.dedCd = deducCd.dedCd AND ");
		sql.append("hist.id.empNbr=:employeeNumber AND hist.id.payFreq = :frequency AND hist.id.dtOfPay = :dtOfPay AND hist.id.chkNbr = :chkNbr AND hist.id.voidOrIss = :voidOrIss AND hist.id.adjNbr = :adjNbr ");
		sql.append("GROUP BY hist.stdGross, hist.grossPayTot, hist.absDedAmt, hist.absDedCoded, hist.nontrsNonpayBusAllow, hist.whTax, hist.medTax, ");
		sql.append("hist.extraDutyGross, hist.ovtmGross, hist.absDedRefund, hist.taxedBenefits, hist.eicAmt, hist.nontrsSuppl, hist.nontrsBusAllow , hist.nontrsReimbrExcess, ");
		sql.append("hist.nontrsReimbrBase , hist.nontrsNontaxBusAllow, hist.trsSupplComp, hist.ficaTax, hist.trsSalaryRed, hist.trsDeposit , hist.trsSalaryRed, ");
		sql.append("hist.netPay, hist.nontrsNontaxNonpayAllow, hist.whGross, hist.ficaGross, hist.medGross ");
		
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
		Query q = session.createQuery(sql.toString());
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
		Object[] res = (Object[]) q.uniqueResult();
		
		if(res != null) {
			EarningsDeductions deductions = new EarningsDeductions((BigDecimal) res[0],(BigDecimal) res[1],(BigDecimal) res[2],(BigDecimal) res[3],(BigDecimal) res[4],(BigDecimal) res[5],(BigDecimal) res[6],
					(BigDecimal) res[7],(BigDecimal) res[8],(BigDecimal) res[9],(BigDecimal) res[10],(BigDecimal) res[11],(BigDecimal) res[12],(BigDecimal) res[13],
					(BigDecimal) res[14],(BigDecimal) res[15],(BigDecimal) res[16],(BigDecimal) res[17],(BigDecimal) res[18],
					(Long) res[19],
					(BigDecimal) res[20],(BigDecimal) res[21], (BigDecimal) res[22], (BigDecimal) res[23], (BigDecimal) res[24]);
			return deductions;
		} else {
			return new EarningsDeductions();
		}
		
	}

	public List<EarningsOther> getEarningsOther(String employeeNumber, PayDate payDate, String checkNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT deduct.id.dedCd, (CASE WHEN deduct.id.dedCd = '0CE' THEN 'HSA Employer Contrib' WHEN deduct.id.dedCd = '0CA' THEN 'Emplr Annuity Contrib' ");
		sql.append("WHEN deduct.id.dedCd = '0CD' THEN 'Emplr Dependent Care' WHEN deduct.id.dedCd = '0CH' THEN 'TEA Health Insurance Contribution' ");
        sql.append("WHEN deduct.id.dedCd = '0CN' THEN 'Emplr Non-Annuity Contrib' ELSE isnull(deducCd.shortDescr,'Not in file') end), ");
        sql.append("(CASE WHEN deduct.refundFlg = 'Y' THEN (deduct.dedAmt*-1) ELSE deduct.dedAmt end), deduct.cafeFlg, ");
        sql.append("(CASE WHEN deduct.refundFlg = 'Y' THEN (deduct.emplrAmt*-1) ELSE deduct.emplrAmt end), 0, 0 ");
		sql.append("FROM BhrPayDeductHist deduct, BthrDeducCd deducCd where deduct.id.dedCd = deducCd.dedCd AND ");
		sql.append("deduct.id.empNbr=:employeeNumber AND deduct.id.payFreq = :frequency AND deduct.id.dtOfPay = :dtOfPay AND  deduct.id.chkNbr = :chkNbr AND deduct.id.voidOrIss = :voidOrIss AND deduct.id.adjNbr = :adjNbr  ");
		sql.append("UNION SELECT distinct '', '', 0.00, '', 0.00, 1, 0 ");
		sql.append("FROM BhrCalYtd cal1 ");
		sql.append("WHERE ( cal1.id.empNbr = :employeeNumber ) AND( cal1.id.cyrNyrFlg = 'C' ) AND ");
		sql.append("( cal1.id.payFreq = :frequency ) AND ( cal1.id.calYr = substr(:dtOfPay,1,4) ) AND ");
		sql.append("( cal1.dependCare + cal1.emplrDependCare) > 5000.00 ");
		sql.append("union SELECT distinct '', '', 0.00, '', 0.00, 0, 1 ");
		sql.append("FROM BhrCalYtd cal2  ");
		sql.append("WHERE ( cal2.id.empNbr = :employeeNumber ) AND( cal2.id.cyrNyrFlg = 'C' ) AND ");
		sql.append("( cal2.id.payFreq = :frequency ) AND ( cal2.id.calYr = substr(:dtOfPay,1,4) ) AND ");
		sql.append("( cal2.hsaEmplrContrib) > 3000.00");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
        List<Object[]> res = q.list();
        List<EarningsOther> result = new ArrayList<EarningsOther>();
        EarningsOther other;
        for(Object[] item : res) {
        	other = new EarningsOther((String) item[0], (String) item[1], (BigDecimal) item[2], (Character) item[3], (BigDecimal) item[4], (Integer) item[5], (Integer) item[6]);
        	result.add(other);
        }
        
		return result;
	}

	public List<EarningsJob> getEarningsJob(String employeeNumber, PayDate payDate, String checkNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT hist.id.jobCd, hist.payRate, hist.regHrsWrk, " +
				"hist.xmitalHrsWrk, hist.payType, cd.jobCdDescr ");
		sql.append("FROM BhrEmpJobHist hist, BthrJobCd cd where hist.id.jobCd = cd.id.jobCd and " +
					"hist.id.cyrNyrFlg = cd.id.cyrNyrFlg ");
		sql.append("AND hist.id.empNbr=:employeeNumber AND hist.id.payFreq = :frequency AND hist.id.dtOfPay = :dtOfPay AND hist.id.chkNbr = :chkNbr AND hist.id.voidOrIss = :voidOrIss AND hist.id.adjNbr = :adjNbr AND  ");
		sql.append("((hist.payRate <> 0 AND hist.payType in ('1','2')) OR (hist.payRate <> 0 AND hist.payType in ('3', '4') ");
		sql.append("AND (hist.regHrsWrk <> 0 OR hist.xmitalHrsWrk <>  0))) " +
				   " Union all select distinct case when suppl.extraDutyCd = '' then suppl.jobCd else suppl.extraDutyCd end, " +
				   "	sum(suppl.supplAmt), 0, 0," +
				   "    hist1.payType,  " +
								"case when suppl.extraDutyCd = '' then  " +
							     "  (select cd1.jobCdDescr  " +
									"    from BthrJobCd cd1  " +
									"    WHERE cd1.id.cyrNyrFlg = 'C' and  " +
									"          trim(cd1.id.jobCd) <> '' and  " +
									"          cd1.id.jobCd = suppl.jobCd)  " +
							       " else (select duty.extraDutyDescr  " + 
							    	"	from BthrExtraDuty duty  " +
							    	"	WHERE duty.id.cyrNyrFlg = 'C' and   " +
							    	"	      trim(duty.id.extraDutyCd) <> '' and  " +
							    	"	      duty.id.extraDutyCd = suppl.extraDutyCd)  " +
							       " end as display " +
							"  from BhrSupplXmital suppl, BhrEmpJobHist hist1 " +
							" where hist1.id.empNbr=:employeeNumber AND hist1.id.payFreq = :frequency AND hist1.id.dtOfPay = :dtOfPay AND hist1.id.chkNbr = :chkNbr AND hist1.id.voidOrIss = :voidOrIss AND hist1.id.adjNbr = :adjNbr AND" +
							"       0 = :adjNbr and " +
							"       suppl.id.payFreq = hist1.id.payFreq and " +
							"       suppl.id.dtOfPay = hist1.id.dtOfPay and " +
							"       suppl.id.empNbrr = hist1.id.empNbr and " +
							"       suppl.jobCd = hist1.id.jobCd and " +
							"       suppl.addStdGrossCd = 'Y' and " +
							"       suppl.nonTrsPymtTyp = '' " +
							" group by suppl.jobCd,  " +
							"         hist1.id.jobCd, " +
							"	 hist1.payType, " +
							"	 suppl.extraDutyCd");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
        List<Object[]> res = q.list();
        List<EarningsJob> result = new ArrayList<EarningsJob>();
        EarningsJob job;
        for(Object[] item : res) {
        	job = new EarningsJob((String) item[0], (BigDecimal) item[1], (BigDecimal) item[2], (BigDecimal) item[3], (Character) item[4], (String) item[5]);
        	result.add(job);
        }
        
		return result;
	}
	public List<EarningsSupplemental> getEarningsSupplemental(String employeeNumber, PayDate payDate,
			String checkNumber){
		List<Character> types = new ArrayList<Character>();
		types.add('S');
		return getEarningsFromBhrPayDistrHist(employeeNumber,payDate,types);
	}
	public List<EarningsSupplemental> getEarningsFromBhrPayDistrHist(String employeeNumber, PayDate payDate, List<Character> types) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();		
		sql.append("SELECT   sum(hist.acctAmt) as edAmt,  ");
		sql.append("		(case when trim(hist.extraDutyCd) = '' then 'ZZZ' else hist.extraDutyCd end) as edCd,");
		sql.append("	 (case when trim(hist.extraDutyCd) = '' then 'ZZZ' else hist.extraDutyCd end) || ' - ' || isnull(duty.extraDutyDescr, 'Not in file'),");
		sql.append("     hist.id.empNbr as bhrPayDeductHistEmpNbr,  ");
		sql.append("     hist.id.chkNbr as bhrPayDistrHistChkNbr,");
		sql.append("     hist.id.voidOrIss as bhrPayDistrHisVoidOrIss,");
		sql.append("     hist.id.adjNbr as bhrPayDistrHistAdjNbr,");
		sql.append("     hist.id.dtOfPay as bhrPayDistrHistDtOfPay ");
		sql.append("FROM BhrPayDistrHist hist, BthrExtraDuty duty ");
		sql.append("                            where hist.id.cyrNyrFlg = duty.id.cyrNyrFlg and");
		sql.append("                               hist.extraDutyCd = duty.id.extraDutyCd and ");
		sql.append("hist.id.empNbr=:employeeNumber AND hist.id.payFreq = :frequency AND hist.id.dtOfPay = :dtOfPay AND hist.id.chkNbr = :chkNbr AND hist.id.voidOrIss = :voidOrIss AND hist.id.adjNbr = :adjNbr AND ");
		sql.append("     hist.acctTyp in (:list) ");
		sql.append("group by hist.id.payFreq,   ");
		sql.append("     hist.id.empNbr,   ");
		sql.append("     hist.id.cyrNyrFlg,   ");
		sql.append("     hist.id.dtOfPay,   ");
		sql.append("     hist.id.chkNbr,   ");
		sql.append("     hist.id.voidOrIss,   ");
		sql.append("     hist.id.adjNbr,   ");
		sql.append("     hist.acctTyp,   ");
		sql.append("     hist.extraDutyCd,   ");
		sql.append("     duty.extraDutyDescr");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
        q.setParameterList("list", types.toArray());
        List<Object[]> res = q.list();
        List<EarningsSupplemental> result = new ArrayList<EarningsSupplemental>();
        EarningsSupplemental suppl;
        for(Object[] item : res) {
        	suppl = new EarningsSupplemental((BigDecimal) item[0], (String) item[1], (String) item[2], (String) item[3], (String) item[4], 
        			(Character) item[5],(Short) item[6],(String) item[7]);
        	result.add(suppl);
        }
        
		return result;
	}

	public List<EarningsSupplemental> getEarningsNonTrsTax(String employeeNumber, PayDate payDate, String checkNumber) {
		List<Character> types = new ArrayList<Character>();
		types.add('B');
		types.add('I');
		return getEarningsFromBhrPayDistrHist(employeeNumber,payDate,types);
	}

	public List<EarningsSupplemental> getEarningsNonTrsNonTax(String employeeNumber, PayDate payDate,
			String checkNumber) {
		List<Character> types = new ArrayList<Character>();
		types.add('T');
		return getEarningsFromBhrPayDistrHist(employeeNumber,payDate,types);
	}

	public List<EarningsBank> getEarningsBank(String employeeNumber, PayDate payDate, String checkNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT hist.id.bankCd, codes.bankName, hist.id.bankAcctTyp,   type.bankAcctTypDescr, isnull(hist.id.bankAcctNbr,'') as bankAcctNbr, hist.bankAcctAmt ");
		sql.append("FROM BhrBankDepositHist hist , BthrBankCodes codes , BthrBankAcctTyp type where codes.bankCd = hist.id.bankCd ");
		sql.append("and type.bankAcctTyp = hist.id.bankAcctTyp ");
		sql.append("and hist.id.empNbr=:employeeNumber AND hist.id.payFreq = :frequency AND hist.id.dtOfPay = :dtOfPay AND hist.id.chkNbr = :chkNbr AND hist.id.voidOrIss = :voidOrIss AND hist.id.adjNbr = :adjNbr");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
        List<Object[]> res = q.list();
        List<EarningsBank> result = new ArrayList<EarningsBank>();
        EarningsBank bank;
        for(Object[] item : res) {
        	bank = new EarningsBank((String) item[0], (String) item[1], (Character) item[2], (String) item[3], (String) item[4],(BigDecimal) item[5]);
        	result.add(bank);
        }
        
		return result;
		
	}

	public List<EarningsLeave> getEarningsLeave(String employeeNumber, PayDate payDate, String checkNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT lv.id.lvTyp, descr.longDescr, sum(isnull(hist.lvUsed,0)) as lvTaken, isnull(hist.id.voidOrIss,'') as voidOrIss, isnull(hist.id.adjNbr,0) as adjNbr, lv.lvEndBal, type.chkStubPos  ");
		sql.append("FROM BhrEmpLv lv, BthrLvTypDescr descr,BthrLvTyp type,BhrEmpLvDockHist hist where descr.lvTyp = lv.id.lvTyp ");
		sql.append("AND type.id.lvTyp = lv.id.lvTyp AND type.id.payFreq = :frequency ");
		sql.append("AND hist.id.payFreq = :frequency AND hist.id.empNbr = :employeeNumber AND hist.id.chkNbr = :chkNbr AND  ");
		sql.append("hist.id.dtOfPay = :dtOfPay AND hist.id.lvAltTyp  = lv.id.lvTyp and ");
		sql.append("hist.id.voidOrIss = :voidOrIss and hist.id.adjNbr = :adjNbr ");
		sql.append("AND lv.id.empNbr=:employeeNumber AND lv.id.payFreq = :frequency AND type.chkStubPos != '' AND type.chkStubPos is not null ");
		sql.append("group by lv.id.lvTyp, descr.longDescr, hist.id.voidOrIss, hist.id.adjNbr, lv.lvEndBal, type.chkStubPos ");
		sql.append("ORDER BY type.chkStubPos, lv.id.lvTyp");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
        List<Object[]> res = q.list();
        List<EarningsLeave> result = new ArrayList<EarningsLeave>();
        EarningsLeave leave;
        for(Object[] item : res) {
        	leave = new EarningsLeave((String) item[0], (String) item[1], (BigDecimal) item[2], (Character) item[3], (Short) item[4],(BigDecimal) item[5],(Character) item[6]);
        	result.add(leave);
        }
        
		return result;
	}

	public List<EarningsOvertime> getEarningsOvertime(String employeeNumber, PayDate payDate) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct suppl.jobCd as jobCd, cd.jobCdDescr,");
		sql.append("	         sum(suppl.supplAmt) as totSupplAmt, ");
		sql.append("	         sum(suppl.ovtmHrsWrk) as totHrsWrk, ");
		sql.append("	         case when suppl.ovtmHrsWrk > 0 then round(suppl.supplAmt / suppl.ovtmHrsWrk,2) else 0 end as ovtmRate ");
		sql.append("FROM BhrSupplXmital suppl, BthrJobCd cd ");
		sql.append("	   where suppl.id.empNbr = :employeeNumber and suppl.jobCd = cd.id.jobCd and suppl.glFileId = cd.glFileId and " );
		sql.append("	         suppl.id.payFreq = :frequency and ");
		sql.append("	         suppl.id.dtOfPay = :dtOfPay and ");
		sql.append("	         suppl.transmittalType = 'O' and " +
				   "		     cd.id.cyrNyrFlg = 'C' ");
		sql.append("	group by suppl.jobCd, ");
		sql.append("	         suppl.supplAmt, ");
		sql.append("	 		suppl.ovtmHrsWrk, " );
		sql.append("cd.jobCdDescr");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        List<Object[]> res = q.list();
        List<EarningsOvertime> result = new ArrayList<EarningsOvertime>();
        EarningsOvertime overTime;
        for(Object[] item : res) {
        	overTime = new EarningsOvertime((String) item[0], (String) item[1], (BigDecimal) item[2], (BigDecimal) item[3], (BigDecimal) item[4]);
        	result.add(overTime);
        }
        
		return result;
	}

	public BigDecimal getEmplrPrvdHlthcare(String employeeNumber, PayDate latestPayDate) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ytd.emplrPrvdHlthcare from BhrCalYtd ytd where ytd.id.empNbr =:employeeNumber and ");
		sql.append("	         ytd.id.payFreq =:frequency and ");
		sql.append("	         ytd.id.cyrNyrFlg = 'C' and ");
		sql.append("	         ytd.id.calYr =:calYr ");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(latestPayDate.getDateFreq(), 1);
		String year = latestPayDate.getDateFreq().substring(0, 4);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("calYr", year);
        BigDecimal res = (BigDecimal) q.uniqueResult();
        
		return res;
	}
}

package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class OptionsDao extends NamedParameterJdbcDaoSupport implements IOptionsDao
{
	@Override
	public Options getOptions() {
		String sql = "SELECT * FROM BHR_EAP_OPT";
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		return (Options)getNamedParameterJdbcTemplate().queryForObject(sql, params, new ParameterizedRowMapper<Options>()
		{
			@Override
			public Options mapRow(ResultSet rs, int rowNum) throws SQLException {
				Options o = new Options();
				
				String idType = rs.getString("ID_TYP");
				
				if("E".equals(idType))
				{
					o.setIdType(Options.IdType.EmployeeNumber);
				}
				else if("S".equals(idType))
				{
					o.setIdType(Options.IdType.Ssn);
				}
				
				if("Y".equals(rs.getString("ENBL_EAP")))
					o.setEnableEmployeeAccessSystem(true);
				else
					o.setEnableEmployeeAccessSystem(false);
				
				if("Y".equals(rs.getString("ENBL_EARN")))
					o.setEnableEarnings(true);
				else
					o.setEnableEarnings(false);
				
				if("Y".equals(rs.getString("ENBL_LV")))
					o.setEnableLeave(true);
				else
					o.setEnableLeave(false);
				
				if("Y".equals(rs.getString("ENBL_W2")))
					o.setEnableW2(true);
				else
					o.setEnableW2(false);
				
				if("Y".equals(rs.getString("ENBL_ELEC_CONSNT_W2")))
					o.setEnableElecConsntW2(true);
				else
					o.setEnableElecConsntW2(false);
				
				if("Y".equals(rs.getString("ENBL_1095")))
					o.setEnable1095(true);
				else
					o.setEnable1095(false);
				
				if("Y".equals(rs.getString("ENBL_ELEC_CONSNT_1095")))
					o.setEnableElecConsnt1095(true);
				else
					o.setEnableElecConsnt1095(false);
				
				if("Y".equals(rs.getString("ENBL_CAL_YTD")))
					o.setEnableCalendarYearToDate(true);
				else
					o.setEnableCalendarYearToDate(false);
				
				if("Y".equals(rs.getString("ENBL_CPI")))
					o.setEnableCurrentPayInformation(true);
				else
					o.setEnableCurrentPayInformation(false);
				
				if("Y".equals(rs.getString("ENBL_DED")))
					o.setEnableDeductions(true);
				else
					o.setEnableDeductions(false);
				
				if("Y".equals(rs.getString("ENBL_DEMO")))
					o.setEnableSelfServiceDemographic(true);
				else
					o.setEnableSelfServiceDemographic(false);
				
				if("Y".equals(rs.getString("ENBL_PAY")))
					o.setEnableSelfServicePayroll(true);
				else
					o.setEnableSelfServicePayroll(false);
				
				if("Y".equals(rs.getString("SHOW_PROC")))
					o.setShowProcessedLeave(true);
				else
					o.setShowProcessedLeave(false);
				
				if("Y".equals(rs.getString("SHOW_UNPROC")))
					o.setShowUnprocessedLeave(true);
				else
					o.setShowUnprocessedLeave(false);
				
				if("Y".equals(rs.getString("RSTR_EARN_DD")))
					o.setRestrictEarnings(true);
				else
					o.setRestrictEarnings(false);
				
				if("Y".equals(rs.getString("ENBL_LV_REQ")))
					o.setEnableLeaveReq(true);
				else
					o.setEnableLeaveReq(false);
				
				if ("Y".equals(rs.getString("USE_PMIS_SPVSR_LEVELS")))
					o.setUsePMISSpvsrLevels(true);
				else
					o.setUsePMISSpvsrLevels(false);
				
				o.setMaxDays(rs.getInt("MAX_DAYS"));
				o.setW2Latest(rs.getInt("W2_LATEST"));
				o.setUrl(rs.getString("URL_HM"));
				o.setPreNote(rs.getString("PRE_NOTE"));
				o.setDdAccounts(rs.getInt("DD_ACCT"));
				
				o.setMessageEmployeeAccessSystem(rs.getString("MSG_EAP"));
				o.setMessageCalendarYearToDate(rs.getString("MSG_CAL_YTD"));
				o.setMessageCurrentPayInformation(rs.getString("MSG_CPI"));
				o.setMessageDeductions(rs.getString("MSG_DED"));
				o.setMessageEarnings(rs.getString("MSG_EARN"));
				o.setMessageLeave(rs.getString("MSG_LV"));
				o.setMessageSelfServiceDemographic(rs.getString("MSG_DEMO"));
				o.setMessageSelfServicePayroll(rs.getString("MSG_PAY"));
				o.setMessageW2(rs.getString("MSG_W2"));
				o.setMessage1095(rs.getString("MSG_1095"));
				o.setMessageLeaveRequest(rs.getString("MSG_LV_REQ"));
				
				return o;
			}
		});
	}
}
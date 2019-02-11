package net.esc20.txeis.EmployeeAccess.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.dao.api.ILeaveDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Leave;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;
import net.esc20.txeis.EmployeeAccess.service.api.ILeaveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveService implements ILeaveService
{
	private ILeaveDao leaveDao;
	private IOptionsDao optionsDao;
	
	@Autowired
	public LeaveService(ILeaveDao leaveDao, IOptionsDao optionsDao)
	{
		this.leaveDao = leaveDao;
		this.optionsDao = optionsDao;
	}

	@Override
	public Frequency getInitialFrequency(List<Frequency> frequencies)
	{
		if(frequencies != null && frequencies.size() > 0)
		{
			return frequencies.get(0);
		}
		
		return null;
	}
	
	@Override
	public Map<Frequency,List<LeaveInfo>> retrieveLeaveInfos(String employeeNumber, boolean removeZeroedOutLeaveTypes)
	{
		List<LeaveInfo> leaveInfos = leaveDao.getLeaveInfo(employeeNumber);
		
		if (removeZeroedOutLeaveTypes) {
			for(int i = 0; i < leaveInfos.size(); i++)
			{
				if(leaveInfos.get(i).getBeginBalance().doubleValue() == 0 &&
					leaveInfos.get(i).getAdvancedEarned().doubleValue() == 0 &&
					leaveInfos.get(i).getUsed().doubleValue() == 0 &&
					leaveInfos.get(i).getPendingUsed().doubleValue() == 0 &&
					leaveInfos.get(i).getPendingPayroll().doubleValue() == 0 &&
					leaveInfos.get(i).getPendingEarned().doubleValue() == 0 &&
					leaveInfos.get(i).getAvailableBalance().doubleValue() == 0 &&
					leaveInfos.get(i).getPendingApproval().doubleValue() == 0)
				{
					leaveInfos.remove(i);
					i--;
				}
			}
		}
		
		Map<Frequency,List<LeaveInfo>> map = new HashMap<Frequency,List<LeaveInfo>>();
		
		for(LeaveInfo info : leaveInfos)
		{
			if(map.get(info.getFrequency()) == null)
			{
				map.put(info.getFrequency(), new ArrayList<LeaveInfo>());
			}
			
			map.get(info.getFrequency()).add(info);
		}
		
		return map;
	}
	
	@Override
	public List<Leave> getBlankLeaves()
	{
		return new ArrayList<Leave>();
	}
	
	@Override
	public List<Leave> retrieveLeaves(String employeeNumber, Frequency frequency, Date from, Date to, String leaveType)
	{
		Date toL = to;
		Date fromL = from;
		
//		if(toL == null)
//		{
//			toL = new Date();
//		}
		
		if(fromL == null)
		{
			Date toLL = toL;
			if(toLL == null)
			{
				toLL = new Date();
			}
			Calendar fromC = Calendar.getInstance();
			fromC.setTime(toLL);
			fromC.add(Calendar.MONTH, -18);
			fromL = fromC.getTime();
		}
		
		List<Leave> leaves = leaveDao.getLeaves(employeeNumber, frequency, fromL, toL, leaveType);
		
		// filter processed and not processed based on EA options
		
		Options o = optionsDao.getOptions();
		
		for(int i = 0; i < leaves.size(); i++)
		{
			if((leaves.get(i).getStatus().equals(Leave.Status.NotProcessed) && !o.isShowUnprocessedLeave())
				|| (leaves.get(i).getStatus().equals(Leave.Status.Processed) && !o.isShowProcessedLeave()))
			{
				leaves.remove(i);
				i--;
			}
		}
		
		return leaves;
	}
	
	@Override
	public List<Frequency> getAvailableFrequencies(Map<Frequency,List<LeaveInfo>> leaveInfos)
	{
		return new ArrayList<Frequency>(leaveInfos.keySet());
	}
	
	@Override
	public List<ICode> getAvailableLeaveTypes(String employeeNumber, Frequency frequency)
	{
		
		List<ICode> types = new ArrayList<ICode>();
		
		if(frequency != null)
		{
			types = leaveDao.getAvailableLeaveTypes(employeeNumber, frequency);
			types.add(0, new Code("","All"));
		}
		
		return types;
	}
	
	@Override
	public String getMessage()
	{
		Options o = optionsDao.getOptions();
		return o.getMessageLeave();
	}
}
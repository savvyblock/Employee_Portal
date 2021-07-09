package com.esc20.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AccountCodeDao;
import com.esc20.nonDBModels.Fund;

@Service
public class FundService {

	@Autowired
	private AccountCodeDao accountCodeDao;

	public List<Fund> getFundInformation(String fileId, String empNbr) {
		return accountCodeDao.getFundInformation(fileId, empNbr);
	}

	public List<Fund> getFunctionInformation(String fileId, String empNbr) {
		return accountCodeDao.getFunctionInformation(fileId, empNbr);
	}

	public List<Fund> getObjectInformation(String fileId, String empNbr) {
		return accountCodeDao.getObjectInformation(fileId, empNbr);
	}

	public List<Fund> getSubobjectInformation(String fileId, String empNbr) {
		return accountCodeDao.getSubobjectInformation(fileId, empNbr);
	}

	public List<Fund> getOrganizationInformation(String fileId, String empNbr) {
		return accountCodeDao.getOrganizationInformation(fileId, empNbr);
	}

	public List<Fund> getProgramInformation(String fileId, String empNbr) {
		return accountCodeDao.getProgramInformation(fileId, empNbr);
	}

	public List<Fund> getEducationalSpanInformation(String fileId, String empNbr) {
		return accountCodeDao.getEducationalSpanInformation(fileId, empNbr);
	}

	public List<Fund> getProjectDetailInformation(String fileId, String empNbr) {
		return accountCodeDao.getProjectDetailInformation(fileId, empNbr);
	}

	public List<Fund> getAccountCodeAutoComplete(String fileId, String empNbr, String search) {
		return accountCodeDao.getAccountCodeAutoComplete(fileId, empNbr, search);
	}
}
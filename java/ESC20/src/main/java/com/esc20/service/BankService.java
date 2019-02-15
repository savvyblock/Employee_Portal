package com.esc20.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.BankDao;
import com.esc20.model.BthrBankCodes;
import com.esc20.nonDBModels.Page;

@Service
public class BankService {

    private Logger logger = LoggerFactory.getLogger(BankService.class);

    @Autowired
    private BankDao bankDao;

    public List<BthrBankCodes> getAllBanks() {
		return bankDao.getAll();
	}
    
    public List<BthrBankCodes> getAllBanks(Page p) {
		return bankDao.getAll(p);
	}
    
    public List<BthrBankCodes> getBanksByEntity(BthrBankCodes bbc) {
		return bankDao.getBanksByEntity(bbc);
	}
    
    
	
}

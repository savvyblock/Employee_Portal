package com.esc20.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.LicenseAgreementDao;

@Service
public class LicenseAgreementService {

	@Autowired
	private LicenseAgreementDao licenseAgreementDao;		

	public boolean getLicense(String userName) {
		return licenseAgreementDao.getLicense(userName);
	}
	
	public boolean setLicense(String userName) {
		return licenseAgreementDao.setLicense(userName);
	}

}

package net.esc20.txeis.EmployeeAccess.domainobject;

import net.esc20.txeis.EmployeeAccess.dao.api.ITxeisPreferenceDAO;
import net.esc20.txeis.EmployeeAccess.domainobject.report.DistrictCachedObject;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ITxeisPreferenceMap;
import net.esc20.txeis.EmployeeAccess.service.TxeisPreferenceMap;

public class RoutedPreferenceFactory extends DistrictCachedObject<ITxeisPreferenceMap> {

	private ITxeisPreferenceDAO _dao = null;
	
	public void setTxeisPreferenceDAO(ITxeisPreferenceDAO pDAO) {
		_dao = pDAO;
	}

	@Override
	protected ITxeisPreferenceMap createItem(String database) {
		TxeisPreferenceMap preferences = new TxeisPreferenceMap();
		preferences.setPreferencesDAO(_dao);
		return preferences;
	}

}

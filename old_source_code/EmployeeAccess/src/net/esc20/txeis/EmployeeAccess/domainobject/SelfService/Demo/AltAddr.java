package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.service.ReferenceDataService;

public class AltAddr extends DemoDomainObject implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private String number;
	private String street;
	private String apartment;
	private String city;
	private Code state;
	private String zip;
	private String zipPlusFour;
	
	
	@Override
	public AltAddr clone()
	{
		AltAddr newMailAddr = new AltAddr();
		newMailAddr.setNumber(number);
		newMailAddr.setStreet(street);
		newMailAddr.setApartment(apartment);
		newMailAddr.setCity(city);
		newMailAddr.setState((Code)state.clone());
		newMailAddr.setZip(zip);
		newMailAddr.setZipPlusFour(zipPlusFour);
		return newMailAddr;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			AltAddr temp = (AltAddr)obj;
			return((number.equals(temp.number)) && 
				   (street.equals(temp.street)) &&
				   (apartment.equals(temp.apartment)) && 
				   (city.equals(temp.city)) && 
				   (state.equals(temp.state)) &&
				   (zip.equals(temp.zip)) &&
				   (zipPlusFour.equals(temp.zipPlusFour)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(number);
		sb.append(street);
		sb.append(apartment);
		sb.append(city);
		sb.append(state.getCode());
		sb.append(zip);
		sb.append(zipPlusFour);
		return sb.toString().hashCode();
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getApartment() {
		return apartment;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setState(Code state) 
	{
		this.state = state;
	}

	public Code getState() {
		return state;
	}

	public void setStateCode(String code)
	{
		this.state = ReferenceDataService.getStateFromCode(code);
	}
	
	public String getStateCode()
	{	
		return state.getCode();
	}
	
	public String getStateString()
	{
		return state.getDisplayLabel();
	}
	
	public void setStateString(String status)
	{
		state = ReferenceDataService.getStateFromDisplayLabel(status);
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZip() {
		return zip;
	}

	public void setZipPlusFour(String zipPlusFour) {
		this.zipPlusFour = zipPlusFour;
	}

	public String getZipPlusFour() {
		return zipPlusFour;
	}
}
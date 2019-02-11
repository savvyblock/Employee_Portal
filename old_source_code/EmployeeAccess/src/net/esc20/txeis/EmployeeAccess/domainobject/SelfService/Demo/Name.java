package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.service.ReferenceDataService;

public class Name extends DemoDomainObject implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Code title = null;
	private String lastName = "";
	private String firstName = "";
	private String middleName = "";
	private Code generation = null;
	private Boolean isDelete = false;
	
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public void setTitle(Code title) {
		this.title = title;
	}
	public Code getTitle() {
		return title;
	}

	public void setTitleCode(String code)
	{
		this.title = ReferenceDataService.getTitleFromCode(code);
	}
	
	public String getTitleCode()
	{
		return title.getCode();
	}
	
	public String getTitleString()
	{
		return title.getDescription();
	}
	
	public void setTitleString(String status)
	{
		title = ReferenceDataService.getTitleFromDisplayLabel(status);
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setGeneration(Code generation) {
		this.generation = generation;
	}
	public Code getGeneration() {
		return generation;
	}
	
	public void setGenerationCode(String code)
	{
		this.generation = ReferenceDataService.getGenerationFromCode(code);
	}
	
	public String getGenerationCode()
	{
		return generation.getCode();
	}
	
	public String getGenerationString()
	{
		return generation.getDescription();
	}
	
	public void setGenerationString(String status)
	{
		generation = ReferenceDataService.getGenerationFromDisplayLabel(status);
	}
	
	
	@Override
	public Name clone()
	{
		Name name = new Name();
		
		name.firstName = firstName;
		name.generation = (Code)generation.clone();
		name.lastName = lastName;
		name.middleName = middleName;
		name.title = (Code)title.clone();
		
		return name;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			Name temp = (Name)obj;
			return((title.equals(temp.title)) && 
				   (firstName.equals(temp.firstName)) &&
				   (lastName.equals(temp.lastName)) && 
				   (middleName.equals(temp.middleName)) && 
				   (generation.equals(temp.generation)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(title);
		sb.append(firstName);
		sb.append(lastName);
		sb.append(middleName);
		sb.append(generation);
		return sb.toString().hashCode();
	}
	
}
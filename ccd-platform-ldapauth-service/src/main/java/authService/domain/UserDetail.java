package authService.domain;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

@Entry(objectClasses = {"inetOrgPerson", "organizationalPerson", "person", "top"}, base = "ou=Departments")
public class UserDetail {
	
	@Id
	private Name id;
	
	@Attribute(name = "cn")
	private String fullName;
	
	@Attribute(name = "sn")
	private String lastName;
	
	@Attribute(name = "givenName")
	private String firstName;
	
	@Attribute(name = "mail")
	private String email;
	
	@Attribute(name = "userPassword")
	private String userPassword;
	
	@Attribute(name = "employeeNumber")
	private int employeeNumber;
	
	@Attribute(name = "telephoneNumber")
	private String phone;
	
    @Transient
    private String unit;

    @Transient
    private String department;

	public Name getId() {
		return id;
	}

	public void setId(Name id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
    
	@Override
	  public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;

	    UserDetail user = (UserDetail) o;

	    if (id != null ? !id.equals(user.id) : user.id != null) return false;

	    return true;
	  }

	  @Override
	  public int hashCode() {
	    return id != null ? id.hashCode() : 0;
	  }

}

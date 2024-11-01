package sample;

//CREATE TABLE administrator (
//		  administratorId int primary key NOT NULL,
//		  Fname varchar(20) DEFAULT NULL,
//		  Lname varchar(20) DEFAULT NULL,
//		  gender varchar(6) DEFAULT NULL,
//		  passworde varchar(20)
//		) ;

public class Administrator {

	private int administratorId;
	private String Fname;
	private String Lname;
	private String gender;
	private String passworde;
	private String email;
	private String phone;

	public Administrator(int administratorId, String fname, String lname, String gender, String passworde, String email,
			String phone) {
		this.administratorId = administratorId;
		Fname = fname;
		Lname = lname;
		this.gender = gender;
		this.passworde = passworde;
		this.email = email;
		this.phone = phone;
	}

	public Administrator(int administratorId, String fname, String lname, String gender, String passworde) {
		this.administratorId = administratorId;
		Fname = fname;
		Lname = lname;
		this.gender = gender;

	}

	public Administrator(String fname, String lname, String gender, String email, String phone) {
		Fname = fname;
		Lname = lname;
		this.gender = gender;
		this.email = email;
		this.phone = phone;

	}

	public Administrator(String fname, String lname, String gender, String phone) {
		this.Fname = fname;
		this.Lname = lname;
		this.gender = gender;
		this.phone = phone;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAdministratorId() {
		return administratorId;
	}

	public void setAdministratorId(int administratorId) {
		this.administratorId = administratorId;
	}

	public String getFname() {
		return Fname;
	}

	public void setFname(String fname) {
		Fname = fname;
	}

	public String getLname() {
		return Lname;
	}

	public void setLname(String lname) {
		Lname = lname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassworde() {
		return passworde;
	}

	public void setPassworde(String passworde) {
		this.passworde = passworde;
	}

	@Override
	public String toString() {
		return " [" + administratorId + ", " + Fname + "," + Lname + ", " + gender + ", " + passworde + ", " + email
				+ ", " + phone + "]";
	}

}

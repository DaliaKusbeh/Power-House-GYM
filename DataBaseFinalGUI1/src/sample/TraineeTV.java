package sample;

public class TraineeTV {
	private int traineeid;
	private String fname;
	private String lname;
	private String coachName;
	private String gender;
	private String email;
	private String phoneNumber;
	private String membershipType;

	public TraineeTV(int traineeid, String fname, String lname, String coachName, String gender, String email,
			String phoneNumber, String membershipType) {
		super();
		this.traineeid = traineeid;
		this.fname = fname;
		this.lname = lname;
		this.coachName = coachName;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.membershipType = membershipType;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public TraineeTV() {
	}

	public int getTraineeid() {
		return traineeid;
	}

	public void setTraineeid(int traineeid) {
		this.traineeid = traineeid;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	@Override
	public String toString() {
		return " [" + traineeid + ", " + fname + "," + lname + ", " + coachName + ", " + gender + ", " + email + ", "
				+ phoneNumber + ", " + membershipType + "]";
	}

}

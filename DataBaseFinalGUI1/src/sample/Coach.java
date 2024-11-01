package sample;

public class Coach {
	private int coachId;
	private int scheduleId;
	private String Fname;
	private String Lname;
	private String gender;
	private String trainingExperiences;
	private String email;

	public Coach(int coachId, int scheduleId, String fname, String lname, String gender, String trainingExperiences,
			String email) {
		this.coachId = coachId;
		this.scheduleId = scheduleId;
		Fname = fname;
		Lname = lname;
		this.gender = gender;
		this.trainingExperiences = trainingExperiences;
		this.email = email;
	}

	public int getCoachId() {
		return coachId;
	}

	public void setCoachId(int coachId) {
		this.coachId = coachId;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
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

	public String getTrainingExperiences() {
		return trainingExperiences;
	}

	public void setTrainingExperiences(String trainingExperiences) {
		this.trainingExperiences = trainingExperiences;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "[" + coachId + "" + scheduleId + ", " + Fname + ", " + Lname + "" + gender + ", " + trainingExperiences
				+ ", " + email + " ]";
	}
}

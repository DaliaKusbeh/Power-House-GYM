package sample;

public class CoachInformation {
 
	private int coachId;
	private String Fname ;
	private String Lname;
	private String gender;
	private String trainingExperiences;
	private String email;
	private int  NoOfTrainees;
		
	public CoachInformation() {
		super();
	}
	public CoachInformation(int coachId, String fname, String lname, String gender, String trainingExperiences,
			String email, int noOfTrainees) {
		super();
		this.coachId = coachId;
		Fname = fname;
		Lname = lname;
		this.gender = gender;
		this.trainingExperiences = trainingExperiences;
		this.email = email;
		NoOfTrainees = noOfTrainees;
	}
	public int getCoachId() {
		return coachId;
	}
	public void setCoachId(int coachId) {
		this.coachId = coachId;
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
	public int getNoOfTrainees() {
		return NoOfTrainees;
	}
	public void setNoOfTrainees(int noOfTrainees) {
		NoOfTrainees = noOfTrainees;
	}
	@Override
	public String toString() {
		return " [coachId=" + coachId + ", Fname=" + Fname + ", Lname=" + Lname + ", gender=" + gender
				+ ", trainingExperiences=" + trainingExperiences + ", email=" + email + ", NoOfTrainees=" + NoOfTrainees
				+ "]";
	}
	
	
}

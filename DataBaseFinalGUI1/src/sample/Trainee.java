package sample;

//traineeId int NOT NULL,
//coachId int NOT NULL,
//Fname varchar(20) DEFAULT NULL,
//Lname varchar(20) DEFAULT NULL,
//gender varchar(5) DEFAULT NULL,
//email varchar(20) DEFAULT NULL,
//PRIMARY KEY (traineeId),
//KEY coachId (coachId),

public class Trainee {
	private int traineeId;
	private int coachId;
	private String Fname;
	private String Lname;
	private String gender;
	private String email;

	public Trainee(int traineeId, int coachId, String fname, String lname, String gender, String email) {
		this.traineeId = traineeId;
		this.coachId = coachId;
		Fname = fname;
		Lname = lname;
		this.gender = gender;
		this.email = email;
	}

	public int getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(int traineeId) {
		this.traineeId = traineeId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Trainee [traineeId=" + traineeId + ", coachId=" + coachId + ", Fname=" + Fname + ", Lname=" + Lname
				+ ", gender=" + gender + ", email=" + email + "]";
	}

}

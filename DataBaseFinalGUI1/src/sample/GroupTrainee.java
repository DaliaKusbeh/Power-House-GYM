package sample;

public class GroupTrainee {

	private int traineeId;
	private String Fname;
	private String Lname;
	private String scheduleName;

	public GroupTrainee() {
		super();
	}

	public GroupTrainee(int traineeId, String fname, String lname, String scheduleName) {
		super();
		this.traineeId = traineeId;
		Fname = fname;
		Lname = lname;
		this.scheduleName = scheduleName;
	}

	public int getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(int traineeId) {
		this.traineeId = traineeId;
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

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	@Override
	public String toString() {
		return " [" + traineeId + ", " + Fname + ", " + Lname + ", " + scheduleName + "]";
	}

}

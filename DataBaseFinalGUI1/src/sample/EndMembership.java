package sample;

import java.util.Date;

public class EndMembership {

	private int traineeId;
	private String Fname;
	private String Lname;
	private String membershipType;
	private Date startDate;
	private Date endDate;

	public EndMembership(int traineeId, String fname, String lname, String membershipType, Date startDate,
			Date endDate) {
		super();
		this.traineeId = traineeId;
		Fname = fname;
		Lname = lname;
		this.membershipType = membershipType;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "EndMembership [trainrrId=" + traineeId + ", Fname=" + Fname + ", Lname=" + Lname + ", membershipType="
				+ membershipType + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}

package sample;

public class Mempership {
	
	private int membershipId;
	private int traineeId;
	private String membershipType;
	private String statuss;
	
	
	
	
	public Mempership() {
		super();
	}
	
	public Mempership(int membershipId, int traineeId, String membershipType, String statuss) {
		super();
		this.membershipId = membershipId;
		this.traineeId = traineeId;
		this.membershipType = membershipType;
		this.statuss = statuss;
	}
	public int getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}
	public int getTraineeId() {
		return traineeId;
	}
	public void setTraineeId(int traineeId) {
		this.traineeId = traineeId;
	}
	public String getMembershipType() {
		return membershipType;
	}
	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}
	public String getStatuss() {
		return statuss;
	}
	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}

	@Override
	public String toString() {
		return "Mempership [membershipId=" + membershipId + ", traineeId=" + traineeId + ", membershipType="
				+ membershipType + ", statuss=" + statuss + "]";
	}
	
	
	

}

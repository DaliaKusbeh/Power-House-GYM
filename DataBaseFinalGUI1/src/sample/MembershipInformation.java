package sample;

public class MembershipInformation {

	private int membershipId;
	private String membershipType;
	private String Duration;
	private String price;
	private int NoOfMempership;

	@Override
	public String toString() {
		return "MembershipInformation [membershipId=" + membershipId + ", membershipType=" + membershipType
				+ ", Duration=" + Duration + ", price=" + price + ", NoOfMempership=" + NoOfMempership + "]";
	}

	public MembershipInformation() {
		super();
	}

	public MembershipInformation(int membershipId, String membershipType, String duration, String price,
			int noOfMempership) {
		super();
		this.membershipId = membershipId;
		this.membershipType = membershipType;
		Duration = duration;
		this.price = price;
		NoOfMempership = noOfMempership;
	}

	public int getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getNoOfMempership() {
		return NoOfMempership;
	}

	public void setNoOfMempership(int noOfMempership) {
		NoOfMempership = noOfMempership;
	}

}
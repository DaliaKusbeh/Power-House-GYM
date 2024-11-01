package sample;

public class ClassOfPaymentInfo {
     
	private int traineeId;
	private String membershipType;
	private String amountRequired;
	private String amountPaid;
	private String remainingAmount;
	private String paidDate;
	
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
	public String getAmountRequired() {
		return amountRequired;
	}
	public void setAmountRequired(String amountRequired) {
		this.amountRequired = amountRequired;
	}
	public String getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(String remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	public String getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	public ClassOfPaymentInfo(int traineeId, String membershipType, String amountRequired, String amountPaid,
			String remainingAmount, String paidDate) {
		super();
		this.traineeId = traineeId;
		this.membershipType = membershipType;
		this.amountRequired = amountRequired;
		this.amountPaid = amountPaid;
		this.remainingAmount = remainingAmount;
		this.paidDate = paidDate;
	}
	
	
	
}

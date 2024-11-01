package sample;

public class TraineeJoinPayment {
	
	private int paymentId;
	private int membershipId;
	private String amountRequired;
	private String amountPaid;
	private String remainingAmount;
	private String paidDate;
	
	
	public TraineeJoinPayment(int paymentId, int membershipId, String amountRequired, String amountPaid,
			String remainingAmount, String paidDate) {
		super();
		this.paymentId = paymentId;
		this.membershipId = membershipId;
		this.amountRequired = amountRequired;
		this.amountPaid = amountPaid;
		this.remainingAmount = remainingAmount;
		this.paidDate = paidDate;
	}


	public TraineeJoinPayment() {
		super();
	}


	public int getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}


	public int getMembershipId() {
		return membershipId;
	}


	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
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


	@Override
	public String toString() {
		return "TraineeJoinPayment [paymentId=" + paymentId + ", membershipId=" + membershipId + ", amountRequired="
				+ amountRequired + ", amountPaid=" + amountPaid + ", remainingAmount=" + remainingAmount + ", paidDate="
				+ paidDate + "]";
	}
	
	
	
	

}

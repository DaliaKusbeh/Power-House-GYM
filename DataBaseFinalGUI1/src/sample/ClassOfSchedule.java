package sample;

public class ClassOfSchedule {
	
	private String Fname;
    private String Lname;
    private String scheduleName;
    private String days;
    private String stime;
    private String etime;    
    private int NoOfTrainee;
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
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public int getNoOfTrainee() {
		return NoOfTrainee;
	}
	public void setNoOfTrainee(int noOfTrainee) {
		NoOfTrainee = noOfTrainee;
	}
	public ClassOfSchedule(String fname, String lname, String scheduleName, String days, String stime, String etime,
			int noOfTrainee) {
		super();
		Fname = fname;
		Lname = lname;
		this.scheduleName = scheduleName;
		this.days = days;
		this.stime = stime;
		this.etime = etime;
		NoOfTrainee = noOfTrainee;
	}
	public ClassOfSchedule() {
		super();
	}
	@Override
	public String toString() {
		return "ClassOfSchedule [Fname=" + Fname + ", Lname=" + Lname + ", scheduleName=" + scheduleName + ", days="
				+ days + ", stime=" + stime + ", etime=" + etime + ", NoOfTrainee=" + NoOfTrainee + "]";
	}
    
    
	

}

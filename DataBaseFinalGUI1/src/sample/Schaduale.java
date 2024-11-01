package sample;

import java.util.Date;

public class Schaduale {
	private String scheduleName;
	private int scheduleId;
	private int traineeId;
	private String datee;
	private String startTime;
	private String endTime;

	
	
	public Schaduale(String scheduleName, int scheduleId,int traineeId, String datee, String startTime, String endTime) {
		super();
		this.scheduleName = scheduleName;
		this.scheduleId = scheduleId;
		this.datee = datee;
		this.startTime = startTime;
		this.endTime = endTime;
		this.traineeId= traineeId;
	}
	
	
	public Schaduale() {
		super();
	}


	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getDatee() {
		return datee;
	}
	public void setDatee(String datee) {
		this.datee = datee;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
//	public String getActivity() {
//		return activity;
//	}
//	public void setActivity(String activity) {
//		this.activity = activity;
//	}
     


	public int getTraineeId() {
		return traineeId;
	}


	public void setTraineeId(int traineeId) {
		this.traineeId = traineeId;
	}

	@Override
	public String toString() {
		return "Schaduale [scheduleName=" + scheduleName + ", scheduleId=" + scheduleId + ", datee=" + datee
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}


	
}

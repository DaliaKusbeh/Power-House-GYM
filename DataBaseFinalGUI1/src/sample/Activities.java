package sample;

public class Activities {

	private int scheduleId;
	private String activity;

	public Activities(int scheduleId, String activity) {
		super();
		this.scheduleId = scheduleId;
		this.activity = activity;
	}

	public Activities(String activity) {
		super();
		this.activity = activity;
	}

	public Activities() {
		super();
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	@Override
	public String toString() {
		return "Activities [scheduleId=" + scheduleId + ", activity=" + activity + "]";
	}

}

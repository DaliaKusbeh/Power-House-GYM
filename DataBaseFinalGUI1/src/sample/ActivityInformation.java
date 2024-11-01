package sample;

public class ActivityInformation {

	private int schedualeId;
	private String scheduleName;
	private String activity;

	public ActivityInformation() {
		super();
	}

	public ActivityInformation(int schedualeId, String scheduleName, String activity) {
		super();
		this.schedualeId = schedualeId;
		this.scheduleName = scheduleName;
		this.activity = activity;

	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getSchedualeId() {
		return schedualeId;
	}

	public void setSchedualeId(int schedualeId) {
		this.schedualeId = schedualeId;
	}

	@Override
	public String toString() {
		return "ActivityInformation [schedualeId=" + schedualeId + ", scheduleName=" + scheduleName + ", activity="
				+ activity + "]";
	}

}

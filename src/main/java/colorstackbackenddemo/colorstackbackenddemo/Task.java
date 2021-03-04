package colorstackbackenddemo.colorstackbackenddemo;

public class Task {

	private final long id;
	private final String content;
	private final String status;

	Task(long id, TaskRequest taskRequest) {
		this.id = id;
		this.content = taskRequest.getContent();
		this.status = taskRequest.getStatus();
	}

	Task(long id, String content, String status) {
		this.id = id;
		this.content = content;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getStatus() {
		return status;
	}
}

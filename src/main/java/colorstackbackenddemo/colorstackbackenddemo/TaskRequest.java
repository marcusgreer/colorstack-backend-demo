package colorstackbackenddemo.colorstackbackenddemo;

class TaskRequest {

	private final String content;
	private final String status;

	TaskRequest(String content, String status) {
		this.content = content;
		this.status = status;
	}

	String getContent() {
		return content;
	}

	String getStatus() {
		return status;
	}
}

package io.demo.restapi;

class TaskRequest {

	private final String content;
	private final String done;

	TaskRequest(String content, String done) {
		this.content = content;
		this.done = done;
	}

	String getContent() {
		return content;
	}

	String getDone() {
		return done;
	}
}

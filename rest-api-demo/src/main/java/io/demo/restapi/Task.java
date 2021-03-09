package io.demo.restapi;

public class Task {

	private final long id;
	private final String content;
	private final String done;

	Task(long id, TaskRequest taskRequest) {
		this.id = id;
		this.content = taskRequest.getContent();
		this.done = taskRequest.getDone();
	}

	Task(long id, String content, String done) {
		this.id = id;
		this.content = content;
		this.done = done;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getDone() {
		return done;
	}
}

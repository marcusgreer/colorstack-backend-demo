package io.demo.restapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	private static boolean USE_STORAGE = true;
	private static HashMap<Long, Task> TODO_LIST = USE_STORAGE ? PersistentStorage.read() : new HashMap<>();
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/tasks")
	public List<Task> getAllTasks(@RequestParam(value = "done", defaultValue = "all"  )String done)
	{
		if(USE_STORAGE) TODO_LIST = PersistentStorage.read();
		List<Task> taskList = new ArrayList<>(TODO_LIST.values());
		if (done.equals("all")) return taskList;
		return taskList.stream().filter(task -> done.equals(task.getDone())).collect(Collectors.toList());
	}

	@GetMapping("/tasks/{id}")
	public List<Task> getTask(@PathVariable Long id) {
		if(USE_STORAGE) TODO_LIST = PersistentStorage.read();
		return Collections.singletonList(TODO_LIST.get(id));
	}

	@PostMapping("/tasks")
	public List<Task> postTasks(@RequestBody TaskRequest taskRequest) {
		long key = counter.incrementAndGet();
		Task task = new Task(key, taskRequest);
		TODO_LIST.put(key, task);
		if(USE_STORAGE) PersistentStorage.write(TODO_LIST);
		return Collections.singletonList(task);
	}

	@PutMapping("/tasks/{id}")
	public List<Task> updateTasks(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
		TODO_LIST.put(id, new Task(id, taskRequest));
		if(USE_STORAGE) PersistentStorage.write(TODO_LIST);
		return Collections.singletonList(TODO_LIST.get(id));
	}

	@DeleteMapping("/tasks/{id}")
	public void deleteTasks(@PathVariable Long id) {
		TODO_LIST.remove(id);
		if(USE_STORAGE) PersistentStorage.write(TODO_LIST);
	}

}

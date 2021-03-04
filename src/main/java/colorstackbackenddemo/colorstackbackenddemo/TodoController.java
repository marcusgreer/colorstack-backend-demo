package colorstackbackenddemo.colorstackbackenddemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

	private static HashMap<Long, Task> TODO_LIST = new HashMap<>();
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/tasks")
	public List<Task> getAllTasks(@RequestParam(value = "size", defaultValue = "100") Long size,
	                             @RequestParam(value = "page", defaultValue = "1"  ) Long page,
	                             @RequestParam(value = "done", defaultValue = "true"  )String done)
	{
		return new ArrayList(TODO_LIST.values());
	}

	@GetMapping("/tasks/{id}")
	public List<Task> getTask(@PathVariable Long id) {
		return Collections.singletonList(TODO_LIST.get(id));
	}

	@PostMapping("/tasks")
	public List<Task> postTasks(@RequestBody TaskRequest taskRequest) {
		long key = counter.incrementAndGet();
		Task task = new Task(key, taskRequest);
		TODO_LIST.put(key, task);
		return Collections.singletonList(task);
	}

	@PutMapping("/tasks/{id}")
	public List<Task> updateTasks(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
		TODO_LIST.put(id, new Task(id, taskRequest));
		return Collections.singletonList(TODO_LIST.get(id));
	}

	@DeleteMapping("/tasks/{id}")
	public void deleteTasks(@PathVariable Long id) {
		TODO_LIST.remove(id);
	}

}

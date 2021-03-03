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

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/tasks")
	public ArrayList getAllTasks(@RequestParam(value = "size", defaultValue = "100") Long size,
	                             @RequestParam(value = "page", defaultValue = "1"  ) Long page,
	                             @RequestParam(value = "done", defaultValue = "true"  )String done)
	{
		return new ArrayList(TODO_LIST.values());
	}

	@GetMapping("/tasks/{id}")
	public Task getTask(@PathVariable Long id) {
		return TODO_LIST.get(id);
	}

	@PostMapping("/tasks")
	public List<Task> postTasks(@RequestParam(value = "name", defaultValue = "World") String name) {
		long key = counter.incrementAndGet();
		Task task = new Task(key, String.format(template, name), "unfinished");
		TODO_LIST.put(key, task);
		return Collections.singletonList(task);
	}

	@PutMapping("/tasks/{id}")
	public Task updateTasks(@PathVariable Long id, @RequestBody Task task) {
		TODO_LIST.put(id, task);
		return TODO_LIST.get(id);
	}

	@DeleteMapping("/tasks/{id}")
	public void deleteTasks(@PathVariable Long id) {
		TODO_LIST.remove(id);
	}

}

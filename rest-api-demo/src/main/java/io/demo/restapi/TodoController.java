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

	private static boolean USE_STORAGE = false;
	private static HashMap<Long, Task> TODO_LIST = USE_STORAGE ? PersistentStorage.read() : new HashMap<>();
	private final AtomicLong counter = new AtomicLong();


	/* GET /tasks
	 *
	 * Get the list of tasks
	 *
	 * When the "done" query parameter is omitted all tasks are shown.
	 * When it is added and given the value "true" or "false", the tasks that match that state will be filtered
	 *
	 *
	 * Example:
	 * curl http://localhost:3000/tasks?done="true"
	 *
	 */
	@GetMapping("/tasks")
	public List<Task> getAllTasks(@RequestParam(value = "done", defaultValue = "all"  )String done)
{
		if(USE_STORAGE) TODO_LIST = PersistentStorage.read();
		List<Task> taskList = new ArrayList<>(TODO_LIST.values());
		if (done.equals("all")) return taskList;
		return taskList.stream().filter(task -> done.equals(task.getDone())).collect(Collectors.toList());
	}

	/* GET /tasks/1/
	 *
	 * Get a specific task by ID.
	 *
	 * Example:
	 * curl http://localhost:3000/tasks/1
	 *
	 */
	@GetMapping("/tasks/{id}")
	public List<Task> getTask(@PathVariable Long id) {
		if(USE_STORAGE) TODO_LIST = PersistentStorage.read();
		return Collections.singletonList(TODO_LIST.get(id));
	}

	/* POST /tasks
	 *
	 * Create a new task.
	 *
	 * The request body should include
	 * a "content" field with a string value and
	 * a "done" field with a boolean value
	 *
	 * Example:
	 * curl --header "Content-Type: application/json" \
	 *   --request POST \
	 *   --data '{"content":"Do Homework","done":"false"}' \
	 *   http://localhost:3000/tasks
	 *
	 */
	@PostMapping("/tasks")
	public String postTasks(@RequestBody TaskRequest taskRequest) {
		long id = counter.incrementAndGet();
		Task task = new Task(id, taskRequest);
		TODO_LIST.put(id, task);
		if(USE_STORAGE) PersistentStorage.write(TODO_LIST);
		return "{ Task has been added with ID " + id + " }";
	}

	/* PUT /tasks/1/
	 *
	 * Upate an existing task
	 *
	 * The request body should include:
	 * a "content" field with a string value and
	 * a "done" field with a boolean value
	 *
	 * Example:
	 * curl --header "Content-Type: application/json" \
	 *   --request PUT \
	 *   --data '{"content":"Do Homework","done":"false"}' \
	 *   http://localhost:3000/tasks/1/
	 *
	 */
	@PutMapping("/tasks/{id}")
	public String updateTasks(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
		TODO_LIST.put(id, new Task(id, taskRequest));
		if(USE_STORAGE) PersistentStorage.write(TODO_LIST);
		return "{ Task " + id + "has been updated }";
	}

	/* DELETE /tasks/1/
	 *
	 * Delete a specific task by ID.
	 *
	 * Example:
	 * curl --request DELETE http://localhost:3000/tasks/1/
	 *
	 */
	@DeleteMapping("/tasks/{id}")
	public void deleteTasks(@PathVariable Long id) {
		TODO_LIST.remove(id);
		if(USE_STORAGE) PersistentStorage.write(TODO_LIST);
	}

}

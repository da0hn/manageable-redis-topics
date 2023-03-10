package org.da0hn.manageable.redis.topics.controllers;

import lombok.AllArgsConstructor;
import org.da0hn.manageable.redis.topics.config.TaskRegister;
import org.da0hn.manageable.redis.topics.messages.SimpleMessage;
import org.da0hn.manageable.redis.topics.publishers.impl.CreateLongTaskPublisher;
import org.da0hn.manageable.redis.topics.response.TaskData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

  private final CreateLongTaskPublisher createLongTaskPublisher;

  private final TaskRegister taskRegister;

  @PostMapping
  public ResponseEntity<SimpleMessage> create() {
    final var response = this.createLongTaskPublisher.publish();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<Void> cancel(@PathVariable("uuid") final String uuid) {

    this.taskRegister.removeTask(uuid);

    return ResponseEntity.ok(null);
  }

  @GetMapping
  public ResponseEntity<List<TaskData>> getAll() {

    final var tasks = this.taskRegister.entrySet().stream()
      .map(TaskData::of)
      .collect(Collectors.toList());

    return ResponseEntity.ok(tasks);
  }

}

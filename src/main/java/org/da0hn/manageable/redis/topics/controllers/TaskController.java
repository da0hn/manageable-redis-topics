package org.da0hn.manageable.redis.topics.controllers;

import lombok.AllArgsConstructor;
import org.da0hn.manageable.redis.topics.publishers.impl.CreateLongTaskPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

  private final CreateLongTaskPublisher createLongTaskPublisher;

  @PostMapping
  public ResponseEntity<Void> create() {
    this.createLongTaskPublisher.publish();
    return ResponseEntity.ok(null);
  }

}

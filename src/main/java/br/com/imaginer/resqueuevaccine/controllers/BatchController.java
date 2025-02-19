package br.com.imaginer.resqueuevaccine.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.imaginer.resqueuevaccine.models.Batch;
import br.com.imaginer.resqueuevaccine.services.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/batches")
@Tag(name = "Batch", description = "Batch REST operations")
public class BatchController {

  @Autowired
  private BatchService batchService;

  public BatchController(BatchService batchService) {
    this.batchService = batchService;
  }

  @Operation(summary = "Get all batches")
  @GetMapping
  public ResponseEntity<List<Batch>> getAll() {
    return ResponseEntity.ok(batchService.findAll());
  }

  @Operation(summary = "Get batch by ID")
  @GetMapping("/{id}")
  public ResponseEntity<Batch> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(batchService.findById(id));
  }

  @Operation(summary = "Create new batch")
  @PostMapping
  public ResponseEntity<Batch> create(@RequestBody Batch batch) {
    return ResponseEntity.ok(batchService.create(batch));
  }

  @Operation(summary = "Update existing batch")
  @PutMapping("/{id}")
  public ResponseEntity<Batch> update(@PathVariable UUID id, @RequestBody Batch batchData) {
    return ResponseEntity.ok(batchService.update(id, batchData));
  }

  @Operation(summary = "Delete batch by ID")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    batchService.delete(id);
  }
}
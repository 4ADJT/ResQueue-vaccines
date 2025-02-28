package br.com.imaginer.resqueuevaccine.services;

import br.com.imaginer.resqueuevaccine.dto.BatchRequest;
import br.com.imaginer.resqueuevaccine.models.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BatchService {

  Page<Batch> findAllByClinicId(UUID userId, Pageable pageable);
  Batch create(UUID userId, BatchRequest batch);
  Batch update(UUID userId, UUID id, Batch batchData);
  void delete(UUID userId, UUID id);
  String useBatch(UUID userId, UUID id, int quantity);
  Batch findById(UUID userId, UUID id);
  void updateBatchNotifyReason(Batch batch, String message);
}

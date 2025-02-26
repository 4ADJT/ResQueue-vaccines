package br.com.imaginer.resqueuevaccine.services;

import br.com.imaginer.resqueuevaccine.models.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BatchService {

  Page<Batch> findAllByClinicId(UUID clinicId, Pageable pageable);
  Batch create(Batch batch);
  Batch update(UUID id, Batch batchData);
  void delete(UUID id);
  String useBatch(UUID id, int quantity);
  Batch findById(UUID id);
}

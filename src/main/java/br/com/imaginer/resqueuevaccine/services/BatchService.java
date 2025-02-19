package br.com.imaginer.resqueuevaccine.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.imaginer.resqueuevaccine.models.Batch;
import br.com.imaginer.resqueuevaccine.repositories.BatchRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BatchService {

  @Autowired
  private BatchRepository batchRepository;

  public BatchService(BatchRepository batchRepository) {
    this.batchRepository = batchRepository;
  }

  public List<Batch> findAll() {
    return batchRepository.findAll();
  }

  public Batch findById(UUID id) {
    return batchRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Batch not found"));
  }

  public Batch create(Batch batch) {
    batch.setId(null);
    return batchRepository.save(batch);
  }

  public Batch update(UUID id, Batch batchData) {
    Optional<Batch> optional = batchRepository.findById(id);
    if (!optional.isPresent()) {
      return null;
    }
    Batch existing = optional.get();
    existing.setBatchNumber(batchData.getBatchNumber());
    existing.setVaccineType(batchData.getVaccineType());
    existing.setManufactureDate(batchData.getManufactureDate());
    existing.setExpiryDate(batchData.getExpiryDate());
    existing.setQuantity(batchData.getQuantity());
    existing.setAvailableQuantity(batchData.getAvailableQuantity());
    return batchRepository.save(existing);
  }

  public void delete(UUID id) {
    Batch batch = findById(id);
    batchRepository.delete(batch);
  }
}

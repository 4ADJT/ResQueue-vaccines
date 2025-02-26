package br.com.imaginer.resqueuevaccine.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.imaginer.resqueuevaccine.models.Batch;
import br.com.imaginer.resqueuevaccine.repositories.BatchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchServiceImpl implements BatchService {

  private final BatchRepository batchRepository;
  public BatchServiceImpl(BatchRepository batchRepository) {
    this.batchRepository = batchRepository;
  }

  @Override
  public Page<Batch> findAllByClinicId(UUID clinicId, Pageable pageable) {
    return batchRepository.findAllByClinicId(clinicId, pageable);
  }

  @Override
  public Batch findById(UUID id) {
    return batchRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Batch not found"));
  }

  @Override
  public Batch create(Batch batch) {
    return batchRepository.save(batch);
  }

  @Override
  public Batch update(UUID id, Batch batchData) {
    Optional<Batch> optional = batchRepository.findById(id);
    if (optional.isEmpty()) {
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

  @Override
  @Transactional
  public void delete(UUID id) {
    Batch batch = findById(id);

    batch.setEliminated(LocalDateTime.now());

    batchRepository.saveAndFlush(batch);
  }

  @Override
  public String useBatch(UUID id, int quantity) {
    Batch batch = findById(id);

    if(batch.getEliminated() != null) {
      return "Batch eliminated at the moment.";
    }

    if (batch.getAvailableQuantity() < quantity) {
      return "Not enough vaccines in batch";
    }
    batch.setAvailableQuantity(batch.getAvailableQuantity() - quantity);
    batchRepository.save(batch);
    return "Vaccines used";
  }
}

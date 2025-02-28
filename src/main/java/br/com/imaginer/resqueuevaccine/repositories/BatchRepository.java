package br.com.imaginer.resqueuevaccine.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imaginer.resqueuevaccine.models.Batch;

public interface BatchRepository extends JpaRepository<Batch, UUID> {
  Page<Batch> findAllByClinicId(UUID clinicId, Pageable pageable);
  List<Batch> findAllByNotifyReasonIsNull();
}

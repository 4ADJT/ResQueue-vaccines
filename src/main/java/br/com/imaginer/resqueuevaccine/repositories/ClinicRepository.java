package br.com.imaginer.resqueuevaccine.repositories;

import br.com.imaginer.resqueuevaccine.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClinicRepository extends JpaRepository<Clinic, UUID> {
  Clinic findClinicByUserId(UUID userId);

  Clinic findByClinicId(UUID clinicId);
}

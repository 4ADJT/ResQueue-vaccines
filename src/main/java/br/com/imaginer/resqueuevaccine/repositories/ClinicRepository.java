package br.com.imaginer.resqueuevaccine.repositories;

import br.com.imaginer.resqueuevaccine.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClinicRepository extends JpaRepository<Clinic, UUID> {

  @Query(value = "SELECT * FROM clinic WHERE active = true AND user_id = :userId AND clinic_id = :clinicId LIMIT 1",
      nativeQuery = true)
  Clinic findActiveClinicByUserIdAndClinicId(UUID clinicId, UUID userId);

  Optional<List<Clinic>> findByUserId(UUID userId);
}

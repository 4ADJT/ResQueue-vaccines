package br.com.imaginer.resqueuevaccine.services;

import br.com.imaginer.resqueuevaccine.dto.ClinicRequest;
import br.com.imaginer.resqueuevaccine.exception.ClinicAlreadyExistisException;
import br.com.imaginer.resqueuevaccine.exception.ClinicNotFoundException;
import br.com.imaginer.resqueuevaccine.exception.UnauthorizedUser;
import br.com.imaginer.resqueuevaccine.models.Clinic;
import br.com.imaginer.resqueuevaccine.repositories.ClinicRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClinicServiceImpl implements ClinicService {

  private final ClinicRepository clinicRepository;
  public ClinicServiceImpl(ClinicRepository clinicRepository) {
    this.clinicRepository = clinicRepository;
  }

  @Override
  @Transactional
  public Clinic create(ClinicRequest clinic) {

    Clinic clinicExists = this.findByUserIdAndClinicId(clinic.clinicId(), clinic.userId());

    if(clinicExists != null && clinicExists.isActive()) {
      throw new ClinicAlreadyExistisException();
    }

    Clinic createClinic = Clinic.builder()
        .clinicId(clinic.clinicId())
        .userId(clinic.userId())
        .active(true)
        .build();

    return clinicRepository.save(createClinic);
  }

  @Override
  public Clinic findByUserIdAndClinicId(UUID clinicId, UUID userId) {
    return clinicRepository.findActiveClinicByUserIdAndClinicId(clinicId, userId);
  }

  @Override
  @Transactional
  public Clinic deactivate(UUID clinicId, UUID userId) {
    Clinic clinic = clinicRepository.findActiveClinicByUserIdAndClinicId(clinicId, userId);

    if(clinic == null) {
      throw new ClinicNotFoundException();
    }

    if(!clinic.getUserId().equals(userId)) {
      throw new UnauthorizedUser();
    }

    if(!clinic.isActive()) {
      return clinic;
    }

    clinic.setActive(false);

    return clinicRepository.saveAndFlush(clinic);
  }

  @Override
  public Optional<List<Clinic>> getClinicsByUser(UUID userId) {

    return clinicRepository.findByUserId(userId);
  }

  @Override
  public Clinic getActiveClinicByUser(UUID userId) {
    Optional<List<Clinic>> clinics = this.getClinicsByUser(userId);

    return clinics.orElseThrow(ClinicNotFoundException::new)
        .stream()
        .filter(Clinic::isActive)
        .findFirst()
        .orElseThrow(ClinicNotFoundException::new);
  }

}

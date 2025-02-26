package br.com.imaginer.resqueuevaccine.services;

import br.com.imaginer.resqueuevaccine.exception.ClinicAlreadyExistisException;
import br.com.imaginer.resqueuevaccine.exception.ClinicNotFoundException;
import br.com.imaginer.resqueuevaccine.exception.UnauthorizedUser;
import br.com.imaginer.resqueuevaccine.models.Clinic;
import br.com.imaginer.resqueuevaccine.repositories.ClinicRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class ClinicServiceImpl implements ClinicService {

  private final ClinicRepository clinicRepository;
  public ClinicServiceImpl(ClinicRepository clinicRepository) {
    this.clinicRepository = clinicRepository;
  }

  @Override
  @Transactional
  public Clinic create(Clinic clinic) {

    Clinic clinicExists = this.findByUserId(clinic.getUserId());

    if(clinicExists != null) {
      throw new ClinicAlreadyExistisException();
    }

    return clinicRepository.save(clinic);
  }

  @Override
  public Clinic findByUserId(UUID userId) {
    return clinicRepository.findClinicByUserId(userId);
  }

  @Override
  @Transactional
  public Clinic update(UUID clinicId, UUID userId, boolean active) {
    Clinic clinic = clinicRepository.findByClinicId(clinicId);

    if(clinic == null) {
      throw new ClinicNotFoundException();
    }

    if(!clinic.getUserId().equals(userId)) {
      throw new UnauthorizedUser();
    }

    if(Objects.equals(clinic.isActive(), active)) {
      return clinic;
    }

    clinic.setActive(active);

    return clinicRepository.saveAndFlush(clinic);
  }

}

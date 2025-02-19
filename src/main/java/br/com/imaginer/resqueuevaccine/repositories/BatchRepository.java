package br.com.imaginer.resqueuevaccine.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imaginer.resqueuevaccine.models.Batch;

public interface BatchRepository extends JpaRepository<Batch, UUID> {
}

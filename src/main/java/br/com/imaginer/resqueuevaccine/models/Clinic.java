package br.com.imaginer.resqueuevaccine.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clinic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clinic {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "clinic_id", unique = true, nullable = false)
  private UUID clinicId;

  @Column(name = "user_id")
  private UUID userId;

  private boolean active;

  @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Batch> batches;

}

package br.com.imaginer.resqueuevaccine.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @Column(name = "clinic_id")
  private UUID clinicId;

  @Column(name = "user_id")
  private UUID userId;

  private boolean active;

}

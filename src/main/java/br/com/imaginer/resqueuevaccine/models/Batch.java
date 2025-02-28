package br.com.imaginer.resqueuevaccine.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "clinic_id", referencedColumnName = "clinic_id", nullable = false)
  @JsonBackReference
  private Clinic clinic;

  private String batchNumber;
  private String vaccineType;
  private Date manufactureDate;
  private Date expiryDate;
  private int quantity;
  private int availableQuantity;
  private String notifyReason;
  private LocalDateTime eliminated;

}

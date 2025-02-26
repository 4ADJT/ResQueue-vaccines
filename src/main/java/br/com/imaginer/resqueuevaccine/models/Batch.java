package br.com.imaginer.resqueuevaccine.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
  private UUID clinicId;
  private String batchNumber;
  private String vaccineType;
  private Date manufactureDate;
  private Date expiryDate;
  private int quantity;
  private int availableQuantity;
  private LocalDateTime eliminated;

}

package br.com.imaginer.resqueuevaccine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public record BatchRequest(
    @JsonProperty("batch_number") String batchNumber,
    @JsonProperty("vaccine_type") String vaccineType,
    @JsonProperty("manufacture_date") Date manufactureDate,
    @JsonProperty("expiry_date") Date expiryDate,
    @JsonProperty("quantity") int quantity,
    @JsonProperty("available_quantity") int availableQuantity
) {
}

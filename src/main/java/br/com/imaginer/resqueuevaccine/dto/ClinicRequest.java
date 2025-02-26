package br.com.imaginer.resqueuevaccine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ClinicRequest(

    @JsonProperty("clinic_id") UUID clinicId,

    @JsonProperty("user_id") UUID userId,

    boolean active
) {
}

package br.com.imaginer.resqueuevaccine.exception;

public class ClinicNotFoundException extends RuntimeException {
  public ClinicNotFoundException() {
    super("Clinic not found!");
  }
}

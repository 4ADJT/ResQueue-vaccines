package br.com.imaginer.resqueuevaccine.exception;

public class ClinicAlreadyExistisException extends RuntimeException {
  public ClinicAlreadyExistisException() {
    super("Clinic already exists, proceed to update status of you need.");
  }
}

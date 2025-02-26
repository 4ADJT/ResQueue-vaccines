package br.com.imaginer.resqueuevaccine.exception;

public class UnauthorizedUser extends RuntimeException {
  public UnauthorizedUser() {
    super("User unauthorized");
  }
}

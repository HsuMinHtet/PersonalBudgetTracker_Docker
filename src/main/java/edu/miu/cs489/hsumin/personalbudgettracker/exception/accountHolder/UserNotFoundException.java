package edu.miu.cs489.hsumin.personalbudgettracker.exception.accountHolder;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}

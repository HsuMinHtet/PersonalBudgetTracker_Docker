package edu.miu.cs489.hsumin.personalbudgettracker.exception.accountHolder;

public class AccountDuplicateException extends RuntimeException {
    public AccountDuplicateException(String message) {
        super(message);
    }
}

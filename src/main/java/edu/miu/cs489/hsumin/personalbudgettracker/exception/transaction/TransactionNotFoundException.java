package edu.miu.cs489.hsumin.personalbudgettracker.exception.transaction;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}

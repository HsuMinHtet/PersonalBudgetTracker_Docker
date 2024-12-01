package edu.miu.cs489.hsumin.personalbudgettracker.exception.category;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}

package com.example.weatherstation.exception;

import java.util.Date;

public record ErrorDetails(Date timestamp, String message, String details) {
}
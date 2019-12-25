package ua.com.rafael.doit.feature.model;

import lombok.Data;

@Data
public class CommandExecutingStatus {
    private final String message;
    private final boolean isSuccessful;
}

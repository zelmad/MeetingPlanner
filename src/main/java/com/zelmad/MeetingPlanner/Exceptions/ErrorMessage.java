package com.zelmad.MeetingPlanner.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
}



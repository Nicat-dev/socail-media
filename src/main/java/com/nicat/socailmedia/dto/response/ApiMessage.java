package com.nicat.socailmedia.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ApiMessage {
    private String message;
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private LocalDateTime date;
}
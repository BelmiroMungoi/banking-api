package com.bbm.banking.exception.handler;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class StandardErrorResponse {

    private int code;
    private HttpStatus status;
    private OffsetDateTime time;
    private String title;
    private String path;
    private List<ValidationError> fields;

}

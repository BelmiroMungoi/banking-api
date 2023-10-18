package com.bbm.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {

    private int responseCode;
    private HttpStatus responseStatus;
    private String responseMessage;
    private LocalDateTime createdAt;
    private AccountInfo accountInfo;
}

package com.upendra.portfolio.common.dto.email;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailResponse {

    private String to;

    private String subject;

    private String status;
}

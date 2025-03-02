package com.mercheazy.server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponseDto {
    private int id;
    private String url;
}

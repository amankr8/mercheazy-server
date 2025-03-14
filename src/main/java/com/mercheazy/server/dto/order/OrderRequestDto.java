package com.mercheazy.server.dto.order;

import lombok.Data;

@Data
public class OrderRequestDto {
    private int profileId;
    private int productId;
    private int quantity;
}

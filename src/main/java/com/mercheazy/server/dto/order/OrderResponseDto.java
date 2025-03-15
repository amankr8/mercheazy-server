package com.mercheazy.server.dto.order;

import com.mercheazy.server.entity.order.MerchOrder.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderResponseDto {
    private int id;
    private double totalPrice;
    private OrderStatus status;
    private int profileId;
    private List<OrderItemResponseDto> orderItems;
    private Date createDate;
    private Date updateDate;
}

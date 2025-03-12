package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.order.OrderItemRequestDto;
import com.mercheazy.server.dto.order.OrderRequestDto;
import com.mercheazy.server.dto.order.OrderResponseDto;
import com.mercheazy.server.entity.order.MerchOrder;
import com.mercheazy.server.entity.order.MerchOrder.OrderStatus;
import com.mercheazy.server.entity.order.MerchOrderItem;
import com.mercheazy.server.entity.product.Product;
import com.mercheazy.server.entity.user.AppUser;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.OrderRepository;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements com.mercheazy.server.service.OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        if (orderRequestDto.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty.");
        }

        MerchOrder merchOrder = MerchOrder.builder()
                .appUser(AuthUtil.getLoggedInUser())
                .status(OrderStatus.PENDING)
                .build();
        merchOrder = orderRepository.save(merchOrder);

        List<MerchOrderItem> merchOrderItems = saveOrderItems(orderRequestDto.getOrderItems(), merchOrder);

        merchOrder.setMerchOrderItems(merchOrderItems);
        double totalPrice = merchOrderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        merchOrder.setTotalPrice(totalPrice);

        return orderRepository.save(merchOrder).toOrderResponseDto();
    }

    private List<MerchOrderItem> saveOrderItems(List<OrderItemRequestDto> orderItemRequestDtos, MerchOrder merchOrder) {
        return orderItemRequestDtos.stream()
                .map(requestDtoOrderItem -> {
                    Product product = productRepository.findById(requestDtoOrderItem.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
                    return MerchOrderItem.builder()
                            .merchOrder(merchOrder)
                            .product(product)
                            .price(product.getSellPrice())
                            .quantity(requestDtoOrderItem.getQuantity())
                            .build();
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(int userId) {
        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return orderRepository.findByAppUserId(appUser.getId())
                .stream().map(MerchOrder::toOrderResponseDto).toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(int id, OrderStatus status) {
        MerchOrder merchOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        merchOrder.setStatus(status);
        return orderRepository.save(merchOrder).toOrderResponseDto();
    }
}

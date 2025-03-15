package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.order.OrderRequestDto;
import com.mercheazy.server.entity.cart.Cart;
import com.mercheazy.server.entity.cart.CartItem;
import com.mercheazy.server.entity.order.MerchOrder;
import com.mercheazy.server.entity.order.MerchOrder.OrderStatus;
import com.mercheazy.server.entity.order.MerchOrderItem;
import com.mercheazy.server.entity.product.Product;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.entity.user.Profile;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.order.OrderRepository;
import com.mercheazy.server.service.CartService;
import com.mercheazy.server.service.ProductService;
import com.mercheazy.server.service.UserService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements com.mercheazy.server.service.OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    @Override
    public MerchOrder placeOrder(OrderRequestDto orderRequestDto) {
        Profile orderProfile = validateAndReturnProfile(orderRequestDto.getProfileId());

        Product product = productService.getProductById(orderRequestDto.getProductId());
        if (productService.outOfStock(product.getId(), orderRequestDto.getQuantity())) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock.");
        }

        MerchOrder merchOrder = MerchOrder.builder()
                .profile(orderProfile)
                .merchOrderItems(new ArrayList<>())
                .totalPrice(0.0)
                .status(OrderStatus.PLACED)
                .build();
        merchOrder = orderRepository.save(merchOrder);

        MerchOrderItem merchOrderItem = MerchOrderItem.builder()
                        .product(product)
                        .merchOrder(merchOrder)
                        .quantity(orderRequestDto.getQuantity())
                        .price(product.getSellPrice())
                        .build();

        merchOrder.getMerchOrderItems().add(merchOrderItem);
        double totalPrice = merchOrderItem.getPrice() * merchOrderItem.getQuantity();
        merchOrder.setTotalPrice(totalPrice);
        return orderRepository.save(merchOrder);
    }

    @Override
    public MerchOrder checkoutOrderByProfileId(int profileId) {
        Profile orderProfile = validateAndReturnProfile(profileId);

        AuthUser authUser = AuthUtil.getLoggedInUser();
        Cart cart = cartService.getCartByUserId(authUser.getId());
        if (cart.getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty.");
        }

        MerchOrder merchOrder = MerchOrder.builder()
                .profile(orderProfile)
                .status(OrderStatus.PLACED)
                .build();
        merchOrder = orderRepository.save(merchOrder);

        List<MerchOrderItem> merchOrderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = productService.getProductById(cartItem.getProduct().getId());
            if (productService.outOfStock(product.getId(), cartItem.getQuantity())) {
                throw new IllegalArgumentException("Requested quantity exceeds available stock.");
            }

            MerchOrderItem merchOrderItem = MerchOrderItem.builder()
                    .merchOrder(merchOrder)
                    .product(product)
                    .price(product.getSellPrice())
                    .quantity(cartItem.getQuantity())
                    .build();
            merchOrderItems.add(merchOrderItem);
        }

        merchOrder.setMerchOrderItems(merchOrderItems);
        double totalPrice = merchOrderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        merchOrder.setTotalPrice(totalPrice);
        merchOrder = orderRepository.save(merchOrder);

        // Clear user cart
        cartService.clearCartById(cart.getId());
        return merchOrder;
    }

    private Profile validateAndReturnProfile(int profileId) {
        AuthUser loggedInUser = AuthUtil.getLoggedInUser();
        List<Profile> userProfiles = loggedInUser.getProfiles();
        Profile profile = userProfiles.stream().filter(it -> it.getId() == profileId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found."));

        if (profile.getAddress() == null || profile.getPhone() == null) {
            throw new IllegalArgumentException("Missing contact info.");
        }
        return profile;
    }

    @Override
    public List<MerchOrder> getOrdersByUser(int userId) {
        AuthUser authUser = userService.getUserById(userId);
        List<MerchOrder> userOrders = new ArrayList<>();
        for (Profile profile : authUser.getProfiles()) {
            userOrders.addAll(orderRepository.findByProfileId(profile.getId()));
        }
        return userOrders;
    }

    @Override
    public MerchOrder getOrderById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
    }

    private void validateAndUpdateStockOnOrder(MerchOrder merchOrder) {
        for (MerchOrderItem merchOrderItem : merchOrder.getMerchOrderItems()) {
            if (productService.outOfStock(merchOrderItem.getProduct().getId(), merchOrderItem.getQuantity())) {
                throw new IllegalArgumentException("Requested quantity exceeds available stock.");
            }
            productService.updateStock(merchOrderItem.getProduct().getId(), -merchOrderItem.getQuantity());
        }
    }

    private void updateStockOnCancel(MerchOrder merchOrder) {
        for (MerchOrderItem merchOrderItem : merchOrder.getMerchOrderItems()) {
            productService.updateStock(merchOrderItem.getProduct().getId(), -merchOrderItem.getQuantity());
        }
    }

    @Override
    public MerchOrder updateOrderStatus(int orderId, OrderStatus newStatus) {
        MerchOrder merchOrder = getOrderById(orderId);
        OrderStatus oldStatus = merchOrder.getStatus();
        switch (oldStatus) {
            case PLACED:
                switch (newStatus) {
                    case CREATED:
                        validateAndUpdateStockOnOrder(merchOrder);
                        break;
                    case SHIPPED, DELIVERED, CANCELLED:
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid status.");
                }
                break;
            case CREATED:
                switch (newStatus) {
                    case SHIPPED, DELIVERED:
                        break;
                    case CANCELLED:
                        updateStockOnCancel(merchOrder);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid status.");
                }
                break;
            case SHIPPED:
                switch (newStatus) {
                    case DELIVERED:
                        break;
                    case CANCELLED:
                        throw new IllegalArgumentException("Order cannot be cancelled after shipping.");
                    default:
                        throw new IllegalArgumentException("Invalid status.");
                }
                break;
            case DELIVERED:
                throw new IllegalArgumentException("Order is already delivered.");
            case CANCELLED:
                throw new IllegalArgumentException("Order is already cancelled.");
            default:
                throw new IllegalArgumentException("Invalid status.");
        }
        merchOrder.setStatus(newStatus);
        return orderRepository.save(merchOrder);
    }
}

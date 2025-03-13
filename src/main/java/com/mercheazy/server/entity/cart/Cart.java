package com.mercheazy.server.entity.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mercheazy.server.dto.cart.CartResponseDto;
import com.mercheazy.server.entity.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "au_id")
    @JsonManagedReference
    private AppUser appUser;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<CartItem> cartItems;

    public CartResponseDto toCartResponseDto() {
        return CartResponseDto.builder()
                .id(id)
                .cartItems(cartItems.stream().map(CartItem::toCartItemResponseDto).toList())
                .build();
    }
}

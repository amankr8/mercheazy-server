package com.mercheazy.server.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private int id;

    @Column(name = "a_house", nullable = false)
    private String house;

    @Column(name = "a_street", nullable = false)
    private String street;

    @Column(name = "a_city", nullable = false)
    private String city;

    @Column(name = "a_state", nullable = false)
    private String state;

    @Column(name = "a_zip", nullable = false)
    private String zip;

    @Column(name = "a_country", nullable = false)
    private String country;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Profile profile;
}

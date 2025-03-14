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
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ph_id")
    private int id;

    @Column(name = "ph_type", nullable = false)
    private Type type;

    @Column(name = "ph_country_code", nullable = false)
    private String countryCode;

    @Column(name = "ph_number", unique = true, nullable = false)
    private String number;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Profile profile;

    public enum Type {
        HOME, WORK, MOBILE
    }
}

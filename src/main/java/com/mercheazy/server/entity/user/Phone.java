package com.mercheazy.server.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mercheazy.server.entity.Country;
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

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cr_id")
    private Country country;

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

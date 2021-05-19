package com.example.demo.patient.model;

import com.example.demo.consultation.model.Consultation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String name;

    @Column(length = 512, nullable = false, unique = true)
    private String numberId;

    @Column(length = 512, nullable = false, unique = true)
    private String pnc;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(length = 1024)
    private String address;

    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;
}

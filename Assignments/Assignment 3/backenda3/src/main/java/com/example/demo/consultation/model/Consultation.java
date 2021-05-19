package com.example.demo.consultation.model;

import com.example.demo.patient.model.Patient;
import com.example.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="patient_id")
    @NotNull
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    @NotNull
    private User doctor;

    @Column(length = 2024)
    private String details;

}

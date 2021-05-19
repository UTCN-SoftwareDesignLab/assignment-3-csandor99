package com.example.demo.consultation;

import com.example.demo.consultation.model.Consultation;
import com.example.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {

    List<Consultation> findAllByDateEqualsAndDoctorEquals(LocalDateTime date, User doctor);
    List<Consultation> findAllByDoctorEquals(User doctor);
}

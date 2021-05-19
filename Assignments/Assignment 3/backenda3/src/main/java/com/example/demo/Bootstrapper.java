package com.example.demo;


import com.example.demo.consultation.ConsultationRepository;
import com.example.demo.consultation.model.Consultation;
import com.example.demo.patient.PatientRepository;
import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final PatientRepository patientRepository;

    private final ConsultationRepository consultationRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            consultationRepository.deleteAll();
            patientRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("cristi@email.com")
                    .username("cristi")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("cristi1@email.com")
                    .username("cristi1")
                    .password("WooHoo1!")
                    .roles(Set.of("SECRETARY"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("cristi2@email.com")
                    .username("cristi2")
                    .password("WooHoo1!")
                    .roles(Set.of("DOCTOR"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("cristi3@email.com")
                    .username("cristi3")
                    .password("WooHoo1!")
                    .roles(Set.of("DOCTOR"))
                    .build());
        }
    }
}

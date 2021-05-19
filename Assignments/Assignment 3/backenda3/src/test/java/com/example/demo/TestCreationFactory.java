package com.example.demo;

import com.example.demo.consultation.model.Consultation;
import com.example.demo.consultation.model.dto.ConsultationDTO;
import com.example.demo.patient.model.Patient;
import com.example.demo.patient.model.dto.PatientDTO;
import com.example.demo.user.dto.UserListDTO;
import com.example.demo.user.model.User;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Patient.class)) {
            supplier = TestCreationFactory::newPatient;
        } else if (cls.equals(PatientDTO.class)) {
            supplier = TestCreationFactory::newPatientDto;
        } else if (cls.equals(PatientDTO.class)) {
            supplier = TestCreationFactory::newConsultation;
        } else if (cls.equals(PatientDTO.class)) {
            supplier = TestCreationFactory::newConsultationDto;
        } else if (cls.equals(PatientDTO.class)) {
            supplier = TestCreationFactory::newUser;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .build();
    }

    private static Patient newPatient() {
        return Patient.builder()
                .id(randomLong())
                .name(randomString())
                .numberId(randomString())
                .pnc(randomString())
                .birthDate(createRandomDate(1900,2021))
                .address(randomString())
                .build();
    }

    private static PatientDTO newPatientDto() {
        return PatientDTO.builder()
                .id(randomLong())
                .name(randomString())
                .numberId(randomString())
                .pnc(randomString())
                .birthDate(createRandomDate(1900,2021))
                .address(randomString())
                .build();
    }

    private static User newUser(){
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    private static Consultation newConsultation() {
        return Consultation.builder()
                .id(randomLong())
                .date(randomDateTime())
                .patient(newPatient())
                .doctor(newUser())
                .details(randomString())
                .build();
    }

    private static ConsultationDTO newConsultationDto() {
        return ConsultationDTO.builder()
                .id(randomLong())
                .date(randomDateTime().toString())
                .details(randomString())
                .patientId(randomLong())
                .doctorId(randomLong())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static double randomDouble() {return new Random().nextDouble();}

    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static LocalDate createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        return LocalDate.of(year, month, day);
    }

    public static LocalDateTime randomDateTime() {
        LocalDateTime now = LocalDateTime.now();
        int year = 60 * 60 * 24 * 365;
        return now.plusSeconds((long) createRandomIntBetween(-2 * year, 2 * year));
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

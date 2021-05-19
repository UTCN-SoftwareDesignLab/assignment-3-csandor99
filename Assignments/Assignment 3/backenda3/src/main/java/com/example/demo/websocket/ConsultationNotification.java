package com.example.demo.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ConsultationNotification {
    String date;
    String patient;
}

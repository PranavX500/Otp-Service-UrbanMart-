package com.example.OTP_Service.Service;

import com.example.OTP_Service.DTO.FlagResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    private final KafkaTemplate<String, FlagResponse> kafkaTemplate;

    public EmailProducer(KafkaTemplate<String, FlagResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmailProducer(FlagResponse flagResponse){
        System.out.println(flagResponse);
        kafkaTemplate.send("Otp-Success-topic", flagResponse);
    }
}

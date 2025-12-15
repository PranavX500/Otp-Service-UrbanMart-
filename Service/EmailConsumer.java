package com.example.OTP_Service.Service;

import com.example.OTP_Service.DTO.OtpRequest;
import com.example.OTP_Service.DTO.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    private UserResponse latestUserResponse;

    @Autowired
    OtpManupalation otpManipulation;

    public UserResponse getLatestUserResponse() {
        return latestUserResponse;
    }

    @KafkaListener(topics = "Email-topic", groupId = "Otp-group")
    public void EmailId(OtpRequest otpRequest) {

        System.out.println("Received Email = " + otpRequest.getEmailId());


        UserResponse userResponse = new UserResponse(otpRequest.getEmailId());

        this.latestUserResponse = userResponse;

        otpManipulation.createAndSendOtp(userResponse);
    }
}


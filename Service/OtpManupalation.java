package com.example.OTP_Service.Service;

import com.example.OTP_Service.DTO.UserResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class   OtpManupalation{

    @Autowired
    private RedisTemplate<String, String> redis;

    @Autowired
    private EmailSender emailSender;

    private int generateOtp() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }
    public void createAndSendOtp(UserResponse userResponse) {

        try {
            int otp = generateOtp();
            System.out.println("Generated OTP = " + otp);

            String key = "otp:" + userResponse.getEmailId();
            System.out.println("Saving OTP in Redis...");

            redis.opsForValue().set(key, String.valueOf(otp), 5, TimeUnit.MINUTES);
            System.out.println("Saved OTP. Now sending email...");

            emailSender.sendMail(
                    userResponse.getEmailId(),
                    "Your OTP Code",
                    "Your OTP is: " + otp
            );

            System.out.println("Email SENT successfully!");

        } catch (Exception ex) {
            System.out.println("ERROR WHILE SENDING OTP EMAIL:");
            ex.printStackTrace();
        }
    }



    public boolean validateOtp(UserResponse userResponse,int userOtp) {
        String email=userResponse.getEmailId();
        String key = "otp:" + email;

        String storedOtp = redis.opsForValue().get(key);

        if (storedOtp == null) {
            return false; // expired or not found
        }


        return storedOtp.equals(String.valueOf(userOtp));
    }
}




package com.kiarie.votingsystemapi.Utils;

import java.util.Random;

public class OtpUtil {

    public static String generateOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}

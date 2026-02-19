package com.banking.masking;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MaskingServiceTest {

    static class User {
        private String email;
        private String phoneNumber;
        private String name;

        public User() {} 

        public User(String email, String phoneNumber, String name) {
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.name = name;
        }

        // Getters and setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @Test
    void testMasking() {
        MaskingProperties properties = new MaskingProperties();
        properties.setEnabled(true);
        properties.setFields(Arrays.asList("email", "phoneNumber"));
        properties.setMaskStyle(MaskingProperties.MaskStyle.PARTIAL);

        MaskingService maskingService = new MaskingService(properties);

        User user = new User("john.doe@gmail.com", "0712345678", "John Doe");

        User maskedUser = maskingService.mask(user);

        // Original should remain unmasked
        assertEquals("john.doe@gmail.com", user.getEmail());
        assertEquals("0712345678", user.getPhoneNumber());

        // Masked values should be changed
        System.out.println("Original email: " + user.getEmail());
        System.out.println("Masked email:   " + maskedUser.getEmail());

        System.out.println("Original phone: " + user.getPhoneNumber());
        System.out.println("Masked phone:   " + maskedUser.getPhoneNumber());
    }
}
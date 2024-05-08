package com.example.notificationservice.service;

import com.example.notificationservice.model.Notification;
import org.apache.commons.mail.*;

public class EmailSender {
    public static void sendEmail(Notification notification) {
        try {
            Email email = new HtmlEmail();

            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("ivonamaria14@gmail.com", "nu_asta_e_parola"));
            email.setSSLOnConnect(true);

            email.setFrom("ivonamaria14@gmail.com");
            email.addTo(notification.getUserEmail());
            email.setSubject("Payment Notification");

            String textContent;
            if (notification.getMessage().equals("Payment was successful!")) {
                textContent = "Dear Customer,\n\n"
                        + "Your payment has been successfully processed.\n"
                        + "Here are the details:\n"
                        + "ID: " + notification.getId() + "\n"
                        + "Message: " + notification.getMessage() + "\n"
                        + "Departure: " + notification.getDeparture() + "\n"
                        + "Destination: " + notification.getDestination() + "\n"
                        + "Number of Seats: " + notification.getNumberOfSeats() + "\n"
                        + "Total Paid Amount: " + notification.getPrice() + " " + notification.getCurrency() + "\n\n"
                        + "Thank you for choosing our service.\n\n"
                        + "Best regards,\n"
                        + "Flight Booking App";
            } else {
                textContent = "Dear Customer,\n\n"
                        + "Your payment hasn't been processed. Please try again!" + "\n\n"
                        + "Thank you for choosing our service.\n\n"
                        + "Best regards,\n"
                        + "Flight Booking App";
            }

            email.setMsg(textContent);

            email.send();

            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}


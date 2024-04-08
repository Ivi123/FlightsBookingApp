package org.example.paymentservice.config.payPal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.clientSecret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;
    // Bean definition for PayPal environment
    //   Returns an instance of PayPalEnvironment configured with the PayPal client ID and client secret.
    //    The environment is typically set to the PayPal sandbox mode for testing purposes.
    @Bean
    public PayPalEnvironment payPalEnvironment() {
        return new PayPalEnvironment.Sandbox(clientId, clientSecret);
    //        create and configure a PayPal HTTP client.
    //        The method returns an instance of PayPalHttpClient configured with the provided environment.
    //        The HTTP client is used to make requests to the PayPal API, such as processing payments and retrieving payment details.
    }

    @Bean
    public PayPalHttpClient payPalHttpClient(PayPalEnvironment environment) {
        return new PayPalHttpClient(environment);
    }
}
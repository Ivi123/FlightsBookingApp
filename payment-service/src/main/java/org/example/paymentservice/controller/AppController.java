package org.example.paymentservice.controller;

import org.example.paymentservice.model.WebRequest;
import org.example.paymentservice.service.PaymentProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
public class AppController {
    private static Logger log = LoggerFactory.getLogger(AppController.class);

    @Value("${stripe.api.publicKey}")
    private String publicKey;

    private final PaymentProcessingService paymentService;

    public AppController(PaymentProcessingService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Endpoint for the home page.
     *
     * @param paymentId The ID of the payment.
     * @param bookingId The ID of the booking.
     * @param amount    The amount of the payment.
     * @param email     The email associated with the payment.
     * @param model     The model for passing attributes to the view.
     * @return A Mono containing the view name.
     */
    @GetMapping("/")
    public Mono<String> home(@RequestParam(value = "paymentId", required = false, defaultValue = "") String paymentId,
                             @RequestParam(value = "bookingId", required = false, defaultValue = "") String bookingId,
                             @RequestParam(value = "amount", required = false, defaultValue = "0") String amount,
                             @RequestParam(value = "email", required = false, defaultValue = "") String email,
                             Model model) {
        if (amount == null) {
            amount = String.valueOf(0L); // Default value for amount if not provided
        }
        if (bookingId == null) {
            bookingId = ""; // Default value for bookingId if not provided
        }
        if (paymentId == null) {
            paymentId = ""; // Default value for paymentId if not provided
        }
        // Build the WebRequest object using the provided values
        WebRequest request = new WebRequest();
        request.setPaymentId(paymentId);
        request.setBookingId(bookingId);
        Long parseAmount = Double.valueOf(amount).longValue();
        request.setAmount(parseAmount);
        request.setEmail(email);
        // Add the Request object to the model
        model.addAttribute("request", request);
        return Mono.just("index");
    }


    @GetMapping("/error")
    public Mono<String> errorPage(Model model) {
        paymentService.processAndDisplayPayment();
        return Mono.just("error");
    }

    @GetMapping("/success")
    public Mono<String> successPage() {
        paymentService.processAndDisplayPayment();
        return Mono.just("success");
    }

    /**
     * Endpoint for handling form submission.
     *
     * @param request       The WebRequest object containing form data.
     * @param bindingResult The result of the form binding process.
     * @param model         The model for passing attributes to the view.
     * @return A Mono containing the view name.
     */
    @PostMapping("/")
    public Mono<String> showCard(@ModelAttribute WebRequest request,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return Mono.just("index");
        }
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", request.getAmount());
        model.addAttribute("email", request.getEmail());
        model.addAttribute("bookingId", request.getBookingId());
        model.addAttribute("paymentId", request.getPaymentId());
        return Mono.just("checkout");
    }
}

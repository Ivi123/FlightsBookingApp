// Initialize Stripe.js with public key
const stripe = Stripe(publicKey);

// Define the request object containing payment details
const request = {
    amount: amount,
    email: email,
    bookingId: bookingId,
    paymentId: paymentId
};

let elements;

// Initialize Stripe Elements and check payment status
initialize();
checkStatus();

// Event listener for form submission
document
    .querySelector("#payment-form")
    .addEventListener("submit", handleSubmit);

// Variables to store email address and payment intent ID
let emailAddress = '';
let paymentIntentID = '';

// Function to initialize Stripe Elements and fetch payment intent
async function initialize() {
    const response = await fetch("/create-payment-intent", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(request),
    });

    const {intentID, clientSecret} = await response.json();

    paymentIntentID = intentID;

    // Set appearance options for Elements
    const appearance = {
        theme: 'stripe',
    };
    elements = stripe.elements({appearance, clientSecret});

    // Create and mount the Link Authentication Element
    const linkAuthenticationElement = elements.create("linkAuthentication");
    linkAuthenticationElement.mount("#link-authentication-element");

    // Event listener for changes in Link Authentication Element
    linkAuthenticationElement.on('change', (event) => {
        emailAddress = event.value.email;
    });

    // Options for Payment Element layout and default values
    const paymentElementOptions = {
        layout: "tabs",
        defaultValues: {
            billingDetails: {
                email: request.email
            }
        }
    };

    // Create and mount the Payment Element
    const paymentElement = elements.create("payment", paymentElementOptions);
    paymentElement.mount("#payment-element");
}

// Function to handle form submission
async function handleSubmit(e) {
    e.preventDefault();
    setLoading(true);

    const {error} = await stripe.confirmPayment({
        elements,
        confirmParams: {
            return_url: window.location.origin + "/success",
            receipt_email: emailAddress
        },
    });

    if (error) {
        showMessageAndRedirect(error.message, "/error");
    } else {
        showMessageAndRedirect("Payment succeeded!", "/success");
    }

    setLoading(false);
}

// Function to check payment status
async function checkStatus() {
    const clientSecret = new URLSearchParams(window.location.search).get(
        "payment_intent_client_secret"
    );

    if (!clientSecret) {
        return;
    }

    const {paymentIntent} = await stripe.retrievePaymentIntent(clientSecret);

    switch (paymentIntent.status) {
        case "succeeded":
            showMessageAndRedirect("Payment succeeded!", "/success");
            break;
        case "processing":
            showMessageAndRedirect("Your payment is processing.", "/error");
            break;
        case "requires_payment_method":
            showMessageAndRedirect("Your payment was not successful, please try again.", "/error");
            break;
        default:
            showMessageAndRedirect("Something went wrong.", "/error");
            break;
    }
}

// Function to display a message and redirect
async function showMessageAndRedirect(messageText, redirectUrl) {
    const messageContainer = document.querySelector("#payment-message");

    messageContainer.classList.remove("hidden");
    messageContainer.textContent = messageText;

    setTimeout(function () {
        messageContainer.classList.add("hidden");
        messageContainer.textContent = "";
        window.location.href = redirectUrl; // Redirect user to specified URL after message display
    }, 1000);
}

// Function to set loading state
function setLoading(isLoading) {
    if (isLoading) {
        document.querySelector("#submit").disabled = true;
        document.querySelector("#spinner").classList.remove("hidden");
        document.querySelector("#button-text").classList.add("hidden");
    } else {
        document.querySelector("#submit").disabled = false;
        document.querySelector("#spinner").classList.add("hidden");
        document.querySelector("#button-text").classList.remove("hidden");
    }
}

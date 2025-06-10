public class CreditCard implements PaymentStrategy {
    private String cardNumber;

    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment(double amount, boolean isExpressShipping) {
        // Simulasi pembayaran
        System.out.println("Processing Credit Card Payment: " + amount);
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "CreditCard";
    }
}

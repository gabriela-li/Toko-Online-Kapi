public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public boolean pay(double amount, boolean isExpressShipping) {
        if (paymentStrategy == null) {
            System.out.println("Payment strategy not set.");
            return false;
        }
        return paymentStrategy.processPayment(amount, isExpressShipping);
    }

    public String getPaymentMethodName() {
        if (paymentStrategy == null) {
            return "No payment method";
        }
        return paymentStrategy.getPaymentMethodName();
    }
}

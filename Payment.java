public class Payment {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public boolean processPayment(double amount, boolean isExpressShipping) {
        return paymentStrategy.processPayment(amount, isExpressShipping);
    }

    public String getPaymentMethodName() {
        return paymentStrategy.getPaymentMethodName();
    }
}

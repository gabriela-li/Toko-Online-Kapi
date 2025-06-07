public interface PaymentStrategy {
    boolean processPayment(double amount, boolean isExpressShipping);
    String getPaymentMethodName();
    
}

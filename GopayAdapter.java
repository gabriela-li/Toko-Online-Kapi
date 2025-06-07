public class GopayAdapter implements PaymentStrategy {
    private GopayAPI gopay;

    public GopayAdapter(GopayAPI gopay) {
        this.gopay = gopay;
    }

    @Override
    public boolean processPayment(double amount, boolean isExpressShipping) {
        return gopay.makePayment(amount, isExpressShipping);
    }

    @Override
    public String getPaymentMethodName() {
        return "Gopay";
    }
}

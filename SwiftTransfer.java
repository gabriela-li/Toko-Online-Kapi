public class SwiftTransfer implements PaymentStrategy {
    private String accountNumber;

    public SwiftTransfer(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean processPayment(double amount, boolean isExpressShipping) {
        System.out.println("Processing Swift Transfer: " + amount);
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "SwiftTransfer";
    }

    public String getAccNum (){
        return this.accountNumber;
    }
}

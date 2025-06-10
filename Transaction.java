public class Transaction {
    private String transactionId;
    private String orderId;
    private String customerId;
    private String customerName;
    private double totalAmount;
    private String paymentMethod;
    private String status;
    private java.time.LocalDateTime transactionDate;
    private String deliveryAddress;
    private java.util.List<TransactionItem> items;

    // Constructor
    public Transaction(String transactionId, String orderId, String customerId, String customerName,
                       double totalAmount, String paymentMethod, String status,
                       java.time.LocalDateTime transactionDate, String deliveryAddress,
                       java.util.List<TransactionItem> items) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionDate = transactionDate;
        this.deliveryAddress = deliveryAddress;
        this.items = new java.util.ArrayList<>(items);
    }

    // Getters
    public String getTransactionId() {
        return transactionId; }
    public String getOrderId() {
        return orderId; }
    public String getCustomerId() {
        return customerId; }
    public String getCustomerName() {
        return customerName; }
    public double getTotalAmount() {
        return totalAmount; }
    public String getPaymentMethod() {
        return paymentMethod; }
    public String getStatus() {
        return status; }

    // Setters
    public void setStatus(String status) { this.status = status; }

    // Method untuk menampilkan detail transaksi
    public String getTransactionSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("\n=== DETAIL TRANSAKSI ===\n");
        summary.append("Transaction ID: ").append(transactionId).append("\n");
        summary.append("Order ID: ").append(orderId).append("\n");
        summary.append("Customer: ").append(customerName).append(" (").append(customerId).append(")\n");
        summary.append("Tanggal: ").append(transactionDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))).append("\n");
        summary.append("Status: ").append(status).append("\n");
        summary.append("Payment Method: ").append(paymentMethod).append("\n");
        summary.append("Alamat Pengiriman: ").append(deliveryAddress).append("\n");
        summary.append("\nItem yang dibeli:\n");

        for (TransactionItem item : items) {
            summary.append("- ").append(item.getProductName())
                    .append(" x").append(item.getQuantity())
                    .append(" @ Rp").append(String.format("%.0f", item.getUnitPrice()))
                    .append(" = Rp").append(String.format("%.0f", item.getTotalPrice())).append("\n");
        }

        summary.append("\nTotal Amount: Rp").append(String.format("%.0f", totalAmount));
        return summary.toString();
    }
}

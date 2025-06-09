public class TransactionManager {
    private static TransactionManager instance;
    private java.util.List<Transaction> transactions;
    private int transactionCounter = 1;

    private TransactionManager() {
        this.transactions = new java.util.ArrayList<>();
    }

    public static TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    // Method untuk menambah transaksi baru
    public void addTransaction(Order order, User user, String paymentMethod) {
        String transactionId = "TXN" + String.format("%05d", transactionCounter++);

        // Convert CartItem ke TransactionItem
        java.util.List<TransactionItem> transactionItems = new java.util.ArrayList<>();
        for (CartItem cartItem : order.getCart().getItems()) {
            transactionItems.add(new TransactionItem(
                    cartItem.getProduct().getId(),
                    cartItem.getProduct().getName(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice()
            ));
        }

        Transaction transaction = new Transaction(
                transactionId,
                order.getOrderId(),
                user.getUsername(),
                user.getFullName(), // Assuming User has getName() method
                order.getTotalPrice(),
                paymentMethod,
                order.getStatus(),
                order.getOrderDate(),
                order.getDeliveryAddress(),
                transactionItems
        );

        transactions.add(transaction);
        System.out.println("Transaksi berhasil disimpan dengan ID: " + transactionId);
    }

    // Method untuk update status transaksi
    public void updateTransactionStatus(String orderId, String newStatus) {
        for (Transaction transaction : transactions) {
            if (transaction.getOrderId().equals(orderId)) {
                transaction.setStatus(newStatus);
                System.out.println("Status transaksi " + transaction.getTransactionId() + " diupdate menjadi: " + newStatus);
                break;
            }
        }
    }

    // Method untuk admin melihat semua transaksi
    public void displayAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }

        System.out.println("\n====== LAPORAN TRANSAKSI ======");
        System.out.printf("%-10s %-15s %-15s %-12s %-15s %-10s\n",
                "TXN ID", "ORDER ID", "CUSTOMER", "TOTAL", "PAYMENT", "STATUS");
        System.out.println("=".repeat(85));

        for (Transaction transaction : transactions) {
            System.out.printf("%-10s %-15s %-15s Rp%-10.0f %-15s %-10s\n",
                    transaction.getTransactionId(),
                    transaction.getOrderId(),
                    transaction.getCustomerId(),
                    transaction.getTotalAmount(),
                    transaction.getPaymentMethod(),
                    transaction.getStatus());
        }
    }

    // Method untuk melihat detail transaksi tertentu
    public void displayTransactionDetail(String transactionId) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId().equals(transactionId)) {
                System.out.println(transaction.getTransactionSummary());
                return;
            }
        }
        System.out.println("Transaksi dengan ID " + transactionId + " tidak ditemukan.");
    }

    // Method untuk filter transaksi berdasarkan status
    public void displayTransactionsByStatus(String status) {
        java.util.List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> t.getStatus().equalsIgnoreCase(status))
                .collect(java.util.stream.Collectors.toList());

        if (filteredTransactions.isEmpty()) {
            System.out.println("Tidak ada transaksi dengan status: " + status);
            return;
        }

        System.out.println("\n====== TRANSAKSI DENGAN STATUS: " + status.toUpperCase() + " ======");
        for (Transaction transaction : filteredTransactions) {
            System.out.printf("%s | %s | %s | Rp%.0f\n",
                    transaction.getTransactionId(),
                    transaction.getCustomerId(),
                    transaction.getPaymentMethod(),
                    transaction.getTotalAmount());
        }
    }

    // Method untuk mendapatkan total revenue
    public double getTotalRevenue() {
        return transactions.stream()
                .filter(t -> "Paid".equalsIgnoreCase(t.getStatus()))
                .mapToDouble(Transaction::getTotalAmount)
                .sum();
    }

    // Method untuk mendapatkan semua transaksi
    public java.util.List<Transaction> getAllTransactions() {
        return new java.util.ArrayList<>(transactions);
    }
}
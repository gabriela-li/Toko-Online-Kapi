public class TransactionReportService {
    private TransactionManager transactionManager;

    public TransactionReportService() {
        this.transactionManager = TransactionManager.getInstance();
    }

    public void displayAllTransactions() {
        transactionManager.displayAllTransactions();
    }

    public void displayTransactionDetail(String transactionId) {
        transactionManager.displayTransactionDetail(transactionId);
    }

    public void displayTransactionsByStatus(String status) {
        transactionManager.displayTransactionsByStatus(status);
    }

    public double getTotalRevenue() {
        return transactionManager.getTotalRevenue();
    }

    public void displayFormattedRevenue() {
        double revenue = getTotalRevenue();
        System.out.printf("Total Keuntungan: Rp%.0f\n", revenue);
    }

    public void displayReportMenu() {
        System.out.println("\n=== LAPORAN TRANSAKSI ===");
        System.out.println("1. Lihat Semua Transaksi");
        System.out.println("2. Lihat Detail Transaksi");
        System.out.println("3. Filter berdasarkan Status");
        System.out.println("4. Total Keuntungan");
        System.out.print("Pilih: ");
    }
}

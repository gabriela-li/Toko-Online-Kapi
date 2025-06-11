import java.util.Scanner;

public class TransactionReportController {
    private TransactionReportService reportService;
    private Scanner scanner;

    public TransactionReportController() {
        this.reportService = new TransactionReportService();
        this.scanner = new Scanner(System.in);
    }

    public void handleTransactionReports() {
        reportService.displayReportMenu();
        int reportChoice = scanner.nextInt();
        scanner.nextLine();

        switch (reportChoice) {
            case 1 -> handleViewAllTransactions();
            case 2 -> handleViewTransactionDetail();
            case 3 -> handleFilterByStatus();
            case 4 -> handleTotalRevenue();
            default -> System.out.println("Pilihan tidak valid.");
        }
    }

    private void handleViewAllTransactions() {
        reportService.displayAllTransactions();
    }

    private void handleViewTransactionDetail() {
        System.out.print("Masukkan Transaction ID: ");
        String transactionId = scanner.nextLine();
        reportService.displayTransactionDetail(transactionId);
    }

    private void handleFilterByStatus() {
        System.out.print("Masukkan Status (Pending/Paid/Failed): ");
        String status = scanner.nextLine();
        reportService.displayTransactionsByStatus(status);
    }

    private void handleTotalRevenue() {
        reportService.displayFormattedRevenue();
    }
}
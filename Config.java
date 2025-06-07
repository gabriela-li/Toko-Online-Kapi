import java.time.LocalDate;

public class Config {
    //atribut config
    private String storeName = "Toko Online Kapi";
    private double taxRate = 0.1; // 10% pajak

    //getter
    public String getStoreName() {
        return storeName;
    }

    public double getTaxRate() {
        return taxRate;
    }

    //setter
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.toString(); //default format: YYYY-MM-DD
    }
}

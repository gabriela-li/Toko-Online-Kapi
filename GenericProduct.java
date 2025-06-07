//GenericProduct sebagai produk konkret untuk testing
public class GenericProduct extends Product {
    public GenericProduct(String id, String name, double price, int stock) {
        super(id, name, price, "Generic", stock);
    }

    @Override
    public String getDetails() {
        return "Generic product: " + name;
    }

    @Override
    public double getTotalPrice() {
        return price;
    }
}

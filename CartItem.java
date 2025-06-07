public class CartItem {
    //atribut-atribut yang dimiliki :
    private Product product;
    private int quantity;

    //constructor
    public CartItem (Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    //return total price dari produk2 yang ada pada keranjang
    //harga dari produknya * qty
    public double getTotalPrice (){
        return product.getTotalPrice()*this.quantity;
    }

    //return productnya aoa
    public Product getProduct() {
        return product;
    }

    //quantity
    public int getQuantity() {
        return quantity;
    }

    //ubah nilai quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

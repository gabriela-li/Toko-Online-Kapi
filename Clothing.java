public class Clothing extends Product{
    private String size;//sizenya brp
    private String material;//tipe material bajunya apa

    //id produk, nama produk, harga, stok, size, material
    public Clothing (String id, String name, double price, int stock, String size, String material){
        super(id, name, price,  "Clothing", stock);
        this.size = size;
        this.material = material;
    }

    @Override
    public String getDetails(){
        return String.format("Clothing: %s, Size: %s, Material: %s", name, size, material);
    }

    public double getTotalPrice(){
        return this.price;
    }
}

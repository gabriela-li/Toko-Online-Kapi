public class User {
    private String username;
    private String password;
    private String fullName;
    private String userType;
    private ShoppingCart cart;

    public User(String username, String password, String fullName, String userType) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.userType = userType;
        this.cart = new ShoppingCart(this); // setiap user punya 1 keranjang
    }

    public ShoppingCart getCart() {
        return this.cart;
    }

    public String getUserType() {
        return userType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }
}

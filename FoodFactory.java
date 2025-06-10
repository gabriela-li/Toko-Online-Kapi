public class FoodFactory extends ProductFactory{
    @Override
    public Food createProduct(String id, String name, double price, int stock, String [] params){
        String expirationDate = params[0];
        String weight = params[1];
        return new Food(id, name, price, stock, expirationDate, weight);
    }
}

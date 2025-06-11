public class ShippingOptions {
    private boolean isExpressShipping;
    private boolean isGiftWrap;

    public ShippingOptions(boolean isExpressShipping, boolean isGiftWrap) {
        this.isExpressShipping = isExpressShipping;
        this.isGiftWrap = isGiftWrap;
    }

    public boolean isExpressShipping() {
        return isExpressShipping;
    }

    public boolean isGiftWrap() {
        return isGiftWrap;
    }

    public double getShippingCost() {
        return isExpressShipping ? 15000 : 0;
    }

    public double getPackagingCost() {
        return isGiftWrap ? 10000 : 0;
    }

    public double getTotalAdditionalCost() {
        return getShippingCost() + getPackagingCost();
    }
}
package restaurant.org.tchadrestaurant.Models;


public class drinksModel {

    private String mName;
    private double mPrice;
    private int mImage;
    private int mQuantity;
    private String mDescription;

    public drinksModel(String name, double price, int image, int qua, String dsc) {

        mName = name;
        mPrice = price;
        mImage = image;
        mDescription = dsc;
        mQuantity = qua;

    }

    public String getdrinkName() {
        return mName;
    }

    public String getdrinkDesc() {
        return mDescription;
    }

    public double getdrinkPrice() {
        return mPrice;
    }

    public int getdrinkImage() {
        return mImage;
    }

    public int getdrinkQuantity() {
        return mQuantity;
    }

}

package restaurant.org.tchadrestaurant.Models;

/**
 * Created by tokmang on 7/20/2016.
 */
public class ItemsModel {

    private String mName;
    private double mPrice;
    private int mImage;
    private int mQuantity;
    private String mDescription;

    public ItemsModel(String name, double price, int image, String dsc) {

        mName = name;
        mPrice = price;
        mImage = image;
        mDescription = dsc;

    }

    public String getItemName() {
        return mName;
    }

    public String getItemDesc() {
        return mDescription;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getItemImage() {
        return mImage;
    }


 /*   public void setItemName(String name) {
        this.mName = name;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }

    public void setItemImage(int image) {
        this.mImage = image;
    }
    public void setItemDesc(String desc) {
        this.mDescription = desc;
    }

*/

}

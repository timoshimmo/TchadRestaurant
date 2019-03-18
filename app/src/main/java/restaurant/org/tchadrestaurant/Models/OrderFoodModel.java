package restaurant.org.tchadrestaurant.Models;

/**
 * Created by tokmang on 8/11/2016.
 */
public class OrderFoodModel {

        private String mName;
        private double mPrice;
        private int mQuantity;

        public OrderFoodModel() {

        super();

        }

        public String getItemName() {
            return mName;
        }

        public int getFoodQuantity() {
            return mQuantity;
        }

        public double getPrice() {
            return mPrice;
        }


    public void setItemName(String name) {
        this.mName = name;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }

    public void setItemQua(int fqua) {
        this.mQuantity = fqua;
    }



}

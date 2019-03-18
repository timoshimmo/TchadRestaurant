package restaurant.org.tchadrestaurant.Models;

/**
 * Created by tokmang on 8/10/2016.
 */
public class OrderDrinksModel {

        private String mName;
        private double mPrice;
        private int mQuantity;

        public OrderDrinksModel() {

            super();

        }

        public String getdrinkName() {
            return mName;
        }


        public double getdrinkPrice() {
            return mPrice;
        }


        public int getdrinkQuantity() {
            return mQuantity;
        }

    public void setdrinkName(String sdn) {
        this.mName = sdn;
    }


    public void setdrinkPrice(double dpr) {
        this.mPrice = dpr;
    }


    public void setdrinkQuantity(int dqua) {
        this.mQuantity = dqua;
    }

}

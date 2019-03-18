package restaurant.org.tchadrestaurant.Models;

/**
 * Created by tokmang on 8/11/2016.
 */
public class OrderModel {

    private String fName;
    private String dName;
    private double mtotPrice;
    private int fQuantity;
    private int dQuantity;
    private String tblNo;
    private int userID;

    public OrderModel() {

        super();

    }


    public void setfoodName(String fdName) {
        this.fName = fdName;
    }

    public void setdrinkName(String dkName) {
        this.dName = dkName;
    }


    public void setTotPrice(double totPr) {
        this.mtotPrice = totPr;
    }

    public void setdrinkQuantity(int drkQty) {
        this.dQuantity = drkQty;
    }

    public void setfoodQuantity(int fdQty) {
        this.fQuantity = fdQty;

    }

    public void setTblNo(String tNo) {
        this.tblNo = tNo;

    }

    public void setUserId(int uId) {
        this.userID = uId;

    }


    public String getfoodName() {
        return fName;
    }

    public String getdrinkName() {
        return dName;
    }

    public double getTotPrice() {
        return mtotPrice;
    }

    public int getdrinkQuantity() {
        return dQuantity;
    }

    public int getfoodQuantity() {
        return fQuantity;
    }

    public String getTablNo() {
        return tblNo;
    }

    public int getUserID() {
        return userID;
    }
}

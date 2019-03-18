package restaurant.org.tchadrestaurant.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

import restaurant.org.tchadrestaurant.Models.ItemsModel;
import restaurant.org.tchadrestaurant.Models.OrderDrinksModel;
import restaurant.org.tchadrestaurant.Models.OrderFoodModel;
import restaurant.org.tchadrestaurant.R;

/**
 * Created by tokmang on 8/11/2016.
 */
public class DrinksListAdapter extends RecyclerView.Adapter<DrinksListAdapter.ViewHolder>{


        // Store a member variable for the contacts
        private ArrayList<ItemsModel> mFoodItems;

        OrderDrinksModel drinksModel;

        int quantityValue;

        // Store the context for easy access
        private Context mContext;

        public DrinksListAdapter(Context ctx, ArrayList<ItemsModel> fdm) {

            this.mFoodItems = fdm;
            this.mContext = ctx;

        }

        private Context getContext() {
            return mContext;
        }

        @Override
        public DrinksListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View orderView = inflater.inflate(R.layout.order_list_layout, parent, false);

            return new ViewHolder(orderView);

        }

        @Override
        public void onBindViewHolder(DrinksListAdapter.ViewHolder holder, int position) {

            ItemsModel models = mFoodItems.get(position);

            drinksModel = new OrderDrinksModel();
            DecimalFormat df = new DecimalFormat("0.00");


            final TextView itemName = holder.itemNameTextView;
            TextView itemDesc = holder.itemDescTextView;
            final TextView itemPrice = holder.itemPriceTextView;

            ImageView imgFoodItems = holder.imgFood;
            ImageButton btnAddToOrder = holder.btnAdd;
            ImageButton btnCloseOrder = holder.btnClose;

            ImageButton addOrderItem = holder.addOrder;

            final RelativeLayout orderLayout = holder.bodyOrders;

            NumberPicker orderNumber = holder.numPicker;

            String itemPriceValue = df.format(models.getPrice());

            itemName.setText(models.getItemName());
            itemDesc.setText(models.getItemDesc());
            itemPrice.setText(itemPriceValue);

            if(Build.VERSION.SDK_INT > 21) {
                imgFoodItems.setImageDrawable(mContext.getResources().getDrawable(models.getItemImage(), null));
            }
            else {
                imgFoodItems.setImageDrawable(mContext.getResources().getDrawable(models.getItemImage()));
            }

            btnAddToOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (orderLayout.getVisibility() == View.GONE) {
                        orderLayout.setVisibility(View.VISIBLE);
                    }


                }
            });

            btnCloseOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderLayout.getVisibility() == View.VISIBLE) {
                        orderLayout.setVisibility(View.GONE);
                    }
                }
            });

            orderNumber.setMinValue(0);
            //Specify the maximum value/number of NumberPicker
            orderNumber.setMaxValue(10);

            orderNumber.setWrapSelectorWheel(true);

            orderNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    //Display the newly selected number from picker
                    // tv.setText("Selected Number : " + newVal);

                    if(newVal == 0) {
                        drinksModel.setdrinkQuantity(1);
                    }
                    else {
                        drinksModel.setdrinkQuantity(newVal);
                    }


                }
            });

            addOrderItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    double ordPrice = Double.valueOf(itemPrice.getText().toString());

                    drinksModel.setdrinkName(itemName.getText().toString());
                    drinksModel.setdrinkPrice(ordPrice);

                    SharedPreferences prefs = mContext.getSharedPreferences("DRINK_PREFERENCES", Context.MODE_PRIVATE);
                    JSONArray jsonArray = new JSONArray();

                    try {
                        jsonArray.put(drinksModel.getdrinkName());
                        jsonArray.put(drinksModel.getdrinkPrice());
                        jsonArray.put(drinksModel.getdrinkQuantity());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("DRINK_ORDER", jsonArray.toString());
                    System.out.println(jsonArray.toString());
                    editor.apply();

                    drinksModel.setdrinkName(itemName.getText().toString());
                    drinksModel.setdrinkPrice(ordPrice);
                    drinksModel.setdrinkQuantity(quantityValue);

                    Toast.makeText(mContext.getApplicationContext(), drinksModel.getdrinkName(), Toast.LENGTH_LONG).show();
                }
            });

        }


        @Override
        public int getItemCount() {
            return mFoodItems.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView itemNameTextView;
            public TextView itemPriceTextView;
            public TextView itemDescTextView;
            public ImageView imgFood;

            public ImageButton btnClose;
            public ImageButton btnAdd;
            public RelativeLayout bodyOrders;
            public NumberPicker numPicker;

            public ImageButton addOrder;

            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                itemNameTextView = (TextView) itemView.findViewById(R.id.txtItemName);
                itemPriceTextView = (TextView) itemView.findViewById(R.id.txtItemPrice);
                itemDescTextView = (TextView) itemView.findViewById(R.id.txtItemDesc);

                imgFood = (ImageView) itemView.findViewById(R.id.imgItemImage);

                btnClose = (ImageButton) itemView.findViewById(R.id.btnCloseOrderView);
                btnAdd = (ImageButton) itemView.findViewById(R.id.btnSelectOrder);

                bodyOrders = (RelativeLayout) itemView.findViewById(R.id.orderBody);

                numPicker = (NumberPicker) itemView.findViewById(R.id.orderPicker);

                addOrder = (ImageButton) itemView.findViewById(R.id.btnAddItem);

            }
        }

}

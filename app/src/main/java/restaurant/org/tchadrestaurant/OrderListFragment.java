package restaurant.org.tchadrestaurant;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import restaurant.org.tchadrestaurant.Models.OrderModel;


public class OrderListFragment extends DialogFragment {

    Dialog d;

    TextView foodName;
    TextView foodQty;
    TextView foodPrice;

    TextView drinkName;
    TextView drinkQty;
    TextView drinkPrice;

    TextView totPrice;

    OrderModel ordModels;

    private OrderTask mAuthTask = null;

    private View mProgressView;
    private View mOrderView;


    public OrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View orderView = inflater.inflate(R.layout.fragment_order_list, container, false);

        Button btnCancel = (Button) orderView.findViewById(R.id.btnOrderCancel);
        Button btnConfirm = (Button) orderView.findViewById(R.id.btnOrderConfirm);

        DecimalFormat df = new DecimalFormat("0.00");

        foodName = (TextView) orderView.findViewById(R.id.txtFoodNameValue);
        foodQty = (TextView) orderView.findViewById(R.id.txtFoodQuantity);
        foodPrice = (TextView) orderView.findViewById(R.id.txtFoodPrice);

        drinkName = (TextView) orderView.findViewById(R.id.txtDrinksValue);
        drinkQty = (TextView) orderView.findViewById(R.id.txtDrinkQuantity);
        drinkPrice = (TextView) orderView.findViewById(R.id.txtDrinkPrice);

        totPrice = (TextView) orderView.findViewById(R.id.txtTotPrice);

        Spinner spnTableNo = (Spinner) orderView.findViewById(R.id.spnTableNo);

        mProgressView = orderView.findViewById(R.id.order_progress);
        mOrderView = orderView.findViewById(R.id.ordView);

        ordModels = new OrderModel();

        SharedPreferences myFoodItems = getActivity().getSharedPreferences("FOOD_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences myDrinks = getActivity().getSharedPreferences("DRINK_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences _userid = getActivity().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        try {

            JSONArray foodArray = new JSONArray(myFoodItems.getString("FOOD_ORDER", "[]"));
            JSONArray drinkArray = new JSONArray(myDrinks.getString("DRINK_ORDER", "[]"));
            int userId = _userid.getInt("userId", 0);

            Double foodPriceFigure = foodArray.getDouble(1);
            Double drinkPriceFigure = drinkArray.getDouble(1);
            Double totPriceFigure = foodArray.getDouble(1) *  foodArray.getInt(2) + drinkArray.getDouble(1) * drinkArray.getInt(2);

            String foodPriceValue = df.format(foodPriceFigure);
            String drinkPriceValue = df.format(drinkPriceFigure);

            String totPriceValue = df.format(totPriceFigure);

            foodName.setText(foodArray.getString(0));
            foodQty.setText(String.valueOf(foodArray.getInt(2)));
            foodPrice.setText(foodPriceValue);

            drinkName.setText(drinkArray.getString(0));
            drinkQty.setText(String.valueOf(drinkArray.getInt(2)));
            drinkPrice.setText(drinkPriceValue);

            totPrice.setText("RM" + totPriceValue);

            ordModels.setfoodName(foodArray.getString(0));
            ordModels.setfoodQuantity(foodArray.getInt(2));
            ordModels.setdrinkName(drinkArray.getString(0));
            ordModels.setdrinkQuantity(drinkArray.getInt(2));
            ordModels.setTotPrice(totPriceFigure);
            ordModels.setUserId(userId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        spnTableNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ordModels.setTblNo(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                ordModels.setTblNo("");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptAddOrder();

            }
        });

        return orderView;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("dialogsOrder");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    public String POST(HashMap<String, String> parameters) {

        InputStream inputStream;
        String result = "";
        String link = "http://restaurant-webservices.net16.net/webservices/addOrder.php";

        try {

            URL url = new URL(link);
            // create HttpURLConnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestProperty("Accept-Encoding", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getPostDataString(parameters));

            writer.flush();
            writer.close();
            outputStream.close();

            // receive response as inputStream
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            result = convertInputStreamToString(inputStream);

            inputStream.close();

        } catch (Exception e) {
            Log.d("InputStream", "" + e.getLocalizedMessage());
            System.out.println("Error InputStream" + e.getLocalizedMessage());
        }



        return result;
    }

    public void attemptAddOrder() {
        if (mAuthTask != null) {
            return;
        }

        HashMap<String, String> mOrderInfo = new HashMap<>();

        mOrderInfo.put("fdname", ordModels.getfoodName());
        mOrderInfo.put("fQuantity", String.valueOf(ordModels.getfoodQuantity()));
        mOrderInfo.put("dkname", ordModels.getdrinkName());
        mOrderInfo.put("dQuantity", String.valueOf(ordModels.getdrinkQuantity()));
        mOrderInfo.put("tableNo", ordModels.getTablNo());
        mOrderInfo.put("totPrice", String.valueOf(ordModels.getTotPrice()));
        mOrderInfo.put("userId", String.valueOf(ordModels.getUserID()));

        showProgress(true);
        mAuthTask = new OrderTask(mOrderInfo);
        mAuthTask.execute((Void) null);


    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = "";

            while ((line = bufferedReader.readLine()) != null)
                sb.append(line);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();

    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public class OrderTask extends AsyncTask<Void, Void, String> {

        private final HashMap<String, String> mOrderData;

        OrderTask(HashMap<String, String> mData) {
            mOrderData = mData;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String data = POST(mOrderData);
            return data;
        }

        @Override
        protected void onPostExecute(final String success) {

            String message = "";

            try {

                JSONObject responseObj = new JSONObject(success);
                message = responseObj.getString("msg");

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

            mAuthTask = null;
            showProgress(false);
            Dismiss();

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mOrderView.setVisibility(show ? View.GONE : View.VISIBLE);
            mOrderView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mOrderView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mOrderView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

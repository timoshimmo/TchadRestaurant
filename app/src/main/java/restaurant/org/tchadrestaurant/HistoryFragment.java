package restaurant.org.tchadrestaurant;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;


public class HistoryFragment extends Fragment {

    private HistoryTask mAuthTask = null;
    private View mProgressView;
    private RecyclerView mListView;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        mListView = (RecyclerView) view.findViewById(R.id.recyclerHistory);
        mProgressView = view.findViewById(R.id.history_progress);

        getOrderHistory();

        return view;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mListView.setVisibility(show ? View.GONE : View.VISIBLE);
            mListView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mListView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mListView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public String POST(HashMap<String, String> parameters) {

        InputStream inputStream;
        String result = "";
        String link = "http://restaurant-webservices.net16.net/webservices/getHistory.php";

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

    public void getOrderHistory() {
        if (mAuthTask != null) {
            return;
        }

        HashMap<String, String> mUserInfo = new HashMap<>();

        SharedPreferences _userid = getActivity().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        int userId = _userid.getInt("userId", 0);

        mUserInfo.put("userId", String.valueOf(userId));

        showProgress(true);
        mAuthTask = new HistoryTask(mUserInfo);
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
            // String result = "";
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


    public class HistoryTask extends AsyncTask<Void, Void, String> {

        private final HashMap<String, String> mUserData;

        HistoryTask(HashMap<String, String> mData) {
            mUserData = mData;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String response = "";
            String data = POST(mUserData);

            try {
                JSONObject responseObj = new JSONObject(data);
                response = responseObj.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(final String success) {
            mAuthTask = null;
            showProgress(false);

            Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


}

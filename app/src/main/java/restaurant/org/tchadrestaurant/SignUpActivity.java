package restaurant.org.tchadrestaurant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Build;

import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import com.android.volley.AuthFailureError;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends Activity {

    Button btnSignup;
    private EditText mPasswordView;
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mEmailView;
    private EditText mMobileView;

    private View mProgressView;
    private View mSignupFormView;

    private static String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mFirstNameView = (EditText) findViewById(R.id.eTxtFName);
        mLastNameView = (EditText) findViewById(R.id.eTxtLName);
        mEmailView = (EditText) findViewById(R.id.eTxtEmail);
        mPasswordView = (EditText) findViewById(R.id.eTxtPassword);
        mMobileView = (EditText) findViewById(R.id.eTxtMobile);
        mSignupFormView = findViewById(R.id.signupForm);

        mProgressView = findViewById(R.id.login_progress);

        btnSignup = (Button) findViewById(R.id.btnSignUp);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isConnected()) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
                    dialogBuilder.setTitle("No Internet Connection")
                            .setMessage("Internet connection is not available")
                            .setCancelable(true)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog dlg = dialogBuilder.create();
                    dlg.show();
                } else {
                    attemptSignup();
                }
            }
        });

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, IntroActivity.class);
                startActivity(intent);
                SignUpActivity.this.finish();
            }
        });

    }

    private void makeJsonObjectRequest(final String firstname, final String lastname, final String email, final String password, final String mobile) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "http://restaurant-webservices.net16.net/webservices/addUser.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject resObj;

                try {

                    resObj = new JSONObject(response);
                    String status = String.valueOf(resObj.getInt("status"));
                   // Toast.makeText(SignUpActivity.this, status, Toast.LENGTH_LONG).show();

                    if (status.equals("1")) {

                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                showProgress(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                showProgress(false);
            }
        }) {

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                params.put("firstName",firstname);
                params.put("lastName",lastname);
                params.put("email", email);
                params.put("password", password);
                params.put("mobileNo", mobile);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }


        };

        requestQueue.add(stringRequest);

    }

    public void attemptSignup() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String fName = mFirstNameView.getText().toString();
        String lName = mLastNameView.getText().toString();
        String emails = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String mobile = mMobileView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emails)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(emails)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            showProgress(true);
            makeJsonObjectRequest(fName, lName, emails, password, mobile);

        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
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

            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignupFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

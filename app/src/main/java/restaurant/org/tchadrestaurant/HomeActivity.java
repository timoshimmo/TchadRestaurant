package restaurant.org.tchadrestaurant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import restaurant.org.tchadrestaurant.Adapters.FoodListAdapter;
import restaurant.org.tchadrestaurant.Models.ItemsModel;
import restaurant.org.tchadrestaurant.Models.OrderDrinksModel;
import restaurant.org.tchadrestaurant.Models.OrderFoodModel;


public class HomeActivity extends AppCompatActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    Activity homeThis;

    Button makeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);

        homeThis = HomeActivity.this;

        View headerLayout = nvDrawer.inflateHeaderView(R.layout.header_layout);
// We can now look up items within the header if needed
        TextView fullName = (TextView)  headerLayout.findViewById(R.id.txtFullName);
        TextView email = (TextView) headerLayout.findViewById(R.id.txtEmail);

        HomeFragment frag = HomeFragment.newInstance(0);

        fullName.setText("Timothy Wang");
        email.setText("timoshimmo@yahoo.com");
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        makeOrder = (Button) toolbar.findViewById(R.id.btnMakeOrder);

        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
       mDrawer.addDrawerListener(drawerToggle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, frag).commit();

        FoodListAdapter.orderVisible(makeOrder);


        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tag = "dialogsOrder";

                FragmentManager fragmentManager = getSupportFragmentManager();
                OrderListFragment orderFragment = new OrderListFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                transaction.add(android.R.id.content, orderFragment, tag)
                        .addToBackStack(null).commit();

           /*

*/
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = ProfileFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = HistoryFragment.class;
                break;
            case R.id.nav_fourth_fragment:
                fragmentClass = FavouritesFragment.class;
                break;
            case R.id.location_function:
                fragmentClass = MenuFragment.class;
                break;
            case R.id.logout_function:
                finish();
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                System.exit(0);
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    public Activity getHomeActivity() {
        return homeThis;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class HomeFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        protected ArrayList<ItemsModel> allFoodData;

        protected String[] title;
        protected String[] description;
        protected int[] fdImages;
        protected double[] price;

        ItemsModel itmModel;

        private View mProgressView;
        private RecyclerView recyclerView;

        private FoodItemsTask mAuthTask = null;

        OrderFoodModel foodModel;
        OrderDrinksModel drinkModel;

        private ArrayList<OrderFoodModel> mFoodOrder;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static HomeFragment newInstance(int sectionNumber) {
            HomeFragment fragment = new HomeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public HomeFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Initialize dataset, this data would usually come from a local content provider or
            // remote server.
            initDataset();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            recyclerView = (RecyclerView) rootView.findViewById(R.id.rvMeals);
            // Set layout manager to position the items
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            recyclerView.setHasFixedSize(true);

            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
            recyclerView.addItemDecoration(itemDecoration);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

            FoodListAdapter adpter = new FoodListAdapter(getActivity(), allFoodData);
            recyclerView.setAdapter(adpter);


            FloatingActionButton startDrinks = (FloatingActionButton) rootView.findViewById(R.id.fabGetDrinks);

            mProgressView = rootView.findViewById(R.id.home_progress);

            startDrinks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DrinksFragment frag = DrinksFragment.newInstance(0);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, frag).commit();

                }
            });

            getFoodItems();

            return rootView;
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

                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                recyclerView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
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
                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }



        public String makeRequest() {
            InputStream inputStream = null;
            String result = "";
            String link = "http://restaurant-webservices.net16.net/webservices/getfood.php";

            try {

                URL url = new URL(link);
                // create HttpURLConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("Accept-Encoding", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoInput(true);

                urlConnection.setRequestMethod("POST");


                // receive response as inputStream
                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                // convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";


            } catch (Exception e) {
                Log.d("InputStream", "" + e.getLocalizedMessage());
            }

            return result;
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

        public void getFoodItems() {

            if (mAuthTask != null) {
                return;
            }

            showProgress(true);
            mAuthTask = new FoodItemsTask();
            mAuthTask.execute((Void) null);


        }



        private void initDataset() {

            allFoodData = new ArrayList<>();

            title = new String[] {"Mongolian beef", "Chow Mein (Chinese Noodles)", "Beef and Broccoli", "Rice with KungPao Chicken",
                    "General Tsao's Chicken", "Honey walnut shrimp", "Fried Rice"};
            description = new String[] {"Delicious Mongolian beef, with silky and tender beef in a rich and savory Chinese brown sauce",
                    "Fried noodles with shredded vegetables, shrimp and pork",
                    "beef stir-fry dish with broccoli, slathered in a rich brown sauce",
                    "Chinese chicken in savory and spicy Kung Pao sauce. KungPao Sauce made from Sichuan peppercorn",
                    "Original Hunan General Tsao's chicken made with dried red chilies and chinese rice vinegar",
                    "Crunchy shrimp with honey, candied glazed walnut and sweet mayonnaise",
                    "Delicious Chinese fried rice recipe with rice, eggs, chicken, and shrimp."};
            fdImages = new int[] {R.mipmap.mongolian_bf, R.mipmap.chow_mein, R.mipmap.broccoli_beef, R.mipmap.kung_pao_chicken,
                    R.mipmap.general_tso_chicken, R.mipmap.honey_walnut_shrimp, R.mipmap.friedrice};
            price = new double[] {13.40, 11.50, 12.30, 12.50, 10.50, 14.30, 12.50};

            for(int i=0; i < title.length; i++) {

                itmModel = new ItemsModel(title[i], price[i], fdImages[i], description[i]);
                allFoodData.add(i, itmModel);
            }
        }

        public class FoodItemsTask extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... params) {
                // TODO: attempt authentication against a network service.

                String data = makeRequest();
               // String response = "";

                /*JSONArray responseArray = null;

                try {
                    responseArray = new JSONArray(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                return data;
            }

            @Override
            protected void onPostExecute(final String success) {
                mAuthTask = null;
                showProgress(false);

                //if (success != null) {

                 // Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();
               // } else {
                 //   Toast.makeText(getActivity(), "Response is empty!", Toast.LENGTH_LONG).show();
             //   }
            }

            @Override
            protected void onCancelled() {
                mAuthTask = null;
                showProgress(false);
            }
        }

    }

}

package restaurant.org.tchadrestaurant;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import restaurant.org.tchadrestaurant.Adapters.DrinksListAdapter;
import restaurant.org.tchadrestaurant.Adapters.FoodListAdapter;
import restaurant.org.tchadrestaurant.Models.ItemsModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinksFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    protected ArrayList<ItemsModel> allFoodData;

    protected String[] title;
    protected String[] description;
    protected int[] dkImages;
    protected double[] price;

    ItemsModel itmModel;


    public DrinksFragment() {
        // Required empty public constructor
    }

    public static DrinksFragment newInstance(int sectionNumber) {
        DrinksFragment fragment = new DrinksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_drinks, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvDrinks);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DrinksListAdapter adpter = new DrinksListAdapter(getActivity(), allFoodData);
        recyclerView.setAdapter(adpter);

        FloatingActionButton startFood = (FloatingActionButton) view.findViewById(R.id.fabGetMeals);

        startFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity.HomeFragment frag = HomeActivity.HomeFragment.newInstance(0);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, frag).commit();

            }
        });

        return view;
    }

    private void initDataset() {

        allFoodData = new ArrayList<>();

        title = new String[] {"Coca Cola", "Strawberry drink", "Strawberry drink", "Coca Cola",
                "Strawberry drink", "Strawberry drink", "Coca Cola", "Coca Cola"};
        description = new String[] {"Soda drink", "Fresh drink",
                "Fresh drink",
                "Soda drink",
                "Fresh drink",
                "Fresh drink",
                "Soda drink",
                "Soda drink"};
        dkImages = new int[] {R.mipmap.coke, R.mipmap.strawberries, R.mipmap.coke, R.mipmap.coke,
                R.mipmap.strawberries, R.mipmap.strawberries, R.mipmap.coke, R.mipmap.coke};
        price = new double[] {4.40, 6.50, 6.50, 4.40, 6.50, 6.50, 4.40, 4.40};

        for(int i=0; i < title.length; i++) {

            itmModel = new ItemsModel(title[i], price[i], dkImages[i], description[i]);
            allFoodData.add(i, itmModel);
        }
    }


}

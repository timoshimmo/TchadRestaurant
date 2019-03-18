package restaurant.org.tchadrestaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Read details in FragmentIntroFour to get more details on the fragments
public class FragmentIntroTwo extends Fragment {


    public FragmentIntroTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_twoo, container, false);
    }


}

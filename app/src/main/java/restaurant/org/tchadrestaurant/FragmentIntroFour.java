package restaurant.org.tchadrestaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


//Fragment(page) for the fourth intro page display
public class FragmentIntroFour extends Fragment {

    public FragmentIntroFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment...
        This is the design of the page found in layout folder by the name fragment_fragment_intro_four
         */
        return inflater.inflate(R.layout.fragment_fragment_intro_four, container, false);
    }


}

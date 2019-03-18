package restaurant.org.tchadrestaurant;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Fragment(page) for the first intro page display
public class IntroActivityFragment extends Fragment {

    public IntroActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         /* Inflate the layout for this fragment...
        This is the design of the page found in layout folder by the name fragment_intro_one
         */
        return inflater.inflate(R.layout.fragment_intro_one, container, false);
    }
}

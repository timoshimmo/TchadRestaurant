package restaurant.org.tchadrestaurant;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import com.viewpagerindicator.CirclePageIndicator;

public class IntroActivity extends AppCompatActivity {

    static SectionsPagerAdapter mSectionsPagerAdapter;
    static ViewPager mViewPager;

    Button getStarted;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mSectionsPagerAdapter = new SectionsPagerAdapter(this.getSupportFragmentManager());

        mViewPager = (ViewPager)this.findViewById(R.id.launch_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        CirclePageIndicator cp = (CirclePageIndicator)findViewById(R.id.indicator);
        if (cp != null) {
            cp.setViewPager(mViewPager);
        }

        mViewPager.setCurrentItem(0, true);

        getStarted = (Button) findViewById(R.id.btnIntroGetStarted);
        signIn = (Button) findViewById(R.id.btnIntroMember);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);
        }

        public int getCount() {
            return 4;
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new IntroActivityFragment();
                case 1:
                    return new FragmentIntroTwo();
                case 2:
                    return new FragmentIntroThree();
                case 3:
                    return new FragmentIntroFour();
                default:
                    return null;
            }
        }

    }

}

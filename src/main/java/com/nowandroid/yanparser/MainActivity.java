package com.nowandroid.yanparser;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nowandroid.yanparser.FitFragments.DetailsFragment;
import com.nowandroid.yanparser.FitFragments.FragmentList;

public class MainActivity extends AppCompatActivity {


    FragmentList fragmentList;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentList = new FragmentList();

        fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.frameContainer, fragmentList);
        fragmentTransaction.commit();
    }

    public void onBackPressed() {
        int stackEntryCount = getFragmentManager().getBackStackEntryCount();
        if(stackEntryCount > 0){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}

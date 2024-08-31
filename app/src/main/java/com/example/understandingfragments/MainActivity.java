package com.example.understandingfragments;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button,button2,button3;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.startFragment);
        button2=findViewById(R.id.removeFragment);
        button3=findViewById(R.id.popStack);
        textView=findViewById(R.id.backStackTextView);
        fragmentManager = getSupportFragmentManager();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popStack();
            }
        });
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                textView.setText("Fragment count in back stack: "+fragmentManager.getBackStackEntryCount());

                StringBuilder backStackEntryMessage=new StringBuilder("Current of fragment transaction back stack: "+fragmentManager.getBackStackEntryCount()+"\n");
                for(int i=fragmentManager.getBackStackEntryCount()-1;i>=0;i--){
                    backStackEntryMessage.append(fragmentManager.getBackStackEntryAt(i).getName()+"\n");
                }
                Log.v(TAG,backStackEntryMessage.toString());
            }
        });

    }
    private void addFragment() {

        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment;
        switch (fragmentManager.getBackStackEntryCount()) {
                    case 0:fragment=new SampleFragment();
                    break;
                    case 1:fragment=new FragmentOne();
                    break;
                    case 2:fragment=new FragmentTwo();
                    break;
                    case 3:fragment=new FragmentThree();
                    break;
                    default:fragment=new SampleFragment();
        }

        fragmentTransaction.add(R.id.frameLayout,fragment);
        //id of layout in activity_main.xml
        fragmentTransaction.addToBackStack("Add "+fragment.toString());
        fragmentTransaction.commit();
    }

    private void removeFragment() {
        fragmentTransaction=fragmentManager.beginTransaction();
        Fragment fragment=fragmentManager.findFragmentById(R.id.frameLayout);
        if(fragment!=null){
            fragmentTransaction.remove(fragment);
            fragmentTransaction.addToBackStack("Remove "+fragment.toString());
            fragmentTransaction.commit();
        }
        else{
            Log.v(TAG,"Fragment is null");
        }
    }
    private void popStack() {
        //remember that the back button on the mobile is work same as popStack
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        //fragmentManager.popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //fragmentManager.popBackStack("Add SampleFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
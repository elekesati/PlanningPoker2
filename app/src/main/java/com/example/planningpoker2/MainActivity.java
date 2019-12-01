package com.example.planningpoker2;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PlanningPokerMain";

    public static FragmentManager mFragmentManager;
    private Toolbar mToolbar;
    private static Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getSupportActionBar().setSubtitle("Fragment Container");

        mFragmentManager = getSupportFragmentManager();
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.fragment_container)!=null){

            if (savedInstanceState != null){
                return;
            }

            /*FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            SignInFragment loginFragment = new SignInFragment();
            fragmentTransaction.add(R.id.fragment_container, loginFragment, null);
            fragmentTransaction.commit();*/
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new SignInFragment(), null)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.group_list:
                Log.d(TAG, "Change to group list");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new GroupsListFragment(), null)
                        .commit();
                return true;
            case R.id.results:
                Log.d(TAG, "Change to results");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ResultFragment(), null)
                        .commit();
                return true;
            case R.id.add_group:
                Log.d(TAG, "Change to add group");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,  new AddGroupsFragment(), null)
                        .commit();
                return true;
            case R.id.set_visibility:
                Log.d(TAG, "Change to set visibility");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TaskVisibilityHandlerFragment(), null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void setMenu(){
        Log.d(TAG, "Setting up menu");
        Database database = new Database();
        database.getUserStatus(new OnGetDataListener() {
            @Override
            public void onSuccess(List<String> dataList) {
            }

            @Override
            public void onSuccess(ArrayList<String> taskList) {
                if (taskList.get(0).equals("1")){
                    Log.d(TAG, "The user is admin");
                    menu.getItem(2).setVisible(true);
                    menu.getItem(3).setVisible(true);
                }
            }
        });
    }
}

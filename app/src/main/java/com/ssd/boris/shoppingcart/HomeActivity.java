package com.ssd.boris.shoppingcart;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.ssd.boris.shoppingcart.Fragments.AppliancesFragment;
import com.ssd.boris.shoppingcart.Fragments.ElectronicsFragment;
import com.ssd.boris.shoppingcart.Fragments.FashionFragment;
import com.ssd.boris.shoppingcart.Fragments.GroceriesFragment;
import com.ssd.boris.shoppingcart.Fragments.HotDealsFragment;
import com.ssd.boris.shoppingcart.Services.BottomNavigationBehavior;


public class HomeActivity extends AppCompatActivity {

    private ActionBar actionBar;

//    private DBHandler dbHandler;
//    private User user;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        actionBar = getSupportActionBar();

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

//           dbHandler = new DBHandler(this);
//        user = dbHandler.getUser(email);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        actionBar.setTitle("Electronics");
        loadFragment(new ElectronicsFragment());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actionbar_navigation, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.myprofile:
                Intent intent = new Intent(this, UserProfileActivity.class);
                intent.putExtra("email", this.email);
                startActivity(intent);
                return true;
            case R.id.wishlist:
                Intent intent1 = new Intent(this, WishListActivity.class);
                intent1.putExtra("email",this.email);
                startActivity(intent1);
                return true;

            case R.id.cart:
                Intent intent2 = new Intent(this,CartActivity.class);
                intent2.putExtra("email",this.email);
                startActivity(intent2);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =    new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_electronics:
                    actionBar.setTitle("Electronics");
                    fragment = new ElectronicsFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.nav_appliances:
                    actionBar.setTitle("Appliances");
                    fragment = new AppliancesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_fashion:
                    actionBar.setTitle("Fashion");
                    fragment = new FashionFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_groceries:
                    actionBar.setTitle("Groceries");
                    fragment = new GroceriesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_hotdeals:
                    actionBar.setTitle("HotDeals");
                    fragment = new HotDealsFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

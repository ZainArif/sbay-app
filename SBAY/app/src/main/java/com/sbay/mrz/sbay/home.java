package com.sbay.mrz.sbay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;

public class home extends AppCompatActivity {

    public ActionBar actionBar;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View headerView;
    private Menu menuView;
    private ImageView cart;

    private TextView tv_headerName;
    private TextView tv_headerEmail;
    private TextView tv_headerMenu;
    private TextView tv_headerEnterAccount;
    private TextView tv_headerLogin;

    private String username;
    private String email;
    private String menuType;
    private String seller_cust_id;

    private Bundle bundle;

    private addProduct addProduct;
    private myProduct myProduct;

    private static final String TAG = "Media Manager Exception";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try{
            MediaManager.init(this);
        }
        catch (IllegalStateException e){
            Log.d(TAG,e.getMessage());
        }

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        drawerLayout = (DrawerLayout)findViewById(R.id.home_layout);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);
        menuView = navigationView.getMenu();
        cart = (ImageView)findViewById(R.id.cart);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new cart())
                        .add(new cart(),"cart")
                        .addToBackStack("cart")
                        .commit();
            }
        });

        tv_headerName = (TextView)headerView.findViewById(R.id.headerName);
        tv_headerEmail = (TextView)headerView.findViewById(R.id.headerEmail);
        tv_headerMenu = (TextView)headerView.findViewById(R.id.headerMenu);
        tv_headerEnterAccount = (TextView)headerView.findViewById(R.id.headerEnterAccount);
        tv_headerLogin = (TextView)headerView.findViewById(R.id.headerLogin);

        tv_headerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_Activity = new Intent(home.this, MainActivity.class);
                startActivityForResult(main_Activity,1);
            }
        });

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new products())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_softwares);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_softwares:
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new products())
                                .commit();
                        return true;
                    case R.id.nav_addSoftware:
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        bundle = new Bundle();
                        bundle.putString("seller_cust_id",seller_cust_id);
                        addProduct = new addProduct();
                        addProduct.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, addProduct)
                                .commit();
                        return true;
                    case R.id.nav_mySoftware:
                        menuItem.setChecked((true));
                        drawerLayout.closeDrawers();
                        bundle = new Bundle();
                        bundle.putString("seller_cust_id",seller_cust_id);
                        myProduct = new myProduct();
                        myProduct.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, myProduct)
                                .commit();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                username = data.getStringExtra("username");
                email = data.getStringExtra("email");
                menuType = data.getStringExtra("menu type");
                seller_cust_id = data.getStringExtra("seller_cust_id");

                if (menuType.equals("Seller")){
                    tv_headerEnterAccount.setVisibility(View.GONE);
                    tv_headerLogin.setVisibility(View.GONE);
                    tv_headerName.setVisibility(View.VISIBLE);
                    tv_headerEmail.setVisibility(View.VISIBLE);
                    tv_headerMenu.setVisibility(View.VISIBLE);
                    tv_headerName.setText(username);
                    tv_headerEmail.setText(email);
                    tv_headerMenu.setText(menuType + " " + getResources().getString(R.string.menu));
                    menuView.setGroupVisible(R.id.menuSeller,true);
                }
                else if (menuType.equals("Customer")){
                    tv_headerEnterAccount.setVisibility(View.GONE);
                    tv_headerLogin.setVisibility(View.GONE);
                    tv_headerName.setVisibility(View.VISIBLE);
                    tv_headerEmail.setVisibility(View.VISIBLE);
                    tv_headerMenu.setVisibility(View.VISIBLE);
                    tv_headerName.setText(username);
                    tv_headerEmail.setText(email);
                    tv_headerMenu.setText(menuType + " " + getResources().getString(R.string.menu));
                    menuView.setGroupVisible(R.id.menuCustomer,true);
                }

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
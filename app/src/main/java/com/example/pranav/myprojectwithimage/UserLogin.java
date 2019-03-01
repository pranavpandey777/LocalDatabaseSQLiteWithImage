package com.example.pranav.myprojectwithimage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UserLogin extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    ListView listView;
    String[] item;
    Button logout;
    Bitmap bitmap;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private LinearLayout mylay;
    TextView name1,welcomename;
    private SharedPreferences sp;
MyDatabase md;
ImageView image1,welimage;
SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);

        name1=findViewById(R.id.name);
        welcomename=findViewById(R.id.welname);
        logout=findViewById(R.id.logout);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.framelayout);
        image1=findViewById(R.id.image);
        welimage=findViewById(R.id.welimage);
        mylay=findViewById(R.id.mylay);
        listView = findViewById(R.id.lv);
        md=new MyDatabase(this);
        setSupportActionBar(toolbar);

        sp = getSharedPreferences("my_key",MODE_PRIVATE);
        String na=sp.getString("name",null);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        item = new String[]{"Profile","Details"};


        db=md.getReadableDatabase();
        String where=MyDatabase.USERNAME+"=?";
        String[] args={na};

        Cursor c= db.query(MyDatabase.TABLENAME,null,where,args,null,null,null);


        while(c.moveToNext()){

            int index1=c.getColumnIndex(MyDatabase.NAME);

            int index4=c.getColumnIndex(MyDatabase.IMAGE);


            byte[] bytes = c.getBlob(index4);
            Bitmap bitmap1 = MyImageHandler.getImage(bytes);
            String sname=c.getString(index1);



            name1.setText(sname.toUpperCase());
            image1.setImageBitmap(bitmap1);
            welcomename.setText(sname);
            welimage.setImageBitmap(bitmap1);


        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, item);
        listView.setAdapter(adapter);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = item[position];
                // Toast.makeText(DashBoard.this, item + "selected", Toast.LENGTH_SHORT).show();
                selectItem(position);
                drawerLayout.closeDrawer(mylay);
            }


            private void selectItem(int position) {
                String items = item[position];
                setTitle(items);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment;

                switch (position) {

                    case 0:

                        fragment = new Profile();
                        fragmentTransaction.replace(R.id.framelayout, fragment);
                        fragmentTransaction.commit();
                        break;

                    case 1:
                        fragment = new AddEmp();
                        fragmentTransaction.replace(R.id.framelayout, fragment);
                        fragmentTransaction.commit();
                        break;

                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(UserLogin.this);

                builder.setTitle("Logout")
                        .setMessage("Are you sure")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedpreferences =getSharedPreferences("my_key",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent=new Intent(UserLogin.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                              dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();



            }
        });


        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        // Setting ActionBarDrawerToggle on DrawerLayout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Enabling Home button
        getSupportActionBar().setHomeButtonEnabled(true);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



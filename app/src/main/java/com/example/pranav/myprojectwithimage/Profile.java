package com.example.pranav.myprojectwithimage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Pranav on 09-03-2018.
 */

public class Profile extends Fragment {
    TextView name, password, email, num, username;
    private SharedPreferences sp;
    MyDatabase md;
    SQLiteDatabase db;
    Button btn;
    ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.profile, container, false);
        name = v.findViewById(R.id.name);
        username = v.findViewById(R.id.username);
        password = v.findViewById(R.id.password);
        email = v.findViewById(R.id.email);
        btn = v.findViewById(R.id.delete);
        image = v.findViewById(R.id.image);
        md = new MyDatabase(getActivity());
        sp = getContext().getSharedPreferences("my_key", getContext().MODE_PRIVATE);
        final String iname = sp.getString("name", null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    builder
                            .setTitle("Delete Account")
                            .setMessage("Are you sure you want to delete your account")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    db = md.getWritableDatabase();

                                    String wheredel = MyDatabase.USERNAME + "=?";
                                    String[] args1 = {iname};

                                    int id = db.delete(MyDatabase.TABLENAME, wheredel, args1);
                                    if (id == -1) {

                                        //.makeText(getContext(), "not Deleted", Toast.LENGTH_SHORT).show();
                                        Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.frame), "Not Deleted", Snackbar.LENGTH_SHORT);
                                        snackbar.show();


                                    } else {

                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }

        });

        db = md.getReadableDatabase();
        String where = MyDatabase.USERNAME + "=?";
        String[] args = {iname};

        Cursor c = db.query(MyDatabase.TABLENAME, null, where, args, null, null, null);


        while (c.moveToNext()) {

            int index1 = c.getColumnIndex(MyDatabase.NAME);
            int index5 = c.getColumnIndex(MyDatabase.USERNAME);
            int index2 = c.getColumnIndex(MyDatabase.PASSWORD);
            int index3 = c.getColumnIndex(MyDatabase.EMAIL);
            int index4 = c.getColumnIndex(MyDatabase.IMAGE);
            byte[] bytes = c.getBlob(index4);

            Bitmap bitmap1 = MyImageHandler.getImage(bytes);

            String sname = c.getString(index1);
            String spass = c.getString(index2);
            String suser = c.getString(index5);
            String semail = c.getString(index3);


            name.setText(sname);
            username.setText(suser);
            password.setText(spass);
            email.setText(semail);
            image.setImageBitmap(bitmap1);


        }



        return v;

    }
}

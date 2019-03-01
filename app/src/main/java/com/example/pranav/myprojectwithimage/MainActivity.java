package com.example.pranav.myprojectwithimage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageView selectimage;
    EditText name, password, cnfpassword, email, username;
    Button register, login;
    TextWatcher watcher = null;
    String iname, ipassword, icnfpassword, iemail, iusername;
    MyDatabase md;
    SQLiteDatabase db;
    Bitmap bit;
    Context context;


    boolean addedimage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectimage = findViewById(R.id.imageSelect);
        name = findViewById(R.id.name);
        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cnfpassword = findViewById(R.id.cnfpassword);
        email = findViewById(R.id.email);
        register = findViewById(R.id.register);
        md = new MyDatabase(this);

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(intent, 100);
            }
        });


        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                db = md.getReadableDatabase();
                String usn = username.getText().toString();
                String[] col = {MyDatabase.USERNAME};
                String where = MyDatabase.USERNAME + "=?";
                String[] args = {usn};

                Cursor c = db.query(MyDatabase.TABLENAME, col, where, args, null, null, null);

                if (c.getCount() > 0) {
                    username.setError("Username Exist");
                }

                String passchk = password.getText().toString();
                String cnfpasschk = cnfpassword.getText().toString();
                if (!passchk.equals(cnfpasschk)) {

                    cnfpassword.setError("Password not matched");

                }
                String cklusername=username.getText().toString();
                if(cklusername.equals("")){

                    username.setError("Username should not be blank");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        username.addTextChangedListener(watcher);
        cnfpassword.addTextChangedListener(watcher);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = md.getReadableDatabase();
                String usn = username.getText().toString();
                String[] col = {MyDatabase.USERNAME};
                String where = MyDatabase.USERNAME + "=?";
                String[] args = {usn};

                Cursor c = db.query(MyDatabase.TABLENAME, col, where, args, null, null, null);

                if (c.getCount() > 0) {
                    // Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    //  vibe.vibrate(50);
                    username.setError("Username Exist");
                    username.findFocus();
                } else {
                    String passchk = password.getText().toString();
                    String cnfpasschk = cnfpassword.getText().toString();
                    String chkemail=email.getText().toString();
                    if (passchk.equals(cnfpasschk)) {
                        String cklusername = username.getText().toString();

                        if (!chkemail.equals("")) {

                            if (cklusername.equals("")) {

                                username.setError("Username should not be blank");


                            } else {
                                iname = name.getText().toString();
                                iusername = username.getText().toString();
                                ipassword = password.getText().toString();
                                icnfpassword = cnfpassword.getText().toString();
                                iemail = email.getText().toString();

                                db = md.getWritableDatabase();
                                Bitmap image;
                                if (addedimage) {
                                    image = bit;
                                } else {
                                    image = BitmapFactory.decodeResource(getResources(), R.drawable.inputimage);
                                }
                                byte[] rimage = MyImageHandler.getBytes(image);


                                ContentValues cv = new ContentValues();
                                cv.put(MyDatabase.NAME, iname);
                                cv.put(MyDatabase.USERNAME, iusername);
                                cv.put(MyDatabase.PASSWORD, ipassword);
                                cv.put(MyDatabase.EMAIL, iemail);
                                cv.put(MyDatabase.IMAGE, rimage);

                                long id = db.insert(MyDatabase.TABLENAME, null, cv);
                                if (id == -1) {
                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.frame), "Unsuccessful", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                } else {

                                    name.setText("");
                                    password.setText("");
                                    cnfpassword.setText("");
                                    email.setText("");
                                    username.setText("");
                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.frame), "successful", Snackbar.LENGTH_LONG);
                                    snackbar.show();


                                }
                            }
                            }else{

                            email.setError("Email block should not be blank");


                        };


                        } else {

                            cnfpassword.setError("Password not matched");

                        }


                }
            }

        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                bit = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                selectimage.setImageBitmap(bit);
                addedimage = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void dialog() {
        LoginPage log = new LoginPage();
        log.show(getSupportFragmentManager(), "login");


    }

}

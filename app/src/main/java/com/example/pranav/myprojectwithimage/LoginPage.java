package com.example.pranav.myprojectwithimage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatDialogFragment {
    EditText usernme, password;
    MyDatabase md;
    SQLiteDatabase db;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    Button btn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login, null);
        usernme = view.findViewById(R.id.user);
        password = view.findViewById(R.id.pass);
        btn=view.findViewById(R.id.login);
        md = new MyDatabase(getActivity());
        builder.setView(view);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = md.getReadableDatabase();
                String[] col = {MyDatabase.USERNAME, MyDatabase.PASSWORD};
                String whereClause = MyDatabase.USERNAME + "=?";
                String[] whereArgs = {usernme.getText().toString()};
                Cursor cursor = db.query(MyDatabase.TABLENAME, col, whereClause, whereArgs, null, null, null);

                if (cursor.moveToFirst()) {
                    String iname = usernme.getText().toString();
                    String ipassword = password.getText().toString();
                    int index1 = cursor.getColumnIndex(MyDatabase.USERNAME);
                    int index2 = cursor.getColumnIndex(MyDatabase.PASSWORD);

                    String name = cursor.getString(index1);
                    String password1 = cursor.getString(index2);

                    if (iname.equals(name) && ipassword.equals(password1)) {
                        String usersend=usernme.getText().toString();

                        sp=getActivity().getSharedPreferences("my_key",getContext().MODE_PRIVATE);//it returns two parameters one string and another status flag
                        editor=sp.edit();
                        editor.putString("name",usersend);
                        editor.commit();

                        Intent intent=new Intent(getActivity(),UserLogin.class);
                        startActivity(intent);
                        getActivity().finish();

                        // Toast.makeText(getContext(), "pass", Toast.LENGTH_SHORT).show();
                    } else {

                        // Toast.makeText(getContext(), "Invalid UserName or Password", Toast.LENGTH_SHORT).show();

                    }
                }




            }
        });


        return builder.create();


    }
}

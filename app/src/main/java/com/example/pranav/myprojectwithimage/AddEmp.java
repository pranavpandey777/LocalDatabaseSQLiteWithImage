package com.example.pranav.myprojectwithimage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AddEmp extends Fragment {
    RecyclerView rv;
    LinearLayoutManager manager;
    ArrayList<AddEmpSingleRow> arrayList;
    AddEmpSingleRow addEmpSingleRow;
    MyDatabase md;
    SQLiteDatabase db;


    @Nullable
    @Override
    public View onCreateView
            (LayoutInflater inflater,
             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addemp, container, false);
        rv = v.findViewById(R.id.rv);
        md=new MyDatabase(getActivity());
        manager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(manager);
        arrayList = new ArrayList();

        db=md.getReadableDatabase();

        String[] col={MyDatabase.NAME,MyDatabase.EMAIL,MyDatabase.IMAGE};

        Cursor c=db.query(MyDatabase.TABLENAME,col,null,null,null,null,null);

        while(c.moveToNext()){
           int index1 =c.getColumnIndex(MyDatabase.NAME);
           int index2 =c.getColumnIndex(MyDatabase.EMAIL);
           int index3 =c.getColumnIndex(MyDatabase.IMAGE);

           byte[] img=c.getBlob(index3);
            Bitmap imgg=MyImageHandler.getImage(img);
           String kname=c.getString(index1);
           String kemail=c.getString(index2);


            addEmpSingleRow = new AddEmpSingleRow(kname, kemail,imgg);
            arrayList.add(addEmpSingleRow);

        }

        AddEmpAdapter addEmpAdapter=new AddEmpAdapter(getActivity(),arrayList);
        rv.setAdapter(addEmpAdapter);
    return v;
    }
}

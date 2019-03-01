package com.example.pranav.myprojectwithimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddEmpAdapter extends RecyclerView.Adapter<AddEmpAdapter.MyViewHolder>  {
    Context context;
    ArrayList<AddEmpSingleRow> arrayList;

    public AddEmpAdapter(Context context, ArrayList<AddEmpSingleRow> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.addempsinglerow,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AddEmpSingleRow s=arrayList.get(position);
        String name2=s.getName();
        Bitmap image2=s.getImage();
        String num2=s.getNum();
        holder.name1.setText(name2);
        holder.num1.setText(num2);
        holder.image1.setImageBitmap(image2);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
TextView name1 ,num1;
ImageView image1;
    public MyViewHolder(View itemView) {
        super(itemView);
        name1=itemView.findViewById(R.id.name);
        num1=itemView.findViewById(R.id.num);
        image1=itemView.findViewById(R.id.image);

    }
}

}

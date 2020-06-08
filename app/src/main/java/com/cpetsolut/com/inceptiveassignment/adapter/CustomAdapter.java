package com.cpetsolut.com.inceptiveassignment.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsolut.com.inceptiveassignment.DBHelper.Controllerdb;
import com.cpetsolut.com.inceptiveassignment.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    Controllerdb controldb;
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> PatientName = new ArrayList<String>();
    private ArrayList<String> UHIDNumber = new ArrayList<String>();
    private ArrayList<String> Age = new ArrayList<String>();
    private ArrayList<String> Gender = new ArrayList<String>();
    private ArrayList<String> DateOfBirth = new ArrayList<String>();
    private ArrayList<String> Address = new ArrayList<String>();
    private ArrayList<String> MobileNumber = new ArrayList<String>();

    public CustomAdapter(Context mContext, ArrayList<String> patientName, ArrayList<String> UHIDNumber, ArrayList<String> age, ArrayList<String> address) {
        this.mContext = mContext;
        this.PatientName = patientName;
        this.UHIDNumber = UHIDNumber;
        this.Age = age;
        this.Address = address;
    }

    @Override
    public int getCount() {
        return Id.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final    viewHolder holder;
        controldb =new Controllerdb(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.result_layout, null);
            holder = new viewHolder();
            holder.pname = (TextView) convertView.findViewById(R.id.pname);
            holder.puhid = (TextView) convertView.findViewById(R.id.puhid);
            holder.page = (TextView) convertView.findViewById(R.id.page);
            holder.paddress = (TextView) convertView.findViewById(R.id.paddress);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.pname.setText(PatientName.get(position));
        holder.puhid.setText(UHIDNumber.get(position));
        holder.page.setText(Age.get(position));
        holder.paddress.setText(Address.get(position));
        return convertView;
    }
    public class viewHolder {

        TextView pname;
        TextView puhid;
        TextView page;
        TextView paddress;
    }
}

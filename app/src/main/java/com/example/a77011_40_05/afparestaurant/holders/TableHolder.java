package com.example.a77011_40_05.afparestaurant.holders;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.models.Table;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.GenericAlertDialog;

public class TableHolder extends RecyclerView.ViewHolder {

    private CardView cvTableBody;
    private TextView txtTableNumber;
    private Table table;

    public TableHolder(View view) {
        super(view);
        cvTableBody = view.findViewById(R.id.cvTableBody);
        txtTableNumber = view.findViewById(R.id.txtTableNumber);
    }

    public void setTable(Table table, Activity activity){
        this.table = table;

        String number = String.format("%02d", table.getNumber());
        txtTableNumber.setText(number);

        if(table.getIdOrder() != 0){
            cvTableBody.setCardBackgroundColor(activity.getResources().getColor(R.color.tableOccupied));
            cvTableBody.setEnabled(false);
        }else{
            cvTableBody.setCardBackgroundColor(activity.getResources().getColor(R.color.tableFree));
            cvTableBody.setEnabled(true);
        }

        cvTableBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGuests(activity);
            }
        });
    }

    public void selectGuests(Activity activity){
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_guests_selector,null,false);
        EditText guests = view.findViewById(R.id.edtGuests);
        new GenericAlertDialog(activity, "Nombre de personnes:", view, new GenericAlertDialog.CallGenericAlertDialog() {
            @Override
            public void onValidate() {
                //Toast.makeText(activity,"Guests: "+guests.getText(),Toast.LENGTH_LONG).show();
                HomeActivity home = (HomeActivity) activity;
                Bundle args = new Bundle();
                try{
                    int nbGuests = Integer.parseInt(guests.getText().toString());
                    if(nbGuests != 0){
                        args.putInt("guests",nbGuests);
                        args.putInt("idTable",table.getIdTable());
                        home.changeFragment(Constants.FRAG_ORDER,args);
                    }else{
                        Toast.makeText(activity,"Au moins 1 convive nécéssaire.",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(activity,"Erreur de saisie.",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}

package com.example.a77011_40_05.afparestaurant.holders;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.models.Step;

public class StepHolder  extends RecyclerView.ViewHolder {

    private CardView cvStepsBody;
    private TextView txtStep;
    private Step step;

    public StepHolder(View view) {
        super(view);
        cvStepsBody = view.findViewById(R.id.cvStepsBody);
        txtStep = view.findViewById(R.id.txtStep);
    }

    public void setStep(Step step, Activity activity){
        this.step = step;

        txtStep.setText(step.getName());

        cvStepsBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMeal(activity);
            }
        });
    }

    public void selectMeal(Activity activity){

        HomeActivity home = (HomeActivity) activity;
        home.showMealsDialog(step.getIdStep());

        /*View view = LayoutInflater.from(activity).inflate(R.layout.dialog_guests_selector,null,false);
        EditText guests = view.findViewById(R.id.edtGuests);
        new GenericAlertDialog(activity, "Nombre de personnes:", view, new GenericAlertDialog.CallGenericAlertDialog() {
            @Override
            public void onValidate() {
                //Toast.makeText(activity,"Guests: "+guests.getText(),Toast.LENGTH_LONG).show();
                HomeActivity home = (HomeActivity) activity;
                Bundle args = new Bundle();
                int nbGuests = Integer.parseInt(guests.getText().toString());
                args.putInt("guests",nbGuests);
                args.putInt("idTable",table.getIdTable());
                home.changeFragment(Constants.FRAG_ORDER,args);
            }
        });*/
    }
}
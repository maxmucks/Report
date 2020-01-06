package org.deafop.ngoreport.models;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import org.deafop.ngoreport.Activities.MainActivity;
import org.deafop.ngoreport.R;

public class InternetDialog {
    private Context context;

    public InternetDialog(){

    }
    public InternetDialog(Context context){
        this.context = context;
    }

    public void showNoInternetDialog(){
        final Dialog dialog1 = new Dialog(context, R.style.df_dialog);
        dialog1.setContentView(R.layout.dialog_no_internet);
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(dialog1.getContext(), MainActivity.class);
                dialog1.getContext().startActivity(backIntent);
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }
    public  boolean getInternetStatus() {

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected) {
            //show no internet dialog
            showNoInternetDialog();
        }
        return isConnected;
    }


}

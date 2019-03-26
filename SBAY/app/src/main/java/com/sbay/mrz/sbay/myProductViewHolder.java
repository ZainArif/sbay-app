package com.sbay.mrz.sbay;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class myProductViewHolder extends RecyclerView.ViewHolder{
    ImageView iv_productImage;
    TextView tv_productName;
    TextView tv_productCat;
    Button btn_edit;
    Button btn_delete;

    public myProductViewHolder(@NonNull View itemView) {
        super(itemView);

        iv_productImage = (ImageView)itemView.findViewById(R.id.productImage);
        tv_productName = (TextView)itemView.findViewById(R.id.productName);
        tv_productCat = (TextView)itemView.findViewById(R.id.productCat);
        btn_edit = (Button)itemView.findViewById(R.id.btn_edit);
        btn_delete = (Button)itemView.findViewById(R.id.btn_delete);
    }
}
package com.sbay.mrz.sbay;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class cartViewHolder extends RecyclerView.ViewHolder {
     TextView tv_cartProductName;
     TextView tv_cartProductCat;
     TextView tv_cartProductCost;
     TextView tv_cartNumber;
     Button btn_cartMinus;
     Button btn_cartPlus;
     Button btn_cartDel;
     ImageView iv_productImage;

    public cartViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_cartProductName = (TextView)itemView.findViewById(R.id.cart_productName);
        tv_cartProductCat = (TextView)itemView.findViewById(R.id.cart_productCat);
        tv_cartProductCost = (TextView)itemView.findViewById(R.id.cart_productCost);
        tv_cartNumber = (TextView)itemView.findViewById(R.id.tv_cart_number);
        btn_cartMinus = (Button)itemView.findViewById(R.id.btn_cartMinus);
        btn_cartPlus = (Button)itemView.findViewById(R.id.btn_cartPlus);
        btn_cartDel = (Button)itemView.findViewById(R.id.btn_cartDel);
        iv_productImage = (ImageView) itemView.findViewById(R.id.cart_productImage);
    }

}

package com.sbay.mrz.sbay;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class updateProduct extends Fragment {
    private View rootView;

    private Bundle bundle;

    private EditText et_productName;
    private EditText et_productDesc;
    private EditText et_productCost;
    private EditText et_productExeUrl;
    private EditText et_productDemoUrl;
    private EditText et_productHostUrl;
    private Spinner sp_productCat;
    private ImageButton imgbtn_pName;
    private ImageButton imgbtn_pDesc;
    private ImageButton imgbtn_pCost;
    private ImageButton imgbtn_pCat;
    private ImageButton imgbtn_pExeUrl;
    private ImageButton imgbtn_pDemoUrl;
    private ImageButton imgbtn_pHostUrl;
    private Button btn_updateProduct;
    private Button btn_uploadImage;
    private ImageView img_screenshot;

    private extraFunctions extraFunctions;

    private String pId;
    private String sellerId;
    private String pName;
    private String pDesc;
    private String pCost;
    private String pExeUrl;
    private String pDemoUrl;
    private String pHostUrl;
    private String pCat;
    private String[] pScreenshot;
    private String[] pScreenshotPublicId;

    private Boolean isImageUpload;
    private Boolean isImageSelect;

    private ApiInterface apiInterface;

    private softwareDetails softwareDetails;

    private Uri path;
    private Bitmap bitmap;

    private String[] spinnerList;

    private static final String TAG = "Media Manager Exception";

    private AlertDialog.Builder alertDialog;

    public updateProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_update_product, container, false);

        alertDialog = new AlertDialog.Builder(this.getContext());

        isImageUpload = false;
        isImageSelect = false;

        apiInterface = ApiClient.getApiClient("products").create(ApiInterface.class);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(getActivity(), getContext());

        spinnerList = new String[]{"Android/Ios App", "Android App", "Ios App", "Web App", "VR", "AR", "AI", "Ecommerce", "IOT"};

        et_productName = (EditText) rootView.findViewById(R.id.et_productName);
        et_productDesc = (EditText) rootView.findViewById(R.id.et_productDesc);
        et_productCost = (EditText) rootView.findViewById(R.id.et_productCost);
        et_productExeUrl = (EditText) rootView.findViewById(R.id.et_productExeurl);
        et_productDemoUrl = (EditText) rootView.findViewById(R.id.et_productDemoUrl);
        et_productHostUrl = (EditText) rootView.findViewById(R.id.et_productHostUrl);
        sp_productCat = (Spinner) rootView.findViewById(R.id.sp_productCat);
        btn_updateProduct = (Button) rootView.findViewById(R.id.btn_updateProduct);
        btn_uploadImage = (Button) rootView.findViewById(R.id.btn_uploadimage);
        img_screenshot = (ImageView) rootView.findViewById(R.id.img_screenshot);
        imgbtn_pName = (ImageButton) rootView.findViewById(R.id.imgbtn_pName);
        imgbtn_pDesc = (ImageButton) rootView.findViewById(R.id.imgbtn_pDesc);
        imgbtn_pCost = (ImageButton) rootView.findViewById(R.id.imgbtn_pCost);
        imgbtn_pCat = (ImageButton) rootView.findViewById(R.id.imgbtn_pCat);
        imgbtn_pExeUrl = (ImageButton) rootView.findViewById(R.id.imgbtn_pExeUrl);
        imgbtn_pDemoUrl = (ImageButton) rootView.findViewById(R.id.imgbtn_pDemoUrl);
        imgbtn_pHostUrl = (ImageButton) rootView.findViewById(R.id.imgbtn_pHostUrl);

        bundle = this.getArguments();
        if (bundle!=null){
            pId = bundle.getString("pId");
            sellerId = bundle.getString("pSellerId");
            pName = bundle.getString("pName");
            pDesc = bundle.getString("pDesc");
            pCost = bundle.getString("pCost");
            pCat = bundle.getString("pCat");
            pExeUrl = bundle.getString("pExeUrl");
            pDemoUrl = bundle.getString("pDemoUrl");
            pHostUrl = bundle.getString("pHostUrl");
            pScreenshot = bundle.getStringArray("pScreenshots");
            pScreenshotPublicId = bundle.getStringArray("pScreenshotsPublicId");
            et_productName.setText(pName);
            et_productDesc.setText(pDesc);
            et_productCost.setText(pCost);
            et_productExeUrl.setText(pExeUrl);
            et_productDemoUrl.setText(pDemoUrl);
            et_productHostUrl.setText(pHostUrl);

//            if (pExeUrl.equals(""))
//                et_productExeUrl.setText(getResources().getString(R.string.notavailable));
//            else
//
//
//            if (pDemoUrl.equals(""))
//                et_productDemoUrl.setText(getResources().getString(R.string.notavailable));
//            else
//
//
//            if (pHostUrl.equals(""))
//                et_productHostUrl.setText(getResources().getString(R.string.notavailable));
//            else


            for (int i=0; i<spinnerList.length; i++){
                if (spinnerList[i].equals(pCat)){
                    sp_productCat.setSelection(i);
                    break;
                }
            }

            Glide.with(getContext()).load(pScreenshot[0]).into(img_screenshot);
        }

        sp_productCat.setEnabled(false);

        imgbtn_pName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(getContext(),et_productName,btn_updateProduct);
            }
        });

        imgbtn_pDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(getContext(),et_productDesc,btn_updateProduct);
            }
        });

        imgbtn_pCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(getContext(),et_productCost,btn_updateProduct);
            }
        });

        imgbtn_pCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_productCat.setEnabled(true);
                btn_updateProduct.setVisibility(View.VISIBLE);
            }
        });

        imgbtn_pExeUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(getContext(),et_productExeUrl,btn_updateProduct);
            }
        });

        imgbtn_pDemoUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(getContext(),et_productDemoUrl,btn_updateProduct);
            }
        });

        imgbtn_pHostUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(getContext(),et_productHostUrl,btn_updateProduct);
            }
        });

        btn_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btn_updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Are you sure you want to update this information?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        extraFunctions.showProgressDialog(getContext(), "Updating Product");

                        dialog.dismiss();

                        extraFunctions.closeKeyboard(getActivity(), getContext());

                        pName = et_productName.getText().toString();
                        pDesc = et_productDesc.getText().toString();
                        pCost = et_productCost.getText().toString();
                        pExeUrl = et_productExeUrl.getText().toString();
                        pDemoUrl = et_productDemoUrl.getText().toString();
                        pHostUrl = et_productHostUrl.getText().toString();
                        pCat = sp_productCat.getSelectedItem().toString();

                        if (isImageSelect){
                            if (!isImageUpload) {
                                uploadImage();
                            }
                            else
                                update_product();
                        }
                        else
                            update_product();
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.create().show();

            }
        });

        return rootView;
    }

    private void update_product() {

        softwareDetails = new softwareDetails(sellerId, pName, pDesc, pExeUrl, pDemoUrl, pHostUrl, pCost, pCat, pScreenshot,pScreenshotPublicId);

        final Call<softwareDetails> updateProductSoftwareCall = apiInterface.updateProduct(pId, softwareDetails);
        updateProductSoftwareCall.enqueue(new Callback<com.sbay.mrz.sbay.softwareDetails>() {
            @Override
            public void onResponse(Call<com.sbay.mrz.sbay.softwareDetails> call, Response<com.sbay.mrz.sbay.softwareDetails> response) {
                String updateStatus = response.body().getUpdatestatus();
                if (updateStatus.equals("updated")) {

                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.pupdated));
                    extraFunctions.toast.show();

//                    bundle = new Bundle();
//                    bundle.putString("seller_cust_id",sellerId);
//                    myProduct myProduct = new myProduct();
//                    myProduct.setArguments(bundle);
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_container, myProduct)
//                            .commit();

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(updateProduct.this).commit();
                    getActivity().getSupportFragmentManager().popBackStack();
//                    extraFunctions.toast.show();
//                    et_productName.getText().clear();
//                    et_productDesc.getText().clear();
//                    et_productCost.getText().clear();
//                    et_productExeUrl.getText().clear();
//                    et_productDemoUrl.getText().clear();
//                    et_productHostUrl.getText().clear();
//                    img_screenshot.setVisibility(View.GONE);
//                    btn_updateProduct.setVisibility(View.GONE);
//                    isImageUpload = false;
                }
                else {
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.sww));
                    extraFunctions.toast.show();
                }
            }

            @Override
            public void onFailure(Call<com.sbay.mrz.sbay.softwareDetails> call, Throwable t) {
                extraFunctions.hideProgressDialog();
                extraFunctions.text.setText(getResources().getString(R.string.sww));
                extraFunctions.toast.show();
            }
        });

    }

    private byte[] imageToByte() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return imgByte;
    }

    private void uploadImage() {
        MediaManager.get().upload(imageToByte()).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {

            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {

            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                delAsyncTask delAsyncTask = new delAsyncTask(getContext());
                delAsyncTask.execute(pScreenshotPublicId[0]);
                pScreenshot[0] = resultData.get("secure_url").toString();
                pScreenshotPublicId[0] = resultData.get("public_id").toString();
                isImageUpload = true;
                update_product();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {

            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();

    }

    private void selectImage() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, 5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null) {
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
                img_screenshot.setImageBitmap(bitmap);
               // img_screenshot.setVisibility(View.VISIBLE);
                btn_updateProduct.setVisibility(View.VISIBLE);
                isImageSelect = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

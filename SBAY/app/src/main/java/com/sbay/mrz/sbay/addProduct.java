package com.sbay.mrz.sbay;


import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.preprocess.DimensionsValidator;
import com.cloudinary.android.preprocess.ImagePreprocessChain;
import com.cloudinary.android.preprocess.Limit;
import com.cloudinary.utils.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class addProduct extends Fragment {

    private View rootView;

    private Bundle bundle;

    private EditText et_productName;
    private EditText et_productDesc;
    private EditText et_productCost;
    private EditText et_productExeUrl;
    private EditText et_productDemoUrl;
    private EditText et_productHostUrl;
    private Spinner sp_productCat;
    private Button btn_addProduct;
    private Button btn_uploadImage;
    private ImageView img_screenshot;

    private extraFunctions extraFunctions;

    private String sellerId;
    private String pNmae;
    private String pDesc;
    private String pCost;
    private String pExeUrl;
    private String pDemoUrl;
    private String pHostUrl;
    private String pCat;
    private String[] pScreenshot;

    private Boolean valid;
    private Boolean isImageUpload;

    private ApiInterface apiInterface;

    private softwareDetails softwareDetails;

    private Uri path;
    private Bitmap bitmap;
    private String screenShotUrl;

    public addProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_product, container, false);

        apiInterface = ApiClient.getApiClient("products").create(ApiInterface.class);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(getActivity(), getContext());

        bundle = this.getArguments();
        if (bundle != null)
            sellerId = bundle.getString("seller_cust_id");

        pScreenshot = new String[1];
        isImageUpload = false;

        et_productName = (EditText) rootView.findViewById(R.id.et_productName);
        et_productDesc = (EditText) rootView.findViewById(R.id.et_productDesc);
        et_productCost = (EditText) rootView.findViewById(R.id.et_productCost);
        et_productExeUrl = (EditText) rootView.findViewById(R.id.et_productExeurl);
        et_productDemoUrl = (EditText) rootView.findViewById(R.id.et_productDemoUrl);
        et_productHostUrl = (EditText) rootView.findViewById(R.id.et_productHostUrl);
        sp_productCat = (Spinner) rootView.findViewById(R.id.sp_productCat);
        btn_addProduct = (Button) rootView.findViewById(R.id.btn_addProduct);
        btn_uploadImage = (Button) rootView.findViewById(R.id.btn_uploadimage);
        img_screenshot = (ImageView) rootView.findViewById(R.id.img_screenshot);

        btn_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (btn_addProduct.isEnabled()){
                extraFunctions.closeKeyboard(getActivity(), getContext());
                pNmae = et_productName.getText().toString();
                pDesc = et_productDesc.getText().toString();
                pCost = et_productCost.getText().toString();
                pExeUrl = et_productExeUrl.getText().toString();
                pDemoUrl = et_productDemoUrl.getText().toString();
                pHostUrl = et_productHostUrl.getText().toString();
                pCat = sp_productCat.getSelectedItem().toString();

                if (!validation()) {
                    return;
                }

                extraFunctions.showProgressDialog(getContext(), "Saving Product");

                if (!isImageUpload) {
                    uploadImage();
                }
                else
                    postProduct();
                // postProduct();
//                }
//                else {
//                    extraFunctions.text.setText(getResources().getString(R.string.selectImage));
//                    extraFunctions.toast.show();
//                }
            }
        });

        return rootView;
    }

    private boolean validation() {
        valid = true;

        if (TextUtils.isEmpty(pNmae)) {
            et_productName.setError("required");
            valid = false;
        }

        if (TextUtils.isEmpty(pDesc)) {
            et_productDesc.setError("required");
            valid = false;
        }

        if (TextUtils.isEmpty(pCost)) {
            et_productCost.setError("required");
            valid = false;
        }

        return valid;
    }

    private void postProduct() {

        softwareDetails = new softwareDetails(sellerId, pNmae, pDesc, pExeUrl, pDemoUrl, pHostUrl, pCost, pCat, pScreenshot);

        Call<softwareDetails> addSoftwareCall = apiInterface.postProduct(softwareDetails);
        addSoftwareCall.enqueue(new Callback<com.sbay.mrz.sbay.softwareDetails>() {
            @Override
            public void onResponse(Call<com.sbay.mrz.sbay.softwareDetails> call, Response<com.sbay.mrz.sbay.softwareDetails> response) {
                if (response.isSuccessful()) {
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.psaved));
                    extraFunctions.toast.show();
                    et_productName.getText().clear();
                    et_productDesc.getText().clear();
                    et_productCost.getText().clear();
                    et_productExeUrl.getText().clear();
                    et_productDemoUrl.getText().clear();
                    et_productHostUrl.getText().clear();
                    img_screenshot.setVisibility(View.GONE);
                    btn_addProduct.setVisibility(View.GONE);
                    isImageUpload = false;
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
        //return Base64.encodeToString(imgByte,Base64.DEFAULT);
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
                screenShotUrl = resultData.get("secure_url").toString();
                pScreenshot[0] = screenShotUrl;
                isImageUpload = true;
                postProduct();
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
        //getActivity().startActivityFromFragment(addProduct.this,galleryIntent,5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null) {
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
                img_screenshot.setImageBitmap(bitmap);
                img_screenshot.setVisibility(View.VISIBLE);
                btn_addProduct.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

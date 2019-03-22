package com.sbay.mrz.sbay;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductsData {

    @Insert
    void insertProduct(softwareDetails softwareDetails);

    @Delete
    void deleteProduct(softwareDetails softwareDetails);

    @Query("SELECT * FROM products")
    List<softwareDetails> getAllProducts();

    @Query("SELECT * FROM products WHERE productID = :pId Limit 1")
    softwareDetails getProductById(String pId);
}

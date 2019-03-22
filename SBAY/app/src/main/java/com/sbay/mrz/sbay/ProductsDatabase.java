package com.sbay.mrz.sbay;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {softwareDetails.class}, version = 1, exportSchema = false)
public abstract class ProductsDatabase extends RoomDatabase {
    public abstract ProductsData productsData();
}

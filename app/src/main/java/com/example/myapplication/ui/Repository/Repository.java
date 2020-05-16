package com.example.myapplication.ui.Repository;

import android.content.Context;

public class Repository {
    private static Repository srepository;

    private Context context;

    private Repository(Context context) {
        this.context = context;
    }

    public static Repository get(Context context) {
        if(srepository == null) {
            srepository = new Repository(context);
        }
        return srepository;
    }

    public static Repository getRepository() {
        return srepository;
    }
}

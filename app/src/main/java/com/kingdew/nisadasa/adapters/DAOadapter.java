package com.kingdew.nisadasa.adapters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DAOadapter
{
    private DatabaseReference databaseReference;
    private DatabaseReference cateRef;

    public DAOadapter() {
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Posts");
        cateRef=db.getReference("Categories");
    }

    public Query get()
    {
        return databaseReference.orderByKey();
    }


}

package com.example.desginstofinal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBref {
    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance("https://alfaversion-3fc76-default-rtdb.firebaseio.com/");

    public static DatabaseReference refstudent=FBDB.getReference("students");
    public static DatabaseReference refmanager=FBDB.getReference("managers");
    public static DatabaseReference refpro=FBDB.getReference("products");
    public static DatabaseReference reford=FBDB.getReference("orders");
    public static FirebaseStorage FBST = FirebaseStorage.getInstance();
    public static StorageReference refStor=FBST.getReference();
    public static StorageReference refImages=refStor.child("Images");

    public static Query query;

}

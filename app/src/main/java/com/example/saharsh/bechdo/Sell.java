package com.example.saharsh.bechdo;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class Sell extends Fragment {
    private Button update;
    private EditText cost,contact,model;
    private DatabaseReference db;
    private ImageButton imageButton;
    private static final int GALLARY=4;
    private StorageReference mStorage;
    ProgressDialog progressDialog;
    private Uri uri=null;
    private FirebaseAuth mAuth;



    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_sell, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLARY && resultCode==RESULT_OK){


            uri = data.getData();
            imageButton.setImageURI(uri);

            }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        update=(Button)view.findViewById(R.id.update);
        cost=(EditText) view.findViewById(R.id.cost);
        contact=(EditText) view.findViewById(R.id.contact);
        model=(EditText)view.findViewById(R.id.model);
        imageButton=(ImageButton)view.findViewById(R.id.imageButton);
        final DatabaseReference root=db.child("Database");
        progressDialog = new ProgressDialog(getActivity());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLARY);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final String getcost = cost.getText().toString().trim();
               final String getContact = contact.getText().toString().trim();
               final String getmodel = model.getText().toString().trim();
               final String uid=mAuth.getCurrentUser().getUid();



                if (!TextUtils.isEmpty(getcost) && !TextUtils.isEmpty(getContact) && !TextUtils.isEmpty(getmodel) && uri!=null){
                    Log.d("tag","Inside " );
                    progressDialog.setMessage("Uploading...");
                    progressDialog.show();
                    final StorageReference fileName = mStorage.child("Bicycle/" + uri.getLastPathSegment() + ".png");
                    fileName.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(getActivity(), "UPLOAD COMPLETE", Toast.LENGTH_SHORT).show();

                            fileName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Log.i("INFo","url "+uri.toString());
                                    HashMap<String, String> userMap = new HashMap<String, String>();
                                    userMap.put("Cost", getcost);
                                    userMap.put("Model",getmodel );
                                    userMap.put("Contact", getContact);
                                    userMap.put("Url",uri.toString());
                                    userMap.put("UID",uid);

                                    root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressDialog.dismiss();
                                                Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(getActivity(),MainActivity.class);
                                                startActivity(intent);




                                            }
                                        }
                                    });
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                        }
                    });


                }



            }
        });



    }


}


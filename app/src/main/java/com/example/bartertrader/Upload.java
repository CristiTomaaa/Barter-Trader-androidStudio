package com.example.bartertrader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class Upload extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

     Button uploadImage;
     ImageView imageV;
     ProgressBar progBar;
     Uri imgUri;
     StorageReference storageRef;
     StorageTask uptask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        uploadImage = findViewById(R.id.btn_upload_uploadimg);
        imageV = findViewById(R.id.iv_upload_updimg);
        progBar = findViewById(R.id.pb_upload_uploadprog);

        storageRef = FirebaseStorage.getInstance().getReference("images");

//                Intent istart = getIntent();
//        final String reason = istart.getStringExtra("reason");
//        if (reason != null) {
//            Toast.makeText(Upload.this, "."+reason+".", Toast.LENGTH_SHORT).show();
//        }
        imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uptask != null && uptask.isInProgress())
                    Toast.makeText(Upload.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                else {
                    uploadFile();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){

            Picasso.get().load(data.getData()).fit().into(imageV);
            imgUri = data.getData();

        }
    }
    //get file extention
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }
    private void uploadFile() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user");
        final String pkey = dbRef.push().getKey();
        final StorageReference fileRef = storageRef.child(pkey + "." + getFileExtension(imgUri));


        if(imgUri !=null){


            uptask = fileRef.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(Upload.this, "Upload successful!", Toast.LENGTH_LONG).show();


                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String post = "post";
                                    Intent why = getIntent();
                                    String who = why.getStringExtra("class");

                                    if (post.equals(who)){
                                        String url = uri.toString();
                                        Intent i = new Intent(Upload.this, Post.class);
                                        i.putExtra("URL", url);
                                        i.putExtra("PKEY", pkey);

                                        startActivity(i);
                                    }
                                    else
                                    {
                                        String url = uri.toString();
                                        Intent i = new Intent(Upload.this, Register.class);
                                        i.putExtra("URL", url);
                                        i.putExtra("PKEY", pkey);

                                        startActivity(i);
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    fileRef.delete();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Upload.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progBar.setProgress((int) progress);
                        }
                    });

        }
        else {
            Toast.makeText(this, "No file selected !!!", Toast.LENGTH_SHORT).show();
        }
    }
}


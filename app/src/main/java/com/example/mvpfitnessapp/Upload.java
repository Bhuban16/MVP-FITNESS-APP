package com.example.mvpfitnessapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.annotation.Nullable;

public class Upload extends AppCompatActivity {


    private  static final int PICK_VIDEO= 1;
    Button button;
    EditText editText;
    EditText description;
    VideoView videoView;
    private Uri videoUri;
    MediaController mediaController;
    StorageReference storageReference;
     DatabaseReference databaseReference;
    ProgressBar progressBar;
    Member member;
    UploadTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        member = new Member();
        storageReference = FirebaseStorage.getInstance().getReference("Video");
        databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("video");
        description = findViewById(R.id.et_video_description);
        editText = findViewById(R.id.et_video_name);
        button = findViewById(R.id.button_upload_main);
        videoView = findViewById(R.id.videoview_main);
        progressBar = findViewById(R.id.progressBar_main);

        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadVideo();
            }
        });

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO || resultCode == RESULT_OK || data != null || data.getData()  !=null){

            try {
                videoUri = data.getData();
                videoView.setVideoURI(videoUri);
            }catch (Exception e){
                Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
            }

        }



    }
    public void ChooseVideo(View view) {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO);
    }

    private String getExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void ShowVideo (View view){
        Intent intent = new Intent(Upload.this, ShowVideo.class);
        startActivity(intent);
    }



    private  void UploadVideo() {
        String videoName = editText.getText().toString();
        String Description = description.getText().toString();
        String search = editText.getText().toString().toLowerCase();


        if (videoUri != null || !TextUtils.isEmpty(videoName) || !TextUtils.isEmpty(Description)) {
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getExt(videoUri));
            uploadTask = reference.putFile(videoUri);

            Task<Uri> uritask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                Uri downloadUrl = task.getResult();
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(Upload.this, "Data saved", Toast.LENGTH_SHORT).show();
                                member.setName(videoName);
                                member.setDescription(Description);
                                member.setVideourl(downloadUrl.toString());
                                member.setSearch(search);
                                String i = databaseReference.push().getKey();
                                databaseReference.child(i).setValue(member);

                            }else {
                                Toast.makeText(Upload.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });





        }}


}

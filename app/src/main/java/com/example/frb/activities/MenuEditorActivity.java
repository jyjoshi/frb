package com.example.frb.activities;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.frb.R;
import com.example.frb.models.MenuItem;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;

public class    MenuEditorActivity extends AppCompatActivity {
    private int check;

    private ImageView imageView;
    private ProgressBar progressBar;
    private Uri imageUri;
    private Uri compressedImageUri;
    private String stringUri;

    private EditText foodName;
    private EditText foodPrice;
    private EditText foodDescription;

    private Bitmap bitmap;

    private File tempDir;
    private File tempFile;

    private FileOutputStream fos;

    private final StorageReference reference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        check = 0;
        stringUri="";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_editor);

        Button addImage = findViewById(R.id.addImage);
        imageView = findViewById(R.id.imageView2);
        progressBar = findViewById(R.id.progressBar);

        foodName = findViewById(R.id.editFoodName);
        foodDescription = findViewById(R.id.editFoodDescription);
        foodPrice = findViewById(R.id.editPrice);

        progressBar.setVisibility(View.INVISIBLE);

        tempDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        imageView.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, 2);

        });
        
        addImage.setOnClickListener(v -> {
            if(imageUri!=null){
                uploadToFirebaseStorage(imageUri);
            }
            else{
                Toast.makeText(com.example.frb.activities.MenuEditorActivity.this, "Image upload failed. No image detected, so please select an image to upload.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            //Following lines of codes for compressing image; but it was observed that the image quality was noticably reduced, so not implementing it.
//            if (Build.VERSION.SDK_INT >= 29){
//                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageUri);
//                try {
//                    bitmap = ImageDecoder.decodeBitmap(source);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            else{
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
//            if(!tempDir.exists()){
//                tempDir.mkdir();
//            }
//            try {
//                tempFile = File.createTempFile("tempImage", ".jpg", tempDir);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
//            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
//            byte[] bitmapData = os.toByteArray();
//
//            try {
//                fos = new FileOutputStream(tempFile);
//                fos.write(bitmapData);
//                fos.flush();
//                fos.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            compressedImageUri = Uri.fromFile(tempFile);



            imageView.setImageURI(imageUri);

        }
        else{
            Toast.makeText(this, "No image selected", Toast.LENGTH_LONG).show();
        }
    }

    public void addToMenu(View view) {
        checkDataEntered();
        if (check == 1) {
            if (stringUri.equals("")) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup_layout, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                Button yesbutton = popupView.findViewById(R.id.yesbtn);
                Button nobutton = popupView.findViewById(R.id.nobtn);

                yesbutton.setOnClickListener(v -> {
                    check = 1;
                    popupWindow.dismiss();
                });

                nobutton.setOnClickListener(v -> {
                    check = 0;
                    popupWindow.dismiss();
                });

            }
        }
        if(check==1)
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String key = database.getReference("Menu").push().getKey();
            MenuItem menuItem = new MenuItem(
                    stringUri,
                    key,
                    foodName.getText().toString(),
                    foodDescription.getText().toString(),
                    foodPrice.getText().toString()
            );
            assert key != null;
            database.getReference().child("Menu").child(key).setValue(menuItem);
            Toast.makeText(this, "Item added successfully", Toast.LENGTH_LONG).show();
            stringUri = "";
            foodName.setText("");
            foodDescription.setText("");
            foodPrice.setText("");
            imageUri = null;
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_add_photo_alternate_24));

        }

    }

    private void uploadToFirebaseStorage(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getMimeType(this, uri));
        fileRef.putFile(uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
            progressBar.setVisibility(View.INVISIBLE);
            stringUri = uri1.toString();
            Log.i("Value of stringUri", stringUri);
            Toast.makeText(com.example.frb.activities.MenuEditorActivity.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();
            tempFile.delete();
        })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(com.example.frb.activities.MenuEditorActivity.this, "Uploading Failed", Toast.LENGTH_LONG).show();
        });
    }

//    /**
//     * Get the extension type of a particular file, in this case the image file.
//     * @param uri - uniform resource identifier of file
//     * @return File extension type
//     */
//    private String getFileExtension(Uri uri) {
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cr.getType(uri));
//    }

    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    /**
     * Make sure that no fields required for a menu item are left empty.
     */
    private void checkDataEntered() {
        check=1;
        if (isEmpty(foodName)) {
            foodName.setError("Name is required");
            check=0;
        }
        if (isEmpty(foodDescription)) {
            foodDescription.setError("Description is required");
            check=0;
        }

        if (isEmpty(foodPrice)) {
            foodPrice.setError("Last name is required!");
            check=0;
        }
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }
    public static String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }

}

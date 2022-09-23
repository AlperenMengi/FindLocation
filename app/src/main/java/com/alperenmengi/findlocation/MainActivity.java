package com.alperenmengi.findlocation;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alperenmengi.findlocation.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<String> permissionLauncher;
    int latitude;
    int longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        registerLauncher();

    }

    public void showInMaps(View view){

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)){
                Snackbar.make(view,"Permission needed for location!",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //request permission
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                });
            }else{
                //request permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }else{
            //Intent
            Intent intent = new Intent(MainActivity.this,MapsActivity.class);
            intent.putExtra("latitude",Double.parseDouble(binding.latitudeText.getText().toString()));
            intent.putExtra("longitude",Double.parseDouble(binding.longitudeText.getText().toString()));
            startActivity(intent);

        }
    }

    public void registerLauncher(){
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        //permission granted
                        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                        intent.putExtra("latitude",Double.parseDouble(binding.latitudeText.getText().toString()));
                        intent.putExtra("longitude",Double.parseDouble(binding.longitudeText.getText().toString()));
                        startActivity(intent);
                    }
                }else{
                    //permission denied
                    Toast.makeText(MainActivity.this, "Permission needed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
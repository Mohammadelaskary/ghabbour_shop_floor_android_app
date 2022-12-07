package com.example.gbsbadrsf;

import static com.example.gbsbadrsf.MainActivity.getWarningMediaPlayer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieListener;
import com.example.gbsbadrsf.databinding.CustomAlertDialogBinding;


public class CustomDialog extends Dialog {
    private String message;
    private int image;
    public CustomDialog(@NonNull Context context,String message,int image) {
        super(context);
        this.message = message;
        this.image = image;
    }
    CustomAlertDialogBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CustomAlertDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.message.setText(message);
//        binding.imageViewAnim.setFailureListener(result -> {
//            Log.d("AnimationError",result.getMessage());
//        });
//        binding.imageView.setImageResource(image);
        binding.ok.setOnClickListener(v->this.dismiss());
        getWarningMediaPlayer().start();
    }
}

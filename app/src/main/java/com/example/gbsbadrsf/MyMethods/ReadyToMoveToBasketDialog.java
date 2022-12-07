package com.example.gbsbadrsf.MyMethods;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.databinding.ReadyToMoveToBasketDialogLayoutBinding;

public class ReadyToMoveToBasketDialog extends Dialog {
    private String message;
    private OnMoveToBasketClicked onMoveToBasketClicked;
    private OnCancelButtonClicked onCancelButtonClicked;
    private ReadyToMoveToBasketDialogLayoutBinding binding;
    public ReadyToMoveToBasketDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ReadyToMoveToBasketDialogLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.message.setText(message);
        binding.moveToBasket.setOnClickListener(v -> onMoveToBasketClicked.OnMoveToBasketButtonClicked());
        binding.cancel.setOnClickListener(v-> onCancelButtonClicked.OnCancelClicked());
    }

    public void setOnMoveToBasketClicked(OnMoveToBasketClicked onMoveToBasketClicked) {
        this.onMoveToBasketClicked = onMoveToBasketClicked;
    }

    public void setOnCancelButtonClicked(OnCancelButtonClicked onCancelButtonClicked) {
        this.onCancelButtonClicked = onCancelButtonClicked;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public interface OnMoveToBasketClicked{
        void OnMoveToBasketButtonClicked();
    }
    public interface OnCancelButtonClicked{
        void OnCancelClicked();
    }
}

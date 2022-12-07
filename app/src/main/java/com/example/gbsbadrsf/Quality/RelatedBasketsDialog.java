package com.example.gbsbadrsf.Quality;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.WeldingSignInBasketsAdapter;
import com.example.gbsbadrsf.databinding.BasketsDialogBinding;

import java.util.List;

public class RelatedBasketsDialog  extends Dialog {
    public RelatedBasketsDialog(@NonNull Context context) {
            super(context);
            adapter = new RelatedBasketAdapter();
        }

        public void setBaskets(List<Basket> baskets) {
            adapter.setBaskets(baskets);
        }

        public void setBulk(boolean isBulk){
            adapter.setBulk(isBulk);
        }

        private BasketsDialogBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = BasketsDialogBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setUpRecyclerView();
            binding.close.setOnClickListener(v -> dismiss());
        }

        private RelatedBasketAdapter adapter;

        private void setUpRecyclerView() {
            binding.baskets.setAdapter(adapter);
        }
}

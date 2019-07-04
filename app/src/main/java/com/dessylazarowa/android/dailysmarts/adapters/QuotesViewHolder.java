package com.dessylazarowa.android.dailysmarts.adapters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.dessylazarowa.android.dailysmarts.Model.data.QuoteEntity;
import com.dessylazarowa.android.dailysmarts.databinding.QuoteItemBinding;

public class QuotesViewHolder extends RecyclerView.ViewHolder {
    QuoteItemBinding binding;
    QuoteEntity quoteEntity;

    public QuotesViewHolder(final QuoteItemBinding binding, final QuotesListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        binding.heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHeartClicked(quoteEntity);
            }
        });
    }

    public void setQuoteEntity(QuoteEntity quoteEntity) {
        this.quoteEntity = quoteEntity;
    }

    public interface QuotesListener {
        void onHeartClicked(QuoteEntity quoteEntity);
    }
}

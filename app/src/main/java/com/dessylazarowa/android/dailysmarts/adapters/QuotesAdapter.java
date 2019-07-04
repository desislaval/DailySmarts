package com.dessylazarowa.android.dailysmarts.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.dessylazarowa.android.dailysmarts.Model.data.QuoteEntity;
import com.dessylazarowa.android.dailysmarts.Model.data.QuotesRepository;
import com.dessylazarowa.android.dailysmarts.R;
import com.dessylazarowa.android.dailysmarts.databinding.QuoteItemBinding;

public class QuotesAdapter extends ListAdapter<QuoteEntity, QuotesViewHolder> {
    QuotesViewHolder.QuotesListener listener;

    public QuotesAdapter(QuotesViewHolder.QuotesListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<QuoteEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<QuoteEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull QuoteEntity oldItem, @NonNull QuoteEntity newItem) {
            return oldItem.getUid() == newItem.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull QuoteEntity oldItem, @NonNull QuoteEntity newItem) {
            return oldItem.getQuoteText().equals(newItem.getQuoteText()) &&
                    oldItem.getQuoteAuthor().equals(newItem.getQuoteAuthor());
        }
    };

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuoteItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.quote_item, parent, false);
        QuotesViewHolder quotesViewHolder = new QuotesViewHolder(binding, listener);
        return quotesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder holder, int position) {
        QuoteEntity currentQuote = getItem(position);
        holder.binding.quoteTextView.setText(currentQuote.getQuoteText());
        holder.binding.authorTextView.setText(currentQuote.getQuoteAuthor());
        holder.setQuoteEntity(currentQuote);
    }
}

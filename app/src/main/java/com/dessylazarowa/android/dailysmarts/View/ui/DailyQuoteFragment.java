package com.dessylazarowa.android.dailysmarts.View.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dessylazarowa.android.dailysmarts.Model.data.QuoteEntity;
import com.dessylazarowa.android.dailysmarts.Model.service.QuoteModel;
import com.dessylazarowa.android.dailysmarts.Model.service.QuoteViewModel;
import com.dessylazarowa.android.dailysmarts.R;
import com.dessylazarowa.android.dailysmarts.databinding.FragmentDailyQuoteBinding;


public class DailyQuoteFragment extends Fragment {
    private QuoteEntity quoteEntity;
    private FragmentDailyQuoteBinding binding;
    private OnFragmentInteractionListener mListener;
    private com.dessylazarowa.android.dailysmarts.Model.data.QuoteViewModel quoteViewModel;

    public DailyQuoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_daily_quote, container, false);

        setupRetrofit();

        quoteEntity = new QuoteEntity();

        binding.quoteItem.heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseActions();
            }
        });

        binding.quoteItem.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSharingIntent();
            }
        });

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupRetrofit();
            }
        });
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            setupRetrofit();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private void databaseActions() {
        quoteViewModel = ViewModelProviders.of(this).get(com.dessylazarowa.android.dailysmarts.Model.data.QuoteViewModel.class);
        if (quoteEntity.isFavorite()) {
            quoteViewModel.deleteSingle(quoteEntity);
            binding.quoteItem.heartButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border_black_24px));
            quoteEntity.setFavorite(false);
        } else {
            quoteViewModel.insertSingle(quoteEntity);
            binding.quoteItem.heartButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24px));
            quoteEntity.setFavorite(true);
        }
    }

    private void launchSharingIntent() {
        String shareBody = quoteEntity.getQuoteText() + " " + quoteEntity.getQuoteAuthor();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    private void setupRetrofit() {
        QuoteViewModel quoteService = ViewModelProviders.of(this).get(QuoteViewModel.class);
        quoteService.getQuotes().observe(this, new Observer<QuoteModel>() {
            @Override
            public void onChanged(QuoteModel quoteModel) {
                binding.swipeToRefresh.setRefreshing(true);
                binding.quoteItem.quoteTextView.setText(quoteModel.getQuoteText());
                binding.quoteItem.authorTextView.setText(quoteModel.getQuoteAuthor());
                binding.quoteItem.heartButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border_black_24px));
                quoteEntity.setFavorite(false);
                binding.swipeToRefresh.setRefreshing(false);
                quoteEntity.setQuoteAuthor(quoteModel.getQuoteAuthor());
                quoteEntity.setQuoteText(quoteModel.getQuoteText());
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

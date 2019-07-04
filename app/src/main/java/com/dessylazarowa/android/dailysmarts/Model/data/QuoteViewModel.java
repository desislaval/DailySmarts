package com.dessylazarowa.android.dailysmarts.Model.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuoteViewModel extends AndroidViewModel {
    private QuotesRepository quotesRepository;
    LiveData<List<QuoteEntity>> allQuotes;
    public QuoteViewModel(@NonNull Application application) {
        super(application);
        quotesRepository = new QuotesRepository(application);
        allQuotes = quotesRepository.getAllQuotes();
    }

    public void insertSingle(QuoteEntity quoteEntity){
        quotesRepository.insertSingleAsync(quoteEntity);
    }

    public void deleteSingle(QuoteEntity quoteEntity){
        quotesRepository.deleteSingleAsync(quoteEntity);
    }

    public LiveData<List<QuoteEntity>> getAllQuotes(){
        return allQuotes;
    }
}

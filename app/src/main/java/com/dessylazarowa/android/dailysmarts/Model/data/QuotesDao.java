package com.dessylazarowa.android.dailysmarts.Model.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuotesDao {
    @Query("SELECT * FROM QuoteEntity")
    LiveData<List<QuoteEntity>> getAll();

    @Query("DELETE FROM QuoteEntity WHERE quote_text = (:quoteText)")
    void deleteQuote(String quoteText);

    @Insert
    void insert(QuoteEntity quoteEntity);
}

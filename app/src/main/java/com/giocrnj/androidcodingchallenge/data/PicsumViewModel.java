package com.giocrnj.androidcodingchallenge.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.giocrnj.androidcodingchallenge.adapter.PicsumAdapter;

public class PicsumViewModel extends ViewModel {
    public int currentPage = 1;

    public MutableLiveData<PicsumAdapter> adapter = new MutableLiveData<>();
    public MutableLiveData<String> url = new MutableLiveData<>();

}

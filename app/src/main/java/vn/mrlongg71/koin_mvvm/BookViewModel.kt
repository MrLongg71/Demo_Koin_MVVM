package vn.mrlongg71.koin_mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel(private val bookRepository: BookRepository) : ViewModel(),
    Callback<Book> {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _books = MutableLiveData<Book>()
    val books: LiveData<Book>
        get() = _books

    init {
        fetchData()
    }

    private fun fetchData() {
        Log.d("LONgKUTE", " fetch Data: ");
        _isLoading.value = true
        bookRepository.getAllBooks().enqueue(this)
    }

    override fun onFailure(call: Call<Book>, t: Throwable) {
        _isLoading.value = false
        // handle error
        Log.d("LONgKUTE", "faild : ${t.message} ")
    }

    override fun onResponse(call: Call<Book>, response: Response<Book>) {
        Log.d("LONgKUTE", ": responde ");
        if (response.isSuccessful) {
            _books.value = response.body()
        } else {
            // handle error
            Log.d("LONgKUTE", "err: ${response.code()}");
        }
        _isLoading.value = false
    }
}


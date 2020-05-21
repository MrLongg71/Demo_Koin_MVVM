package vn.mrlongg71.koin_mvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val bookViewModel: BookViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bookViewModel.books.observe(this, Observer {
            Log.d("LONgKUTE", "sucess: ");
            // handle UI
            Log.d("LONgKUTE", it.statusCode.toString())
            for (i in it.data){
                Log.d("LONgKUTE", i.name)

            }

        })

        bookViewModel.isLoading.observe(this, Observer {
            // handle UI
            if(it){
                Log.d("LONgKUTE", "loading : true ");
            }
            Log.d("LONgKUTE", "loading : false ");

        })
    }
}

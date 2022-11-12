package com.example.movieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.Pupular
import com.example.movieapp.databinding.ActivityHomeBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HomeFragment : Fragment() {

    private var _binding: ActivityHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = ActivityHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fetchPopularMoviesData().start()

        return root
    }

    private fun fetchPopularMoviesData(): Thread {
        return Thread {
            val url = URL("https://api.themoviedb.org/3/movie/popular?api_key=61d13ec0c9cf5cb4d44ca9442dbf9845")
            val connection = url.openConnection() as HttpURLConnection

            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Pupular::class.java)
                updateUi(request)
                inputStreamReader.close()
                inputSystem.close()
            }
        }
    }

    private fun updateUi(request: Pupular) {
            _binding?.textHome?.text = request.page.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
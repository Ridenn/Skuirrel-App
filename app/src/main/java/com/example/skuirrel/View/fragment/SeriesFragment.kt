package com.example.skuirrel.View.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skuirrel.R
import com.example.skuirrel.ViewModel.SeriesViewModel
import com.example.skuirrel.databinding.FragmentSeriesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class SeriesFragment : Fragment(R.layout.fragment_series) {

    private lateinit var dashboardViewModel: SeriesViewModel

}


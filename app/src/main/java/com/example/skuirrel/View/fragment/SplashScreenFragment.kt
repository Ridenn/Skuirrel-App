package com.example.skuirrel.View.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skuirrel.R

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
    }

    private fun initializeView() {
        Handler(Looper.getMainLooper()).postDelayed({
            loginScreen()
        }, 2000)
    }

    private fun loginScreen() {
        findNavController().navigate(
            SplashScreenFragmentDirections.toAuthentication()
        )
        requireActivity().finishAfterTransition()
    }

}
package com.example.skuirrel.View.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skuirrel.R
import com.example.skuirrel.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        initializeView()
    }

    private fun initializeView() {
        verifyLoggedUser()
        setupButtons()
        handleErrorMessage()
    }

    private fun handleErrorMessage() {
        binding.editEmail.doAfterTextChanged {
            showText("")
        }

        binding.editPassword.doAfterTextChanged {
            showText("")
        }
    }

    private fun setupButtons() {
        binding.txtCadastroNovo.setOnClickListener {

            findNavController().navigate(
                LoginFragmentDirections.toRegistration()
            )
        }

        binding.btnEntrar.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                showText(getString(R.string.fill_fields_text))
            }else{
                authenticateUser()
            }
        }
    }

    private fun authenticateUser() {

        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        showProgress(true)
        hideKeyboard(binding.editEmail)

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(requireActivity().applicationContext, getString(R.string.login_successful_text), Toast.LENGTH_SHORT).show()
                    moviesList()
                }
            }.addOnFailureListener {
                showProgress(false)

                val erro = it

                when{
                    erro is FirebaseAuthInvalidCredentialsException -> showText(getString(R.string.wrong_pass_email_text))
                    erro is FirebaseNetworkException -> showText(getString(R.string.no_connection_text))
                    else -> showText(getString(R.string.login_error_user_text))
                }
            }
    }

    private fun verifyLoggedUser(){
        val loggedUser = FirebaseAuth.getInstance().currentUser
        if(loggedUser != null){
            moviesList()
        }
    }

    private fun moviesList() {

        findNavController().navigate(
            LoginFragmentDirections.toMain()
        )
        requireActivity().finishAfterTransition()
    }

    private fun showText(text: String){
        binding.mensagemErro.setText(text)

//        Handler(Looper.getMainLooper()).postDelayed({
//            binding.mensagemErro.setText("")
//        }, 2000)
    }

    private fun showProgress(show: Boolean) {
        binding.progressBar.isVisible = show
        binding.conteudoTelaLogin.isVisible = !show
    }

    private fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
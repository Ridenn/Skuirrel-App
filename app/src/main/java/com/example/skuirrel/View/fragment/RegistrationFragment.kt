package com.example.skuirrel.View.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.skuirrel.R
import com.example.skuirrel.databinding.FragmentRegistrationBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private lateinit var binding: FragmentRegistrationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)

        initializeView()

    }

    private fun initializeView() {
        setupButtons()
    }

    private fun setupButtons() {
        binding.btnCadastrar.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                showText(getString(R.string.fill_all_fields_text))
            }else{
                registerUser()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(
                    RegistrationFragmentDirections.toLoginScreen()
                )
            }
        })
    }

    private fun registerUser() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        showProgress(true)
        hideKeyboard(binding.editEmail)

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(requireActivity().applicationContext, getString(R.string.user_registered_text), Toast.LENGTH_SHORT).show()

                    binding.editEmail.setText("")
                    binding.editPassword.setText("")
                    binding.msgErro.setText("")

                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(
                            RegistrationFragmentDirections.toSplashScreen()
                        )
                        requireActivity().finishAfterTransition()
                    }, 100)
                }
            }.addOnFailureListener{
                showProgress(false)

                val erro = it

                when{
                    erro is FirebaseAuthWeakPasswordException -> showText(getString(R.string.password_min_character_text))
                    erro is FirebaseAuthUserCollisionException -> showText(getString(R.string.account_already_registered_text))
                    erro is FirebaseNetworkException -> showText(getString(R.string.no_connection_text))
                    else -> showText(getString(R.string.error_register_text))
                }
            }
    }

    private fun showText(text: String){
        binding.msgErro.setText(text)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.msgErro.setText("")
        }, 2000)
    }

    private fun showProgress(show: Boolean) {
        binding.progressBar.isVisible = show
        binding.conteudoTelaRegister.isVisible = !show
    }

    private fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }
}
package com.example.skuirrel.View.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.skuirrel.R
import com.example.skuirrel.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.moviesFragment, R.id.seriesFragment, R.id.favoritesFragment, R.id.logout
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.logout)
            .setOnMenuItemClickListener { menuItem : MenuItem ->
                backToLoginScreen()
                true
            }
    }

    private fun backToLoginScreen() {

        //dialog confirmar logout
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Encerrar sessão desta conta?")
            .setPositiveButton("Sair",
                DialogInterface.OnClickListener { dialog, _ ->
                    FirebaseAuth.getInstance().signOut()
                    dialog.cancel()
                    startActivity(Intent(this, AuthenticationActivity::class.java))
                    finish()
                })
            .setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                })
        builder.create().show()
    }

    private fun setupToolbar(){

        val drawable = resources.getDrawable(R.drawable.gradient_background, theme)

        supportActionBar?.setBackgroundDrawable(drawable)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_layout)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}
package com.example.skuirrel.di

import android.content.Context
import com.example.popularmovies_kotlin.di.modules.ApiModule
import com.example.popularmovies_kotlin.di.modules.AppModule
import com.example.skuirrel.View.activity.AuthenticationActivity
import com.example.skuirrel.View.activity.DetailsActivity
import com.example.skuirrel.View.activity.MainActivity
import com.example.skuirrel.View.activity.SplashScreenActivity
import com.example.skuirrel.View.fragment.DetailsFragment
import com.example.skuirrel.View.fragment.LoginFragment
import com.example.skuirrel.View.fragment.RegistrationFragment
import com.example.skuirrel.View.fragment.SplashScreenFragment
import com.example.skuirrel.View.fragment.FavoritesFragment
import com.example.skuirrel.View.fragment.MoviesFragment
import com.example.skuirrel.View.fragment.SeriesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: AuthenticationActivity)
    fun inject(activity: SplashScreenActivity)
    fun inject(activity: DetailsActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: SplashScreenFragment)
    fun inject(fragment: MoviesFragment)
    fun inject(fragment: SeriesFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: DetailsFragment)
}
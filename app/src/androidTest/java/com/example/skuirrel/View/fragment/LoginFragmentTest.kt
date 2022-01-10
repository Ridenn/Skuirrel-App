package com.example.skuirrel.View.fragment

import android.os.SystemClock
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skuirrel.R
import junit.framework.TestCase
import okhttp3.internal.wait
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest : TestCase() {

    private lateinit var scenario: FragmentScenario<LoginFragment>

    @Before
    fun setup(){
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Skuirrel)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testEmptyFields(){
        onView(withId(R.id.btn_entrar)).perform(click())
        onView(withId(R.id.mensagem_erro))
            .check(matches(withText("Preencha todos os campos!")))
    }

    @Test
    fun testWrongEmailOrPassword(){
        val email = "test@test.com"
        val wrongPassword = "123"

        onView(withId(R.id.edit_email))
            .perform(ViewActions.typeText(email))

        onView(withId(R.id.edit_password))
            .perform(ViewActions.typeText(wrongPassword))

        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_entrar))
            .perform(click())

        SystemClock.sleep(1000);

        onView(withId(R.id.mensagem_erro))
            .check(matches(withText("Email ou senha incorretos")))
    }

}
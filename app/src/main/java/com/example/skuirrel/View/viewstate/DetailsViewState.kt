package com.example.skuirrel.View.viewstate

import com.example.skuirrel.Model.Videos


sealed class DetailsViewState {

    data class Presenting( val results: List<Videos>) : DetailsViewState()

    object Error : DetailsViewState()

    object Loading : DetailsViewState()
}
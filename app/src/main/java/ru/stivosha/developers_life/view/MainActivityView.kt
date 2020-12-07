package ru.stivosha.developers_life.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainActivityView : MvpView {
    fun showProgressBar()
    fun hideProgressBar()
    fun showError(text: String)
    fun showError(text: Int)
    fun setTittleText(text: String)
    fun updatePrevButtonState(isEnabled: Boolean)
    fun showInternetConnectionError()
    fun hideInternetConnectionError()
    fun uploadPostToView(url: String, title: String, index: Int)
}
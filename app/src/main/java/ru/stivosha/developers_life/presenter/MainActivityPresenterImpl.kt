package ru.stivosha.developers_life.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.stivosha.developers_life.R
import ru.stivosha.developers_life.helpers.structures.PostHistory
import ru.stivosha.developers_life.service.PostRepository
import ru.stivosha.developers_life.service.PostRepositoryImpl
import ru.stivosha.developers_life.view.MainActivityView


@InjectViewState
class MainActivityPresenterImpl : MvpPresenter<MainActivityView>(), MainActivityPresenter{

    private val repository: PostRepository = PostRepositoryImpl()
    private val history: PostHistory = PostHistory()

    init {
        viewState.showProgressBar()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = repository.getRandomPost()
                history.add(data)
                launch(Dispatchers.Main) {
                    if(history.currentNode != null){
                        loadCurrentPostToView()
                    }else{
                        viewState.showError(R.string.download_post_error)
                        viewState.hideProgressBar()
                    }
                }
            }catch (e: Exception){
                launch(Dispatchers.Main) { viewState.showInternetConnectionError() }
            }
        }
    }

    override fun loadNextPostToView(){
        if(history.next()){
            loadCurrentPostToView()
        }else{
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val data = repository.getRandomPost()
                    launch(Dispatchers.Main) {
                        if(data.title == null || data.gifURL == null)
                            data.isNotCorrectData = false
                        history.add(data)
                        loadCurrentPostToView()
                    }
                }catch (e: Exception){
                    launch(Dispatchers.Main) { viewState.showInternetConnectionError() }
                }
            }
        }
    }

    override fun loadPrevPostToView(){
        if(!history.prev()){
            viewState.hideProgressBar()
            return
        }
        loadCurrentPostToView()
    }

    override fun loadCurrentPostToView() {
        if(history.currentNode != null && !history.currentNode!!.value.isNotCorrectData){
            viewState.uploadPostToView(history.currentNode!!.value.gifURL!!,
                history.currentNode!!.value.title!!,
                history.currentIndex)
        }else{
            viewState.showError(R.string.download_post_error)
            viewState.hideProgressBar()
            viewState.updatePrevButtonState(history.currentIndex > 0)
        }
    }
}
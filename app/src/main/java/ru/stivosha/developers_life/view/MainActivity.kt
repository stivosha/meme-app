package ru.stivosha.developers_life.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import ru.stivosha.developers_life.R
import ru.stivosha.developers_life.presenter.MainActivityPresenterImpl

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    @InjectPresenter
    lateinit var presenter: MainActivityPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi(){
        button_next.setOnClickListener {
            presenter.viewState.showProgressBar()
            presenter.loadNextPostToView()
        }
        button_prev.setOnClickListener {
            presenter.viewState.showProgressBar()
            presenter.loadPrevPostToView()
        }
        button_retry.setOnClickListener {
            presenter.viewState.hideInternetConnectionError()
            presenter.viewState.showProgressBar()
            presenter.loadNextPostToView()
        }
    }

    override fun showProgressBar() {
        textView_tittle.text = ""
        imageView.visibility = View.INVISIBLE
        button_next.isEnabled = false
        button_prev.isEnabled = false
        circularProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        circularProgressBar.visibility = View.GONE
        button_next.isEnabled = true
        button_prev.isEnabled = true
        imageView.visibility = View.VISIBLE
    }

    override fun showError(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun showError(text: Int) {
        Toast.makeText(this, getString(text), Toast.LENGTH_LONG).show()
    }

    override fun setTittleText(text: String) {
        textView_tittle.text = text
    }

    override fun updatePrevButtonState(isEnabled: Boolean){
        button_prev.isEnabled = isEnabled
    }

    override fun showInternetConnectionError() {
        imageView.visibility = View.GONE
        button_block.visibility = View.GONE
        circularProgressBar.visibility = View.GONE
        imageView_block.visibility = View.GONE
        textView_error.visibility = View.VISIBLE
        button_retry.visibility = View.VISIBLE
    }

    override fun hideInternetConnectionError() {
        textView_error.visibility = View.GONE
        button_retry.visibility = View.GONE
        button_block.visibility = View.VISIBLE
        imageView_block.visibility = View.VISIBLE
    }

    override fun uploadPostToView(url: String, title: String, index: Int) {
        Glide
            .with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    showInternetConnectionError()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    hideProgressBar()
                    updatePrevButtonState(index > 0)
                    setTittleText(title)
                    return false
                }
            })
            .apply(RequestOptions().override(300, 300))
            .into(imageView)
    }
}

package balitskyivan.presentation.presenter

import balitskyivan.presentation.di.Injector
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.entities.ChannelDetails
import balitskyivan.presentation.domain.entities.VkChannelDetails
import balitskyivan.presentation.domain.interactors.IChannelInteractor
import balitskyivan.presentation.interfaces.ChannelDetailsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class ChannelDetailsPresenter : MvpPresenter<ChannelDetailsView>() {

    @Inject
    lateinit var channelInteractor: IChannelInteractor

    init {
        Injector.channelDetailsFragmentPresenterComponent.inject(this)
    }

    fun requestChannelDetails(account: Account, channel: Channel, appId: Int) {
        channelInteractor.getChannelDetails(channel, account, appId)
            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<ChannelDetails> {
                    override fun onSubscribe(d: Disposable?) {

                    }

                    override fun onNext(t: ChannelDetails?) {
                        if (t != null)
                            viewState.initStatistic(
                                (t as VkChannelDetails).comments,
                                t.likes,
                                t.subscribed,
                                t.unsubscribed
                            )
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {
                    }

                }
            )

    }
}
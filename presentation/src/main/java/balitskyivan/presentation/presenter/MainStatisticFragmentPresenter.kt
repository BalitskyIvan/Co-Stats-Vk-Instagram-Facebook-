package balitskyivan.presentation.presenter

import balitskyivan.presentation.di.Injector
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.entities.InstagramAccount
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.domain.interactors.IAccountInteractor
import balitskyivan.presentation.domain.interactors.IChannelInteractor
import balitskyivan.presentation.interfaces.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject
import kotlin.concurrent.thread

@InjectViewState
class MainStatisticFragmentPresenter : MvpPresenter<MainStatisticFragment>() {

    @Inject
    lateinit var accountInteractor: IAccountInteractor

    @Inject
    lateinit var channelInteractor: IChannelInteractor

    init {
        Injector.mainStatisticFragmentPresenter.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestAllAccounts()
        requestAllChannels()
    }

    fun accountsUpdate(account: Account) {
        viewState.updateAccounts(account)
        thread {
            Observable.just(accountInteractor.updateAccount(account))
                .subscribeOn(Schedulers.io())
        }
    }

    fun channelsUpdate(channel: Channel) {
        viewState.updateChannels(channel)
        thread {
            Observable.just(channelInteractor.updateChannel(channel))
                .subscribeOn(Schedulers.io())
        }
    }

    fun accountAdd(account: Account) {
        when (account.accountType) {
            SocialNetworks.VK_TYPE -> addVkAccount(account as VkAccount)
            SocialNetworks.INSTAGRAM_TYPE -> addInstagramAccount(account as InstagramAccount)
        }
        thread {
            Observable.just(accountInteractor.addAccount(account))
                .subscribeOn(Schedulers.io())
        }
    }

    fun channelAdd(channel: Channel) {
        viewState.updateChannels(channel)
        thread {
            Observable.just(channelInteractor.addChannel(channel))
                .subscribeOn(Schedulers.io())
        }
    }

    override fun attachView(view: MainStatisticFragment?) {
        super.attachView(view)

    }

    private fun requestAllAccounts() {
        thread {
            accountInteractor.getAllAccounts().forEach {
                it.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Account> {
                        override fun onSubscribe(d: Disposable?) {

                        }

                        override fun onNext(t: Account?) {
                            if (t != null)
                                accountsUpdate(t)
                        }

                        override fun onError(e: Throwable?) {

                        }

                        override fun onComplete() {

                        }

                    })
            }
        }
    }

    private fun addVkAccount(account: VkAccount) {
        thread {
            accountInteractor.fillAccount(account).subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe(object : Observer<Account> {
                override fun onSubscribe(d: Disposable?) {
                    println("hwehfew")
                }

                override fun onNext(it: Account?) {
                    if (it != null)
                        accountsUpdate(it)
                }

                override fun onError(e: Throwable?) {
                    println("hwehfew")
                }

                override fun onComplete() {
                    println("hwehfew")
                }

            })
        }
    }

    private fun addInstagramAccount(account: InstagramAccount) {
        thread {
            accountInteractor.fillAccount(account).subscribeOn(Schedulers.newThread())?.observeOn(
                AndroidSchedulers.mainThread()
            )?.subscribe(object : Observer<Account> {
                override fun onSubscribe(d: Disposable?) {
                    println("hwehfew")
                }

                override fun onNext(it: Account?) {
                    if (it != null)
                        accountsUpdate(it)
                }

                override fun onError(e: Throwable?) {
                    println("hwehfew")
                }

                override fun onComplete() {
                    println("hwehfew")
                }

            })
        }
    }

    private fun requestAllChannels() {
        thread {
            channelInteractor.getAllChannels().forEach {
                it.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Channel> {
                        override fun onSubscribe(d: Disposable?) {

                        }

                        override fun onNext(t: Channel?) {
                            if (t != null)
                                channelsUpdate(t)
                        }

                        override fun onError(e: Throwable?) {

                        }

                        override fun onComplete() {

                        }

                    })
            }
        }
    }


    override fun detachView(view: MainStatisticFragment?) {
        super.detachView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
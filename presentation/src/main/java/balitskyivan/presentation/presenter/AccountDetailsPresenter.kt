package balitskyivan.presentation.presenter

import balitskyivan.presentation.di.Injector
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.AccountDetails
import balitskyivan.presentation.domain.entities.InstagramAccountDetails
import balitskyivan.presentation.domain.entities.VkAccountDetails
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.domain.interactors.IAccountInteractor
import balitskyivan.presentation.interfaces.AccountDetailsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.thread

class AccountDetailsPresenter : MvpPresenter<AccountDetailsView>() {

    @Inject
    lateinit var accountInteractor: IAccountInteractor

    init {
        Injector.accountDetailsFragmentPresenterComponent.inject(this)
    }


    fun requestAccountDetails(account: Account) {
        accountInteractor.getAccountDetails(account).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<AccountDetails> {
            override fun onSubscribe(d: Disposable?) {
                println("completes")
            }

            override fun onNext(t: AccountDetails?) {
                if (t != null)
                    when (t.type) {
                        SocialNetworks.VK_TYPE -> viewState.initStatistic(
                            (t as VkAccountDetails).friendsCount,
                            t.subscribersCount,
                            t.nowOnline
                        )
                        SocialNetworks.INSTAGRAM_TYPE -> viewState.initStatistic(
                            (t as InstagramAccountDetails).subscribersCount,
                            t.followsCount,
                            t.profileViews
                        )
                    }
            }

            override fun onError(e: Throwable?) {
                println("completes")
            }

            override fun onComplete() {
                println("completes")
            }

        })
    }

}
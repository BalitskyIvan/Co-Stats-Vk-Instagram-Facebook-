package balitskyivan.presentation.presenter

import balitskyivan.presentation.adapters.RecyclerAccountsAdapter
import balitskyivan.presentation.adapters.RecyclerChannelsAdapter
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.interactors.AccountInteractor
import balitskyivan.presentation.domain.interactors.ChannelInteractor
import balitskyivan.presentation.interfaces.MainStatistic
import moxy.InjectViewState
import moxy.MvpPresenter
import kotlin.concurrent.thread

class MainStatisticPresenter : MvpPresenter<MainStatistic>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

    }

    override fun attachView(view: MainStatistic?) {
        super.attachView(view)
    }

    override fun detachView(view: MainStatistic?) {
        super.detachView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
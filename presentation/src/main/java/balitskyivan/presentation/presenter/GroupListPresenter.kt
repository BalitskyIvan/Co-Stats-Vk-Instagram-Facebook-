package balitskyivan.presentation.presenter

import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.interactors.AccountInteractor
import balitskyivan.presentation.domain.interactors.ChannelInteractor
import balitskyivan.presentation.domain.interactors.IChannelInteractor
import balitskyivan.presentation.interfaces.GroupListView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

class GroupListPresenter(private val channelInteractor: IChannelInteractor) :
    MvpPresenter<GroupListView>() {


    var groupList: MutableList<Channel>? = mutableListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun sendGroupListRequest(account: Account) {
        channelInteractor.getChannelsList(account).subscribeOn(Schedulers.newThread())?.observeOn(
            AndroidSchedulers.mainThread()
        )?.subscribe(object : Observer<List<Channel>> {
            override fun onSubscribe(d: Disposable?) {
            }

            override fun onNext(it: List<Channel>?) {
                it!!.forEach { groupList!!.add(it) }
                viewState.groupListUpdated()
            }

            override fun onError(e: Throwable?) {
            }

            override fun onComplete() {
            }


        })
    }

    override fun attachView(view: GroupListView?) {
        super.attachView(view)
    }

    override fun detachView(view: GroupListView?) {
        super.detachView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
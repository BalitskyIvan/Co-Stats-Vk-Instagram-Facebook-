package balitskyivan.presentation.interfaces

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

interface AccountDetailsView : MvpView {
    @AddToEnd
    fun initStatistic(followersCount: Int, followsCount: Int, onlineCount: Int)
}
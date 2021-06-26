package balitskyivan.presentation.interfaces

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ChannelDetailsView : MvpView {
    @AddToEndSingle
    fun initStatistic(comments: Int, likes: Int, subscribed: Int, unsubscribed: Int)
}
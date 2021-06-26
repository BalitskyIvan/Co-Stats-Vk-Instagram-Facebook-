package balitskyivan.presentation.interfaces

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEnd

interface GroupListView : MvpView {
    @AddToEnd
    fun groupListUpdated()
}
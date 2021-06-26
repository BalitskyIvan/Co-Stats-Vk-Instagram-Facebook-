package balitskyivan.presentation.interfaces

import balitskyivan.presentation.domain.entities.Account
import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEnd

interface MainStatistic : MvpView {
    @AddToEnd
    fun accountsUpdated(account: Account)
}
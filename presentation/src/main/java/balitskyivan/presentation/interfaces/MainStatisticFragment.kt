package balitskyivan.presentation.interfaces

import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import moxy.viewstate.strategy.alias.Skip

interface MainStatisticFragment : MvpView {
    @AddToEnd
    fun updateAccounts(account: Account)

    @AddToEnd
    fun updateChannels(channel: Channel)
}
package balitskyivan.presentation.interfaces

import balitskyivan.presentation.domain.entities.Account
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

interface AddItemView : MvpView {
    @AddToEnd
    fun accountAdded(account : Account)
}
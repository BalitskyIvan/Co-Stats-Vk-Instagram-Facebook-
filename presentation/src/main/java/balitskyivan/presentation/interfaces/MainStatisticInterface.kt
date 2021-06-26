package balitskyivan.presentation.interfaces

import balitskyivan.presentation.domain.entities.Account

interface MainStatisticInterface : AccountClickedHandler, ChannelsClickedHandler {
    fun addAccount(account: Account)
}
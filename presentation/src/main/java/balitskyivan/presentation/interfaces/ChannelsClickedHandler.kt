package balitskyivan.presentation.interfaces

import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.enums.SocialNetworks

interface ChannelsClickedHandler {
    fun onChannelClicked(account: Account, channel: Channel)
    fun onAddChannelClicked(accountId: Long, accessToken: String, type: SocialNetworks)
    fun addChannel(account: Channel)
}
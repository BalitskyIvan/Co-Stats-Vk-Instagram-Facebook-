package balitskyivan.presentation.domain.interactors

import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.entities.ChannelDetails
import balitskyivan.presentation.domain.repository.ICommonRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

interface IChannelInteractor {

    fun getAllChannels(): List<Observable<Channel>>
    fun fillChannel(account: Account, channel: Channel): Observable<Channel>
    fun getChannelsList(account: Account): Observable<List<Channel>>
    fun getChannelDetails(
        channel: Channel,
        account: Account,
        appId: Int
    ): Observable<ChannelDetails>

    fun addChannel(channel: Channel)
    fun updateChannel(channel: Channel)
    fun deleteChannel(channel: Channel)

}

class ChannelInteractor @Inject constructor(private val repository: ICommonRepository) :
    IChannelInteractor {

    override fun getAllChannels(): List<Observable<Channel>> = repository.getAllChannels()

    override fun fillChannel(account: Account, channel: Channel): Observable<Channel> =
        repository.fillChannel(account, channel)

    override fun getChannelsList(account: Account): Observable<List<Channel>> =
        repository.getChannelsList(account)

    override fun getChannelDetails(
        channel: Channel,
        account: Account,
        appId: Int
    ): Observable<ChannelDetails> =
        repository.getChannelDetails(channel, account, appId)

    override fun addChannel(channel: Channel) {
        repository.addChannel(channel)
    }

    override fun updateChannel(channel: Channel) {
        repository.updateChannel(channel)
    }

    override fun deleteChannel(channel: Channel) = repository.deleteChannel(channel)

}
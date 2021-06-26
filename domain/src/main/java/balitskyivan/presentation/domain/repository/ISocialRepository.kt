package balitskyivan.presentation.domain.repository

import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.AccountDetails
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.entities.ChannelDetails
import io.reactivex.rxjava3.core.Observable

interface ISocialRepository {

    fun fillAccount(account: Account): Observable<Account>
    fun fillChannel(account: Account, channel: Channel): Observable<Channel>

    fun getChannelsList(account: Account): Observable<List<Channel>>
    fun getAccountDetails(account: Account): Observable<AccountDetails>
    fun getChannelDetails(
        channel: Channel,
        account: Account,
        appId: Int
    ): Observable<ChannelDetails>

}
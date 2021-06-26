package balitskyivan.presentation.domain.repository

import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import io.reactivex.rxjava3.core.Observable

interface ICommonRepository : ISocialRepository {

    fun getAllAccounts(): List<Observable<Account>>
    fun getAllChannels(): List<Observable<Channel>>

    fun updateAccount(account: Account)
    fun updateChannel(channel: Channel)

    fun addAccount(account: Account)
    fun addChannel(channel: Channel)

    fun deleteAccount(account: Account)
    fun deleteChannel(channel: Channel)

}
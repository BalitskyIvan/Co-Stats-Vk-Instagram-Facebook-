package balitskyivan.presentation.data.repository

import balitskyivan.presentation.data.database.DatabaseStorage
import balitskyivan.presentation.data.exceptions.AccessTokenExpiredException
import balitskyivan.presentation.data.exceptions.AccountNotFoundException
import balitskyivan.presentation.data.network.vkApi.VkApi
import balitskyivan.presentation.domain.entities.*
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.domain.repository.ISocialRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class VkRepository @Inject constructor(
    private val databaseStorage: DatabaseStorage,
) : ISocialRepository {

    private val vkApi = VkApi()

    override fun fillAccount(account: Account): Observable<Account> = try {
        vkApi.fillVkAccount(account as VkAccount) as Observable<Account>
    } catch (e: AccessTokenExpiredException) {
        Observable.just(databaseStorage.vkAccountDao.findAccountById(account.id)).doOnNext {
            it.error = ErrorTypes.ACCESS_TOKEN_EXPIRED
        } as Observable<Account>
    } catch (e: AccountNotFoundException) {
        Observable.just(databaseStorage.vkAccountDao.findAccountById(account.id)).doOnNext {
            it.error = ErrorTypes.ITEM_NOT_FOUND
        } as Observable<Account>
    } catch (e: Exception) {
        Observable.just(databaseStorage.vkAccountDao.findAccountById(account.id))
    }

    override fun fillChannel(account: Account, channel: Channel): Observable<Channel> = try {
        vkApi.fillGroup(account as VkAccount, channel as VkChannel) as Observable<Channel>
    } catch (e : AccessTokenExpiredException) {
        Observable.just(channel.apply { error = ErrorTypes.ACCESS_TOKEN_EXPIRED })
    } catch (e : Exception) {
        Observable.just(channel.apply { error = ErrorTypes.ITEM_NOT_FOUND })
    }

    override fun getChannelsList(account: Account): Observable<List<Channel>> = try {
        vkApi.getGroupList(
            account.id,
            account.accessToken
        ) as Observable<List<Channel>>
    } catch (e: AccessTokenExpiredException) {
        Observable.just(
            mutableListOf(createEmptyChannel().apply { error = ErrorTypes.ACCESS_TOKEN_EXPIRED })
        )
    } catch (e: Exception) {
        Observable.just(
            mutableListOf(
                createEmptyChannel().apply { error = ErrorTypes.ITEM_NOT_FOUND }
            )
        )
    }

    override fun getAccountDetails(account: Account): Observable<AccountDetails> = try {
        vkApi.getAccountDetails(account as VkAccount) as Observable<AccountDetails>
    } catch (e: AccessTokenExpiredException) {
        Observable.just(databaseStorage.vkAccountDetails.findAccountById(account.id)).doOnNext {
            it.error = ErrorTypes.ACCESS_TOKEN_EXPIRED
        } as Observable<AccountDetails>
    } catch (e: AccountNotFoundException) {
        Observable.just(databaseStorage.vkAccountDetails.findAccountById(account.id)).doOnNext {
            it.error = ErrorTypes.ITEM_NOT_FOUND
        } as Observable<AccountDetails>
    } catch (e: Exception) {
        Observable.just(databaseStorage.vkAccountDetails.findAccountById(account.id))
    }

    override fun getChannelDetails(channel: Channel, account: Account, appId : Int): Observable<ChannelDetails> =
        try {
            vkApi.getGroupDetails(
                account as VkAccount,
                channel as VkChannel, appId
            ) as Observable<ChannelDetails>
        } catch (e: AccessTokenExpiredException) {
            Observable.just(VkChannelDetails(channel.id, ErrorTypes.ACCESS_TOKEN_EXPIRED, 0,0,0,0))
        } catch (e: Exception) {
            Observable.just(VkChannelDetails(channel.id, ErrorTypes.ITEM_NOT_FOUND, 0,0,0,0))
        }

    private fun createEmptyChannel() = VkChannel(
        0,
        ErrorTypes.NONE,
        null,
        null,
        SocialNetworks.VK_TYPE,
        0,
    )
}
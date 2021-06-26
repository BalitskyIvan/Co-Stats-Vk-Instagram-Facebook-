package balitskyivan.presentation.data.repository

import balitskyivan.presentation.data.database.DatabaseStorage
import balitskyivan.presentation.data.exceptions.AccessTokenExpiredException
import balitskyivan.presentation.data.exceptions.AccountNotFoundException
import balitskyivan.presentation.data.exceptions.ChannelNotFoundException
import balitskyivan.presentation.data.network.facebookApi.InstApi
import balitskyivan.presentation.domain.entities.*
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.repository.ISocialRepository
import io.reactivex.rxjava3.core.Observable
import java.lang.Exception
import javax.inject.Inject

class InstRepository @Inject constructor(
    private val databaseStorage: DatabaseStorage,
) : ISocialRepository {

    private val instApi = InstApi()

    override fun fillAccount(account: Account): Observable<Account> = try {
        instApi.fillInstAccount(account as InstagramAccount) as Observable<Account>
    } catch (e : AccessTokenExpiredException) {
        Observable.just(databaseStorage.instagramAccountDao.findAccountById(account.id)).doOnNext {
            it.error = ErrorTypes.ACCESS_TOKEN_EXPIRED
        } as Observable<Account>
    } catch (e : AccountNotFoundException) {
        Observable.just(databaseStorage.instagramAccountDao.findAccountById(account.id)).doOnNext {
            it.error = ErrorTypes.ITEM_NOT_FOUND
        } as Observable<Account>
    } catch (e : Exception) {
        Observable.just(databaseStorage.instagramAccountDao.findAccountById(account.id))
    }

    override fun fillChannel(account: Account, channel: Channel): Observable<Channel> {
        throw ChannelNotFoundException()
    }

    override fun getChannelsList(account: Account): Observable<List<Channel>> {
        throw ChannelNotFoundException()
    }

    override fun getAccountDetails(account: Account): Observable<AccountDetails> = try {
        instApi.getAccountDetails(account as InstagramAccount) as Observable<AccountDetails>
    } catch (e : AccountNotFoundException) {
        Observable.just(databaseStorage.instagramAccountDetails.findAccountById(account.id)).doOnNext {
            it.error = ErrorTypes.ITEM_NOT_FOUND
        } as Observable<AccountDetails>
    } catch (e : Exception) {
        Observable.just(databaseStorage.instagramAccountDetails.findAccountById(account.id))
    }

    override fun getChannelDetails(channel: Channel, account: Account, appId : Int): Observable<ChannelDetails> {
        throw ChannelNotFoundException()
    }

}
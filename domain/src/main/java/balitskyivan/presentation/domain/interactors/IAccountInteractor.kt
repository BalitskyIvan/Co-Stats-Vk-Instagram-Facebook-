package balitskyivan.presentation.domain.interactors

import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.AccountDetails
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.repository.ICommonRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

interface IAccountInteractor {

    fun fillAccount(account: Account): Observable<Account>

    fun getAllAccounts(): List<Observable<Account>>

    fun getAccountDetails(account: Account): Observable<AccountDetails>

    fun deleteAccount(account: Account)

    fun addAccount(account: Account)

    fun updateAccount(account: Account)
}

class AccountInteractor @Inject constructor(private val repository: ICommonRepository) :
    IAccountInteractor {

    override fun fillAccount(account: Account): Observable<Account> =
        repository.fillAccount(account)

    override fun getAllAccounts(): List<Observable<Account>> = repository.getAllAccounts()

    override fun getAccountDetails(account: Account): Observable<AccountDetails> =
        repository.getAccountDetails(account)

    override fun deleteAccount(account: Account) = repository.deleteAccount(account)

    override fun addAccount(account: Account) {
        repository.addAccount(account)
    }

    override fun updateAccount(account: Account) {
        repository.updateAccount(account)
    }


}
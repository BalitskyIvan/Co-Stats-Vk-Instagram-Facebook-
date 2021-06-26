package balitskyivan.presentation.interfaces

import balitskyivan.presentation.domain.entities.Account

interface AccountClickedHandler {
    fun onAccountClicked(account: Account)
    fun onAddAccountClicked()
}
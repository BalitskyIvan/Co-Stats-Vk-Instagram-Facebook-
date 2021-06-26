package balitskyivan.presentation.presenter

import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.InstagramAccount
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.domain.interactors.AccountInteractor
import balitskyivan.presentation.domain.interactors.IAccountInteractor
import balitskyivan.presentation.interfaces.AddItemView
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class AddItemPresenter @Inject constructor(val accountInteractor: IAccountInteractor) :
    MvpPresenter<AddItemView>() {


    override fun attachView(view: AddItemView?) {
        super.attachView(view)
    }

    fun registerFacebookCallback(): CallbackManager {
        val callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    viewState.accountAdded(
                        createEmptyIntstagramAccount(
                            loginResult!!.accessToken.token
                        )
                    )
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException?) {
                }
            })
        return callbackManager
    }

    override fun detachView(view: AddItemView?) {
        super.detachView(view)
    }

    private fun createEmptyIntstagramAccount(
        accessToken: String
    ) = InstagramAccount(
        0,
        ErrorTypes.NONE,
        null,
        null,
        null,
        SocialNetworks.INSTAGRAM_TYPE,
        0,
        accessToken
    )
}
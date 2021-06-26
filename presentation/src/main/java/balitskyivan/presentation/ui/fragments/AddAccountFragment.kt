package balitskyivan.presentation.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import balitskyivan.presentation.R
import balitskyivan.presentation.di.Injector
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.interfaces.AddItemView
import balitskyivan.presentation.interfaces.MainStatisticInterface
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.interactors.AccountInteractor
import balitskyivan.presentation.domain.interactors.ChannelInteractor
import balitskyivan.presentation.domain.interactors.IAccountInteractor
import balitskyivan.presentation.presenter.AddItemPresenter
import balitskyivan.presentation.presenter.GroupListPresenter
import balitskyivan.presentation.presenter.MainStatisticFragmentPresenter
import com.facebook.CallbackManager
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import moxy.MvpAppCompatDialogFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider


class AddAccountFragment : MvpAppCompatDialogFragment(), AddItemView {

    private var facebookCallbackManager: CallbackManager? = null
    private var addAccountHandler: MainStatisticInterface? = null

    private val presenter by moxyPresenter { AddItemPresenter(accountInteractor) }

    @Inject
    lateinit var accountInteractor: IAccountInteractor

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addAccountHandler = context as MainStatisticInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.addAccountFragment.inject(this)
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        facebookCallbackManager = presenter.registerFacebookCallback()
        view.findViewById<ImageView>(R.id.vk_imageButton).setOnClickListener {
            VK.login(requireActivity(), arrayListOf(VKScope.GROUPS, VKScope.FRIENDS, VKScope.STATS))
        }
        view.findViewById<ImageView>(R.id.instagram_icon).setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            val addFragment = AddFacebookAccountFragment.newInstance()
            addFragment.show(transaction, "SOME_TAG")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun onVkResult(): VKAuthCallback = object : VKAuthCallback {
        override fun onLogin(token: VKAccessToken) {
            accountAdded(
                VkAccount(
                    token.userId!!.toLong(),
                    ErrorTypes.NONE,
                    null,
                    null,
                    null,
                    SocialNetworks.VK_TYPE,
                    null,
                    token.accessToken
                )
            )
        }

        override fun onLoginFailed(errorCode: Int) {

        }
    }

    override fun accountAdded(account: Account) {
        addAccountHandler!!.addAccount(account)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddAccountFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
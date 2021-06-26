package balitskyivan.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.domain.interactors.AccountInteractor
import balitskyivan.presentation.domain.interactors.IAccountInteractor
import balitskyivan.presentation.interfaces.*
import balitskyivan.presentation.presenter.MainStatisticFragmentPresenter
import balitskyivan.presentation.presenter.MainStatisticPresenter
import balitskyivan.presentation.ui.fragments.*
import balitskyivan.presentation.ui.fragments.MainStatisticFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthCallback
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainStatisticActivity : MvpAppCompatActivity(R.layout.activity_main), MainStatistic,
    MainStatisticInterface {

    private val presenter by moxyPresenter { MainStatisticPresenter() }

    private var addAccountFragment: AddAccountFragment? = null
    private var addChannelFragment: GroupListFragment? = null
    private var accountDetailsFragment: AccountDetailsFragment? = null
    private var mainStatisticFragment: MainStatisticFragment? = null
    private var channelFragment: ChannelDetailsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                mainStatisticFragment = MainStatisticFragment.newInstance()
                replace(R.id.fragmentContainerView, mainStatisticFragment!!)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback: VKAuthCallback?
        if (requestCode == 282 && addAccountFragment != null) {
            callback = addAccountFragment!!.onVkResult()
            if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
                super.onActivityResult(requestCode, resultCode, data)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun addAccount(account: Account) {
        if (mainStatisticFragment != null)
            mainStatisticFragment!!.accountAdd(account)
    }

    override fun onAccountClicked(account: Account) {
        supportFragmentManager.commit {
            this.addToBackStack(null)
            accountDetailsFragment = AccountDetailsFragment.newInstance(account)
            replace(R.id.fragmentContainerView, accountDetailsFragment!!)
        }
    }

    override fun onAddAccountClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        addAccountFragment = AddAccountFragment.newInstance()
        addAccountFragment!!.show(transaction, "SOME_TAG")
    }

    override fun onChannelClicked(account: Account, channel: Channel) {
        supportFragmentManager.commit {
            this.addToBackStack(null)
            channelFragment = ChannelDetailsFragment.newInstance(account, channel)
            replace(R.id.fragmentContainerView, channelFragment!!)
        }
    }

    override fun onAddChannelClicked(accountId: Long, accessToken: String, type: SocialNetworks) {
        supportFragmentManager.commit {
            this.addToBackStack(null)
            addChannelFragment = GroupListFragment.newInstance(accountId, accessToken, type)
            replace(R.id.fragmentContainerView, addChannelFragment!!)
        }
    }

    override fun addChannel(channel: Channel) {
        mainStatisticFragment!!.channelAdd(channel)
    }

    override fun accountsUpdated(account: Account) {

    }
}
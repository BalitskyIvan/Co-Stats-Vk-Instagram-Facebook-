package balitskyivan.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import balitskyivan.presentation.R
import balitskyivan.presentation.adapters.RecyclerAccountsAdapter
import balitskyivan.presentation.adapters.RecyclerChannelsAdapter
import balitskyivan.presentation.di.Injector
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.presenter.MainStatisticFragmentPresenter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class MainStatisticFragment : MvpAppCompatFragment(),
    balitskyivan.presentation.interfaces.MainStatisticFragment {

    private var accountsRecyclerView: RecyclerView? = null
    private var channelsRecyclerView: RecyclerView? = null
    private var accountRecyclerAdapter: RecyclerAccountsAdapter? = null
    private var channelRecyclerAdapter: RecyclerChannelsAdapter? = null
    private var accountList: MutableList<Account>? = null
    private var channelsList: MutableList<Channel>? = null

    @InjectPresenter
    lateinit var presenter: MainStatisticFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_statistic, container, false)
    }

    fun accountAdd(account: Account) {
        presenter.accountAdd(account)
    }

    fun channelAdd(channel: Channel) {
        presenter.channelAdd(channel)
    }

    fun accountsUpdate(account: Account) {
        presenter.accountsUpdate(account)
    }

    fun channelsUpdate(channel: Channel) {
        presenter.channelsUpdate(channel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountList = mutableListOf()
        channelsList = mutableListOf()

        accountRecyclerAdapter = RecyclerAccountsAdapter(accountList!!)
        channelRecyclerAdapter = RecyclerChannelsAdapter(channelsList!!)

        channelsRecyclerView = view.findViewById(R.id.recyclerChannels)
        channelsRecyclerView?.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        channelsRecyclerView?.adapter = channelRecyclerAdapter

        accountsRecyclerView = view.findViewById(R.id.recyclerAccounts)
        accountsRecyclerView?.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        accountsRecyclerView?.adapter = accountRecyclerAdapter

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainStatisticFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun updateAccounts(account: Account) {
        val existAccount = accountList?.find { it.id == account.id }
        if (existAccount != null)
            accountList?.set(accountList?.indexOf(existAccount)!!, account)
        else
            accountList!!.add(account)
        accountRecyclerAdapter!!.notifyDataSetChanged()
        if (account.accountType == SocialNetworks.VK_TYPE)
            channelRecyclerAdapter!!.setAccount(
                (account as VkAccount).id,
                account.accessToken,
                account.accountType
            )
    }

    override fun updateChannels(channel: Channel) {
        channelsList!!.add(channel)
        channelRecyclerAdapter!!.notifyDataSetChanged()
    }
}
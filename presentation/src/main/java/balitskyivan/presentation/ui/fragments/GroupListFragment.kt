package balitskyivan.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import balitskyivan.presentation.R
import balitskyivan.presentation.adapters.RecyclerCreateGroupListAdapter
import balitskyivan.presentation.di.Injector
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.domain.interactors.ChannelInteractor
import balitskyivan.presentation.interfaces.GroupListView
import balitskyivan.presentation.presenter.GroupListPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class GroupListFragment : MvpAppCompatFragment(), GroupListView {

    private var recyclerGroup: RecyclerView? = null
    private var recyclerGroupAdapter: RecyclerCreateGroupListAdapter? = null

    @Inject
    lateinit var channelInteractor: ChannelInteractor

    private val presenter by moxyPresenter { GroupListPresenter(channelInteractor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.addGroupListFragment.inject(this)

        super.onCreate(savedInstanceState)
        arguments?.let {
            presenter.sendGroupListRequest(
                constructAccount(
                    it.getLong(ACCOUNT_ID_KEY), it.getString(
                        ACCOUNT_ACCESS_TOKEN_KEY
                    )!!, it.getString(ACCOUNT_TYPE)!!
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerGroupAdapter = RecyclerCreateGroupListAdapter(presenter.groupList!!)
        recyclerGroup = view.findViewById(R.id.recyclerAddGroups)
        recyclerGroup?.layoutManager = LinearLayoutManager(view.context)
        recyclerGroup?.adapter = recyclerGroupAdapter
    }

    companion object {

        private const val ACCOUNT_ID_KEY = "account_id"
        private const val ACCOUNT_ACCESS_TOKEN_KEY = "access_token"
        private const val ACCOUNT_TYPE = "account_type"

        private const val VK_TYPE_IN_STRING = "vk_type"
        private const val INSTAGRAM_TYPE_IN_STRING = "instagram_type"

        @JvmStatic
        fun newInstance(accountId: Long, accessToken: String, type: SocialNetworks) =
            GroupListFragment().apply {
                arguments = Bundle().apply {
                    putLong(ACCOUNT_ID_KEY, accountId)
                    putString(ACCOUNT_ACCESS_TOKEN_KEY, accessToken)
                    when (type) {
                        SocialNetworks.INSTAGRAM_TYPE -> putString(
                            ACCOUNT_TYPE,
                            INSTAGRAM_TYPE_IN_STRING
                        )
                        SocialNetworks.VK_TYPE -> putString(ACCOUNT_TYPE, VK_TYPE_IN_STRING)
                    }
                }
            }
    }

    override fun groupListUpdated() {
        recyclerGroupAdapter!!.notifyDataSetChanged()
    }

    private fun constructAccount(id: Long, accessToken: String, type: String): Account {
        var socialType: SocialNetworks = SocialNetworks.VK_TYPE
        when (type) {
            VK_TYPE_IN_STRING -> socialType = SocialNetworks.VK_TYPE
            INSTAGRAM_TYPE_IN_STRING -> socialType = SocialNetworks.INSTAGRAM_TYPE
        }
        return object : Account {
            override var id: Long = id
            override var error: ErrorTypes = ErrorTypes.NONE
            override var avatarUrl: String? = null
            override var name: String? = null
            override var surname: String? = null
            override var accountType: SocialNetworks = socialType
            override var subscribersCount: Int? = null
            override var accessToken: String = accessToken
        }
    }
}
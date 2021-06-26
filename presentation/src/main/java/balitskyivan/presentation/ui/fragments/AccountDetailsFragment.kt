package balitskyivan.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import balitskyivan.presentation.R
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.domain.entities.InstagramAccount
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.interfaces.AccountDetailsView
import balitskyivan.presentation.presenter.AccountDetailsPresenter
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class AccountDetailsFragment : MvpAppCompatFragment(), AccountDetailsView {

    @InjectPresenter
    lateinit var presenter: AccountDetailsPresenter
    private var account: Account? = null
    private var friendsTextView: TextView? = null
    private var subscribersTextView: TextView? = null
    private var friendsOnlineTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            when (it.getString(ACCOUNT_TYPE_KEY)) {
                VK_TYPE_STRING -> account = VkAccount(
                    it.getLong(ACCOUNT_ID_KEY), ErrorTypes.NONE, it.getString(
                        AVATAR_URL_KEY
                    ), it.getString(NAME_KEY), it.getString(SURNAME_KEY), SocialNetworks.VK_TYPE, 0,
                    it.getString(ACCESS_TOKEN_KEY)!!
                )
                INSTAGRAM_TYPE_STRING -> account = InstagramAccount(
                    it.getLong(ACCOUNT_ID_KEY),
                    ErrorTypes.NONE,
                    it.getString(
                        AVATAR_URL_KEY
                    ),
                    it.getString(NAME_KEY),
                    it.getString(SURNAME_KEY),
                    SocialNetworks.INSTAGRAM_TYPE,
                    0,
                    it.getString(ACCESS_TOKEN_KEY)!!
                )
            }
        }
        if (account != null)
            presenter.requestAccountDetails(account!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val avatarImage = view.findViewById<ImageView>(R.id.avatar_image)
        friendsTextView = view.findViewById(R.id.friends_count_text)
        subscribersTextView = view.findViewById(R.id.subscribers_count_text)
        friendsOnlineTextView = view.findViewById(R.id.online_friends_count_text)
        val nameText = view.findViewById<TextView>(R.id.name_field)
        nameText.text = account?.name
        if (account?.surname != null)
            nameText.text = nameText.text.toString() + " " + account?.surname
        Picasso.get().load(account?.avatarUrl)
            .into(avatarImage!!);
    }

    companion object {
        private const val VK_TYPE_STRING = "vk_type"
        private const val INSTAGRAM_TYPE_STRING = "inst_type"

        private const val ACCOUNT_TYPE_KEY = "type"
        private const val NAME_KEY = "name"
        private const val SURNAME_KEY = "surname"
        private const val ACCOUNT_ID_KEY = "id"
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val AVATAR_URL_KEY = "url"

        @JvmStatic
        fun newInstance(account: Account) =
            AccountDetailsFragment().apply {
                arguments = Bundle().apply {
                    when (account.accountType) {
                        SocialNetworks.VK_TYPE -> putString(ACCOUNT_TYPE_KEY, VK_TYPE_STRING)
                        SocialNetworks.INSTAGRAM_TYPE -> putString(
                            ACCOUNT_TYPE_KEY,
                            INSTAGRAM_TYPE_STRING
                        )
                    }
                    putString(NAME_KEY, account.name)
                    putString(SURNAME_KEY, account.surname)
                    putLong(ACCOUNT_ID_KEY, account.id)
                    putString(ACCESS_TOKEN_KEY, account.accessToken)
                    putString(AVATAR_URL_KEY, account.avatarUrl)
                }
            }
    }

    override fun initStatistic(followersCount: Int, followsCount: Int, onlineCount: Int) {
        friendsTextView!!.text = followersCount.toString()
        subscribersTextView!!.text = followsCount.toString()
        friendsOnlineTextView?.text = onlineCount.toString()
    }

}
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
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.domain.entities.VkChannel
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.interfaces.ChannelDetailsView
import balitskyivan.presentation.presenter.ChannelDetailsPresenter
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class ChannelDetailsFragment : MvpAppCompatFragment(), ChannelDetailsView {

    @InjectPresenter
    lateinit var presenter: ChannelDetailsPresenter
    private var group: Channel? = null
    private var commentsTextView: TextView? = null
    private var likesTextView: TextView? = null
    private var subscribedTextView: TextView? = null
    private var unsubscribedTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            group = VkChannel(
                it.getInt(GROUP_ID_KEY),
                ErrorTypes.NONE,
                it.getString(AVATAR_IMAGE_KEY),
                it.getString(
                    GROUP_NAME_KEY
                ),
                SocialNetworks.VK_TYPE,
                0
            )
            presenter.requestChannelDetails(
                VkAccount(
                    0,
                    ErrorTypes.NONE,
                    "",
                    "",
                    "",
                    SocialNetworks.VK_TYPE,
                    0,
                    it.getString(ACCESS_TOKEN_KEY)!!
                ), group as Channel, R.integer.com_vk_sdk_AppId
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_channel_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val avatarImage = view.findViewById<ImageView>(R.id.group_avatar)
        view.findViewById<TextView>(R.id.group_name).text = group?.name
        commentsTextView = view.findViewById(R.id.comments_count_text)
        likesTextView = view.findViewById(R.id.likes_count_text)
        subscribedTextView = view.findViewById(R.id.subscribed_count_text)
        unsubscribedTextView = view.findViewById(R.id.unsubscribed_count_text)
        Picasso.get().load(group?.logoUrl)
            .into(avatarImage!!);
    }

    companion object {
        private const val AVATAR_IMAGE_KEY = "url"
        private const val GROUP_NAME_KEY = "name"
        private const val GROUP_ID_KEY = "id"
        private const val ACCESS_TOKEN_KEY = "token"

        @JvmStatic
        fun newInstance(account: Account, channel: Channel) =
            ChannelDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(AVATAR_IMAGE_KEY, channel.logoUrl)
                    putString(GROUP_NAME_KEY, channel.name)
                    putInt(GROUP_ID_KEY, channel.id)
                    putString(ACCESS_TOKEN_KEY, account.accessToken)
                }
            }
    }

    override fun initStatistic(comments: Int, likes: Int, subscribed: Int, unsubscribed: Int) {
        commentsTextView?.text = comments.toString()
        likesTextView?.text = likes.toString()
        subscribedTextView?.text = subscribed.toString()
        unsubscribedTextView?.text = unsubscribed.toString()
    }
}
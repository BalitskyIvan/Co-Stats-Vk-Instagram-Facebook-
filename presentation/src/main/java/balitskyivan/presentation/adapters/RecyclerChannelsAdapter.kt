package balitskyivan.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import balitskyivan.presentation.R
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.interfaces.ChannelsClickedHandler
import com.squareup.picasso.Picasso

class RecyclerChannelsAdapter(private val channels: List<Channel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        var accountId: Long? = null
        var accessToken: String? = null
        var accountType: SocialNetworks? = null
    }

    private var onClickListener: ChannelsClickedHandler? = null

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        if (position == channels.size)
            return RecyclerAccountsAdapter.LAST_VIEW
        return RecyclerAccountsAdapter.DEFAULT_VIEW
    }

    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nameTextView: TextView? = null
        private var subscribersTextView: TextView? = null
        private var progressBar: ProgressBar? = null
        private var avatarImage: ImageView? = null
        private var subscribersIcon: ImageView? = null

        init {
            nameTextView = view.findViewById(R.id.channelNameTextView)
            progressBar = view.findViewById(R.id.channelProgressBar)
            avatarImage = view.findViewById(R.id.channel_avatar)
            subscribersTextView = view.findViewById(R.id.subscribersChannelTextView)
            subscribersIcon = view.findViewById(R.id.subscribers_channel_icon)
        }

        fun bind(
            position: Int,
            channels: List<Channel>,
            channelClickedHandler: ChannelsClickedHandler
        ) {
            itemView.setOnClickListener {
                channelClickedHandler.onChannelClicked(
                    VkAccount(
                        accountId!!,
                        ErrorTypes.NONE,
                        "",
                        "",
                        "",
                        SocialNetworks.VK_TYPE,
                        0,
                        accessToken!!
                    ), channels[position]
                )
            }
            progressBar!!.visibility = View.INVISIBLE
            nameTextView!!.visibility = View.VISIBLE
            subscribersIcon!!.visibility = View.VISIBLE
            subscribersTextView!!.visibility = View.VISIBLE
            avatarImage!!.visibility = View.VISIBLE
            nameTextView!!.text = channels[position].name
            subscribersTextView!!.text = channels[position].subscribersCount.toString()
            Picasso.get().load(channels[position].logoUrl)
                .into(avatarImage!!);
        }
    }

    class LastChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            position: Int,
            channelClickedHandler: ChannelsClickedHandler
        ) {
            itemView.setOnClickListener {
                if (accountId != null && accessToken != null)
                    channelClickedHandler.onAddChannelClicked(
                        accountId!!,
                        accessToken!!,
                        accountType!!
                    )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        onClickListener = parent.context as ChannelsClickedHandler

        return when (viewType) {
            RecyclerAccountsAdapter.LAST_VIEW -> {
                val item =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_channel_placeholder, parent, false)
                LastChannelViewHolder(item)
            }
            else -> {
                val item =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_channels_item, parent, false)
                ChannelViewHolder(item)
            }
        }
    }

    fun setAccount(accId: Long?, token: String?, type: SocialNetworks) {
        accountId = accId
        accessToken = token
        accountType = type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == channels.size)
            (holder as LastChannelViewHolder).bind(
                position,
                onClickListener!!,
            )
        else
            (holder as ChannelViewHolder).bind(position, channels, onClickListener!!)
    }

    override fun getItemCount(): Int = channels.size + 1
}
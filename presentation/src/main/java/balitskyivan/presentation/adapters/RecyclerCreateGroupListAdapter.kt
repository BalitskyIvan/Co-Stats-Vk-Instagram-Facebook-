package balitskyivan.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import balitskyivan.presentation.R
import balitskyivan.presentation.domain.entities.Channel
import balitskyivan.presentation.interfaces.ChannelsClickedHandler
import com.squareup.picasso.Picasso

class RecyclerCreateGroupListAdapter(private val channels: List<Channel>) :
    RecyclerView.Adapter<RecyclerCreateGroupListAdapter.GroupListViewHolder>() {

    private var onClickListener: ChannelsClickedHandler? = null

    class GroupListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImage: ImageView? = null
        var groupName: TextView? = null

        init {
            avatarImage = view.findViewById(R.id.groupAddAvatar)
            groupName = view.findViewById(R.id.groupAddName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
        onClickListener = parent.context as ChannelsClickedHandler

        return GroupListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_add_group_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClickListener!!.addChannel(channels[position]) }

        holder.groupName!!.text = channels[position].name
        Picasso.get().load(channels[position].logoUrl)
            .into(holder.avatarImage!!);
    }

    override fun getItemCount(): Int = channels.size

}
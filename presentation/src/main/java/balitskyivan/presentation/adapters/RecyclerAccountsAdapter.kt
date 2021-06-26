package balitskyivan.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import balitskyivan.presentation.R
import balitskyivan.presentation.domain.entities.Account
import balitskyivan.presentation.interfaces.AccountClickedHandler
import com.squareup.picasso.Picasso

class RecyclerAccountsAdapter(private val accounts: List<Account>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val DEFAULT_VIEW = 0
        const val LAST_VIEW = 1
    }

    private var onClickListener: AccountClickedHandler? = null

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        if (position == accounts.size)
            return LAST_VIEW
        return DEFAULT_VIEW
    }

    class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nameTextView: TextView? = null
        private var subscribersTextView: TextView? = null
        private var progressBar: ProgressBar? = null
        private var avatarImage: ImageView? = null
        private var subscribersIcon: ImageView? = null

        init {
            nameTextView = view.findViewById(R.id.accountNameTextView)
            progressBar = view.findViewById(R.id.accountProgressBar)
            avatarImage = view.findViewById(R.id.account_avatar)
            subscribersTextView = view.findViewById(R.id.subscribersAccountTextView)
            subscribersIcon = view.findViewById(R.id.subscribers_icon)
        }

        fun bind(
            position: Int,
            accounts: List<Account>,
            accountClickedHandler: AccountClickedHandler
        ) {
            itemView.setOnClickListener { accountClickedHandler.onAccountClicked(accounts[position]) }
            progressBar!!.visibility = View.INVISIBLE
            nameTextView!!.visibility = View.VISIBLE
            subscribersTextView!!.visibility = View.VISIBLE
            subscribersIcon!!.visibility = View.VISIBLE
            avatarImage!!.visibility = View.VISIBLE
            nameTextView!!.text = accounts[position].name
            subscribersTextView!!.text = accounts[position].subscribersCount.toString()
            Picasso.get().load(accounts[position].avatarUrl)
                .into(avatarImage!!);
        }
    }

    class LastAccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int, accountClickedHandler: AccountClickedHandler) {
            itemView.setOnClickListener { accountClickedHandler.onAddAccountClicked() }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        onClickListener = parent.context as AccountClickedHandler

        return when (viewType) {
            LAST_VIEW -> {
                val item =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_account_placeholder_item, parent, false)
                LastAccountViewHolder(item)
            }
            else -> {
                val item =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_accounts_item, parent, false)
                AccountViewHolder(item)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == accounts.size)
            (holder as LastAccountViewHolder).bind(position, onClickListener!!)
        else
            (holder as AccountViewHolder).bind(position, accounts, onClickListener!!)
    }

    override fun getItemCount(): Int = accounts.size + 1

}

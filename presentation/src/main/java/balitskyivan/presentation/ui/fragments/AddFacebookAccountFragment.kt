package balitskyivan.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import balitskyivan.presentation.R
import balitskyivan.presentation.domain.interactors.ChannelInteractor
import balitskyivan.presentation.presenter.GroupListPresenter
import com.facebook.login.widget.LoginButton
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class AddFacebookAccountFragment : MvpAppCompatDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_facebook_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = view.findViewById(R.id.login_button) as LoginButton
        loginButton.fragment = parentFragment
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddFacebookAccountFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
package balitskyivan.presentation.di.component

import android.content.Context
import androidx.lifecycle.ViewModel
import balitskyivan.presentation.MainStatisticActivity
import balitskyivan.presentation.di.modules.AppModule
import balitskyivan.presentation.presenter.AccountDetailsPresenter
import balitskyivan.presentation.presenter.ChannelDetailsPresenter
import balitskyivan.presentation.presenter.MainStatisticFragmentPresenter
import balitskyivan.presentation.ui.fragments.AccountDetailsFragment
import balitskyivan.presentation.ui.fragments.AddAccountFragment
import balitskyivan.presentation.ui.fragments.GroupListFragment
import balitskyivan.presentation.ui.fragments.MainStatisticFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainStatisticActivity)
}

@Module
interface MainActivityModule {

}

@Subcomponent(modules = [AddAccountFragmentModule::class])
interface AddAccountFragmentComponent {
    fun inject(addItemFragment: AddAccountFragment)
}

@Module
interface AddAccountFragmentModule {

}

@Subcomponent(modules = [AddGroupListModule::class])
interface AddGroupListFragmentComponent {
    fun inject(addItemFragment: GroupListFragment)
}

@Module
interface AddGroupListModule {

}

@Subcomponent(modules = [MainStatisticFragmentModule::class])
interface MainStatisticFragmentComponent {
    fun inject(addItemFragment: MainStatisticFragment)
}

@Module
interface MainStatisticFragmentModule {

}

@Subcomponent(modules = [MainStatisticFragmentPresenterModule::class])
interface MainStatisticFragmentPresenterComponent {
    fun inject(addItemFragment: MainStatisticFragmentPresenter)
}

@Module
interface MainStatisticFragmentPresenterModule {

}

@Subcomponent(modules = [AccountDetailsPresenterModule::class])
interface AccountDetailsFragmentPresenterComponent {
    fun inject(accountDetailsPresenter: AccountDetailsPresenter)
}

@Module
interface AccountDetailsPresenterModule {

}

@Subcomponent(modules = [ChannelDetailsPresenterModule::class])
interface ChannelDetailsFragmentPresenterComponent {
    fun inject(channelDetailsPresenter: ChannelDetailsPresenter)
}

@Module
interface ChannelDetailsPresenterModule {

}


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    val context: Context
    val addAccountFragment: AddAccountFragmentComponent
    val addChannelFragment: AddGroupListFragmentComponent
    val mainStatisticFragmentComponent: MainStatisticFragmentComponent
    val mainStatisticFragmentPresenterComponent: MainStatisticFragmentPresenterComponent
    val accountDetailsFragmentPresenterComponent: AccountDetailsFragmentPresenterComponent
    val channelDetailsPresenterComponent: ChannelDetailsFragmentPresenterComponent
}
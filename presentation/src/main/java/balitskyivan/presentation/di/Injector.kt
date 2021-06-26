package balitskyivan.presentation.di

import android.content.Context
import balitskyivan.presentation.di.component.*
import balitskyivan.presentation.di.modules.AppModule
import balitskyivan.presentation.presenter.MainStatisticFragmentPresenter

object Injector {

    private lateinit var appComponent: AppComponent

    val addAccountFragment: AddAccountFragmentComponent
        get() {
            return appComponent.addAccountFragment
        }

    val addGroupListFragment: AddGroupListFragmentComponent
        get() {
            return appComponent.addChannelFragment
        }

    val mainStatisticFragment: MainStatisticFragmentComponent
        get() {
            return appComponent.mainStatisticFragmentComponent
        }

    val mainStatisticFragmentPresenter: MainStatisticFragmentPresenterComponent
        get() {
            return appComponent.mainStatisticFragmentPresenterComponent
        }

    val accountDetailsFragmentPresenterComponent: AccountDetailsFragmentPresenterComponent
        get() {
            return appComponent.accountDetailsFragmentPresenterComponent
        }

    val channelDetailsFragmentPresenterComponent: ChannelDetailsFragmentPresenterComponent
        get() {
            return appComponent.channelDetailsPresenterComponent
        }

    internal fun initAppComponent(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}
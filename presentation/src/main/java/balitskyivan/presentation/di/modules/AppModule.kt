package balitskyivan.presentation.di.modules

import android.content.Context
import androidx.room.Room
import balitskyivan.presentation.data.database.DatabaseStorage
import balitskyivan.presentation.data.repository.Repository
import balitskyivan.presentation.domain.interactors.AccountInteractor
import balitskyivan.presentation.domain.interactors.ChannelInteractor
import balitskyivan.presentation.domain.interactors.IAccountInteractor
import balitskyivan.presentation.domain.interactors.IChannelInteractor
import balitskyivan.presentation.domain.repository.ICommonRepository
import balitskyivan.presentation.presenter.MainStatisticFragmentPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module(includes = [AppModule.InnerAppModule::class])
class AppModule(private val context: Context) {


    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideDatabaseStorage() = Room.databaseBuilder(
        context,
        DatabaseStorage::class.java,
        DatabaseStorage.VK_ACCOUNT_DATA_BASE_STORAGE
    ).build()

    @Provides
    fun provideMainStatisticFragmentPresenter() = MainStatisticFragmentPresenter()

    @Module
    interface InnerAppModule {

        @Binds
        @Singleton
        fun provideCommonRepository(repository: Repository): ICommonRepository

        @Singleton
        @Binds
        fun provideAccountInteractor(accountInteractor: AccountInteractor): IAccountInteractor

        @Singleton
        @Binds
        fun provideChannelInteractor(channelInteractor: ChannelInteractor): IChannelInteractor

    }

}
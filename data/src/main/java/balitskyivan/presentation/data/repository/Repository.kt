package balitskyivan.presentation.data.repository

import balitskyivan.presentation.data.database.DatabaseStorage
import balitskyivan.presentation.data.exceptions.ChannelNotFoundException
import balitskyivan.presentation.domain.entities.*
import balitskyivan.presentation.domain.enums.SocialNetworks
import balitskyivan.presentation.domain.repository.ICommonRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class Repository @Inject constructor(
    private val databaseStorage: DatabaseStorage,
    private val vkRepository: VkRepository,
    private val instRepository: InstRepository
) : ICommonRepository {

    override fun getAllAccounts(): List<Observable<Account>> {
        val result: MutableList<Observable<Account>> = mutableListOf()

        databaseStorage.vkAccountDao.getAllAccounts().forEach {
            result.add(fillAccount(it))
        }
        databaseStorage.instagramAccountDao.getAllAccounts().forEach {
            result.add(fillAccount(it))
        }
        return result
    }

    override fun getAllChannels(): List<Observable<Channel>> {
        val result: MutableList<Observable<Channel>> = mutableListOf()

        databaseStorage.vkAccountDao.getAllAccounts().forEach {
            val vkAccount = it
            databaseStorage.vkChannelDao.getAllChannels().forEach {
                result.add(fillChannel(vkAccount, it))
            }
        }
        return result
    }

    override fun updateAccount(account: Account) {
        when (account.accountType) {
            SocialNetworks.VK_TYPE -> databaseStorage.vkAccountDao.updateAccount(account as VkAccount)
            SocialNetworks.INSTAGRAM_TYPE -> databaseStorage.instagramAccountDao.updateAccount(
                account as InstagramAccount
            )
        }
    }

    override fun updateChannel(channel: Channel) {
        when (channel.type) {
            SocialNetworks.VK_TYPE -> databaseStorage.vkChannelDao.updateChannel(channel as VkChannel)
        }
    }

    override fun addAccount(account: Account) {
        when (account.accountType) {
            SocialNetworks.VK_TYPE -> databaseStorage.vkAccountDao.addAccount(account as VkAccount)
            SocialNetworks.INSTAGRAM_TYPE -> databaseStorage.instagramAccountDao.addAccount(account as InstagramAccount)
        }
    }

    override fun addChannel(channel: Channel) {
        when (channel.type) {
            SocialNetworks.VK_TYPE -> databaseStorage.vkChannelDao.addChannel(channel as VkChannel)
        }
    }

    override fun fillAccount(account: Account): Observable<Account> = when (account.accountType) {
        SocialNetworks.VK_TYPE -> vkRepository.fillAccount(account)
        SocialNetworks.INSTAGRAM_TYPE -> instRepository.fillAccount(account)
    }

    override fun fillChannel(account: Account, channel: Channel): Observable<Channel> =
        when (account.accountType) {
            SocialNetworks.VK_TYPE -> vkRepository.fillChannel(account, channel)
            else -> throw ChannelNotFoundException()
        }


    override fun getChannelsList(account: Account): Observable<List<Channel>> =
        when (account.accountType) {
            SocialNetworks.VK_TYPE -> vkRepository.getChannelsList(account)
            else -> throw ChannelNotFoundException()
        }

    override fun getAccountDetails(account: Account): Observable<AccountDetails> =
        when (account.accountType) {
            SocialNetworks.VK_TYPE -> vkRepository.getAccountDetails(account)
            SocialNetworks.INSTAGRAM_TYPE -> instRepository.getAccountDetails(account)
        }

    override fun getChannelDetails(
        channel: Channel,
        account: Account,
        appId: Int
    ): Observable<ChannelDetails> =
        when (account.accountType) {
            SocialNetworks.VK_TYPE -> vkRepository.getChannelDetails(channel, account, appId)
            else -> throw ChannelNotFoundException()
        }


    override fun deleteAccount(account: Account) {
        //Not implemented
    }

    override fun deleteChannel(channel: Channel) {
        //Not implemented
    }

}

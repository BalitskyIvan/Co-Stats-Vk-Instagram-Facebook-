package balitskyivan.presentation.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import balitskyivan.presentation.data.database.dao.*
import balitskyivan.presentation.domain.entities.*

@Database(entities = [VkAccount::class, InstagramAccount::class, VkChannel::class, VkAccountDetails::class, InstagramAccountDetails::class, VkChannelDetails::class], version = 1)
abstract class DatabaseStorage : RoomDatabase() {

    abstract val vkAccountDao: VkAccountDao
    abstract val vkChannelDao: VkChannelDao
    abstract val instagramAccountDao: InstagramAccountDao
    abstract val vkAccountDetails: VkAccountDetailsDao
    abstract val instagramAccountDetails : InstagramAccountDetailsDao
    abstract val vkChannelDetails : VkChannelDetailsDao

    companion object {
        const val VK_ACCOUNT_DATA_BASE_STORAGE = "VK_ACCOUNT_DATA_BASE_STORAGE"
        const val INSTAGRAM_ACCOUNT_DATA_BASE_STORAGE = "INSTAGRAM_ACCOUNT_DATA_BASE_STORAGE"
        const val VK_CHANNEL_DATA_BASE_STORAGE = "VK_CHANNEL_DATA_BASE_STORAGE"
    }

}
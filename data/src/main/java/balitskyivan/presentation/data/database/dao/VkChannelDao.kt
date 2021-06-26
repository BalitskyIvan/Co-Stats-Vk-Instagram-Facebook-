package balitskyivan.presentation.data.database.dao

import androidx.room.*
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.domain.entities.VkChannel

@Dao
interface VkChannelDao {

    @Insert
    fun addChannel(channel : VkChannel)

    @Update
    fun updateChannel(channel : VkChannel)

    @Query("SELECT * FROM vkchannel WHERE id =:id")
    fun findChannelById(id: Int): VkChannel

    @Query("SELECT * FROM vkchannel")
    fun getAllChannels(): List<VkChannel>

    @Delete()
    fun deleteChannel(channel: VkChannel)
}
package balitskyivan.presentation.data.database.dao

import androidx.room.*
import balitskyivan.presentation.domain.entities.VkAccount

@Dao
interface VkAccountDao {

    @Insert
    fun addAccount(account : VkAccount)

    @Update
    fun updateAccount(account: VkAccount)

    @Query("SELECT * FROM vkaccount WHERE id =:id")
    fun findAccountById(id: Long): VkAccount

    @Query("SELECT * FROM vkaccount")
    fun getAllAccounts(): List<VkAccount>

    @Delete
    fun deleteAccount(account: VkAccount)
}
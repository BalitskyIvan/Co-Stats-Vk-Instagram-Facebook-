package balitskyivan.presentation.data.database.dao

import androidx.room.*
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.domain.entities.VkAccountDetails

@Dao
interface VkAccountDetailsDao {
    @Insert
    fun addDetails(accountDetails: VkAccountDetails)

    @Query("SELECT * FROM vkaccountdetails WHERE id =:id")
    fun findAccountById(id: Long): VkAccountDetails

    @Update
    fun updateDetails(accountDetails: VkAccountDetails)

    @Delete
    fun deleteDetails(accountDetails: VkAccountDetails)
}
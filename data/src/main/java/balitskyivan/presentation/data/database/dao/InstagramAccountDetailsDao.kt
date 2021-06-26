package balitskyivan.presentation.data.database.dao

import androidx.room.*
import balitskyivan.presentation.domain.entities.InstagramAccountDetails

@Dao
interface InstagramAccountDetailsDao {

    @Insert
    fun addDetails(accountDetails: InstagramAccountDetails)

    @Query("SELECT * FROM instagramaccountdetails WHERE id =:id")
    fun findAccountById(id: Long): InstagramAccountDetails

    @Update
    fun updateDetails(accountDetails: InstagramAccountDetails)

    @Delete
    fun deleteDetails(accountDetails: InstagramAccountDetails)
}
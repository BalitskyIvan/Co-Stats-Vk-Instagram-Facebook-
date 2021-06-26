package balitskyivan.presentation.data.database.dao

import androidx.room.*
import balitskyivan.presentation.domain.entities.InstagramAccount

@Dao
interface InstagramAccountDao {

    @Insert
    fun addAccount(account : InstagramAccount)

    @Update
    fun updateAccount(account: InstagramAccount)

    @Query("SELECT * FROM instagramaccount WHERE id =:id")
    fun findAccountById(id: Long): InstagramAccount

    @Query("SELECT * FROM instagramaccount")
    fun getAllAccounts(): List<InstagramAccount>

    @Delete
    fun deleteAccount(account: InstagramAccount)
}
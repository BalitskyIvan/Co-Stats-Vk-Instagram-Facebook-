package balitskyivan.presentation.data.network.vkApi

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface VkRequestService {

    @GET("account.getProfileInfo")
    fun getProfileInfo(
        @Query("access_token") accessToken: String,
        @Query("v") version: String
    ): Observable<String>

    @GET("friends.get")
    fun getFriendsInfo(
        @Query("user_id") uid: String,
        @Query("access_token") accessToken: String,
        @Query("v") version: String
    ): Observable<String>

    @GET("users.getFollowers\n")
    fun getFollowersInfo(
        @Query("user_id") uid: String,
        @Query("access_token") accessToken: String,
        @Query("v") version: String
    ): Observable<String>

    @GET("users.get")
    fun getUserAvatar(
        @Query("user_ids") uid: String,
        @Query("access_token") accessToken: String,
        @Query("fields") avatar: String,
        @Query("v") version: String
    ): Observable<String>

    @GET("friends.getOnline")
    fun getOnlineFriends(
        @Query("user_id") uid: String,
        @Query("access_token") accessToken: String,
        @Query("v") version: String
    ): Observable<String>

    @GET("groups.get")
    fun getGroupList(
        @Query("user_id") uid: String,
        @Query("extended") showFullInfo: Int,
        @Query("filter") filter: String,
        @Query("fields") fields: String,
        @Query("access_token") accessToken: String,
        @Query("v") version: String
    ): Observable<String>

    @GET("stats.get")
    fun getGroupDetails(
        @Query("group_id") id: Int,
        @Query("app_id") appId: Int,
        @Query("interval") interval: String,
        @Query("access_token") accessToken: String,
        @Query("v") version: String
    ): Observable<String>
}
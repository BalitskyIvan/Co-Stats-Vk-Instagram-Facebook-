package balitskyivan.presentation.data.network.vkApi

import android.util.Log
import balitskyivan.presentation.data.R
import balitskyivan.presentation.data.exceptions.AccessTokenExpiredException
import balitskyivan.presentation.data.exceptions.AccountNotFoundException
import balitskyivan.presentation.data.network.Common
import balitskyivan.presentation.domain.entities.VkAccount
import balitskyivan.presentation.domain.entities.VkAccountDetails
import balitskyivan.presentation.domain.entities.VkChannel
import balitskyivan.presentation.domain.entities.VkChannelDetails
import balitskyivan.presentation.domain.enums.ErrorTypes
import balitskyivan.presentation.domain.enums.SocialNetworks
import io.reactivex.rxjava3.core.Observable
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class VkApi {
    lateinit var mService: VkRequestService

    val version: String = "5.62"
    val avatarField = "photo_50"

    fun fillVkAccount(account: VkAccount): Observable<VkAccount> {
        mService = Common.vkRetrofitService

        checkConnection(account.accessToken)

        val profileInfoObserver = mService.getProfileInfo(account.accessToken, version)
        val friendsInfoObserver =
            mService.getFriendsInfo(
                account.id.toString(),
                account.accessToken,
                version
            )
        val followersObserver =
            mService.getFollowersInfo(
                account.id.toString(),
                account.accessToken,
                version
            )
        val userAvatarObserver =
            mService.getUserAvatar(
                account.id.toString(),
                account.accessToken,
                avatarField,
                version,
            )
        return Observable.zip(
            profileInfoObserver,
            friendsInfoObserver,
            followersObserver,
            userAvatarObserver,
            { c, d, t, u -> convert(c, d, t, u, account) })
    }

    fun getGroupList(userId : Long, accessToken : String) : Observable<List<VkChannel>> {
        mService = Common.vkRetrofitService
        val groupRequest = mService.getGroupList(userId.toString(), 1, "admin", "members_count", accessToken, version)
        return groupRequest.map { convertToGroup(it) }
    }

    fun fillGroup(account: VkAccount, channel: VkChannel) : Observable<VkChannel> {
        return Observable.just(channel)
    }

    fun getAccountDetails(account: VkAccount) : Observable<VkAccountDetails> {
        mService = Common.vkRetrofitService
        checkConnection(account.accessToken)

        val friendsCountObserver = mService.getFriendsInfo(account.id.toString(), account.accessToken, version)
        val subscribersCountObserver = mService.getFollowersInfo(account.id.toString(), account.accessToken, version)
        val onlineFriendsObserver = mService.getOnlineFriends(account.id.toString(), account.accessToken, version)
        return Observable.zip(
            friendsCountObserver,
            subscribersCountObserver,
            onlineFriendsObserver,
            {f, s, o -> convertToAccountDetails(f, s, o, account)})
    }

    fun getGroupDetails(account: VkAccount, channel: VkChannel, appId : Int) : Observable<VkChannelDetails> {
        val request = mService.getGroupDetails(channel.id, appId, "all", account.accessToken, "5.131")
        return request.map { convertToGroupDetails(it) }
    }

    private fun convertToGroupDetails(response: String) : VkChannelDetails {
        val stat = JSONObject(response).getJSONArray("response").getJSONObject(0).getJSONObject("activity")
        return VkChannelDetails(0, ErrorTypes.NONE, stat.getInt("comments"), stat.getInt("likes"), stat.getInt("subscribed"), stat.getInt("unsubscribed"))
    }



    private fun convertToGroup(response : String) : List<VkChannel> {
        val groupList : MutableList<VkChannel> = mutableListOf()

        val itemsArray = JSONObject(response).getJSONObject("response").getJSONArray("items")
        val itemsCount = JSONObject(response).getJSONObject("response").getInt("count")

        for (i in 0 until itemsCount) {
            val groupJson = itemsArray.getJSONObject(i)
            groupList.add(
                VkChannel(
                    groupJson.getInt("id"),
                    ErrorTypes.NONE,
                    groupJson.getString("photo_200"),
                    groupJson.getString("name"),
                    SocialNetworks.VK_TYPE,
                    groupJson.getInt("members_count")
                )
            )
        }
        return groupList
    }

    private fun convert(
        profileInfoObserver: String,
        friendsInfoObserver: String,
        followersObserver: String,
        userAvatarObserver: String,
        account: VkAccount
    ): VkAccount {

        val profileResponse = JSONObject(JSONObject(profileInfoObserver).get("response").toString())
        val friendResponse = JSONObject(JSONObject(friendsInfoObserver).get("response").toString())
        val followersResponse = JSONObject(JSONObject(followersObserver).get("response").toString())
        val userAvatarResponse = JSONObject(
            JSONArray(JSONObject(userAvatarObserver).get("response").toString()).get(0).toString()
        )

        account.name = profileResponse.getString("first_name")
        account.surname = profileResponse.getString("last_name")
        account.subscribersCount = friendResponse.getInt("count")
        account.avatarUrl = userAvatarResponse.getString("photo_50")
        return account
    }

    private fun convertToAccountDetails(
        friendsCountObserver : String,
        subscribersCountObserver: String,
        onlineFriendsObserver: String,
        account: VkAccount
    ) : VkAccountDetails {
        val friendsCount = JSONObject(friendsCountObserver).getJSONObject("response").getInt("count")
        val followersCount = JSONObject(subscribersCountObserver).getJSONObject("response").getInt("count")
        val friendsOnline = JSONObject(onlineFriendsObserver).getJSONArray("response").length()
        return VkAccountDetails(account.id, ErrorTypes.NONE, followersCount, friendsCount, friendsOnline)
    }

    private fun checkConnection(accessToken: String) {
        var response = ""
        try {
            mService.getProfileInfo(accessToken, version).doOnNext { response = it }
                .blockingSubscribe()
            val checkErrorObj = JSONObject(response).getJSONObject("error")
            if (checkErrorObj.getInt("error_code") == 5)
                throw AccessTokenExpiredException()
            else if (checkErrorObj.getInt("error_code") != 0)
                throw AccountNotFoundException()
        } catch (e : JSONException) {
            Log.i("DATA", "access token valid")
        }
    }

}
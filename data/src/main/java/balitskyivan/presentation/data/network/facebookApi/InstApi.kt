package balitskyivan.presentation.data.network.facebookApi

import balitskyivan.presentation.data.network.Common
import balitskyivan.presentation.domain.entities.InstagramAccount
import balitskyivan.presentation.domain.entities.InstagramAccountDetails
import balitskyivan.presentation.domain.entities.VkChannel
import balitskyivan.presentation.domain.enums.ErrorTypes
import io.reactivex.rxjava3.core.Observable
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class InstApi {
    lateinit var facebookService: InstRequestService

    fun fillInstAccount(account: InstagramAccount): Observable<InstagramAccount>? {
        facebookService = Common.facebookRetrofitService
        checkConnection(account.accessToken)
        val requestObserver = sendRequest(account)
        return requestObserver.map { convert(it, account) }
    }

    fun getAccountDetails(account: InstagramAccount): Observable<InstagramAccountDetails> {
        val requestViews = facebookService.getMetrics(
            account.id.toString(),
            "profile_views",
            "day",
            account.accessToken
        )
        val requestProfile = facebookService.getProfileInfo(
            account.id.toString(),
            "followers_count,follows_count",
            account.accessToken
        )
        return Observable.zip(requestViews, requestProfile,
            { r1, r2 -> convertToAccountDetails(r1, r2, account) })
    }

    private fun convert(response: String, account: InstagramAccount): InstagramAccount {
        val responseJson = JSONObject(response)
        account.avatarUrl = responseJson.getString("profile_picture_url")
        account.name = responseJson.getString("username")
        account.subscribersCount = responseJson.getInt("followers_count")
        account.id = responseJson.getString("id").toLong()
        return account
    }

    private fun sendRequest(account: InstagramAccount): Observable<String> {
        return facebookService.getAccount(account.accessToken).flatMap { it ->
            facebookService.getInstagramBusinessAccount(
                JSONObject(it).getJSONArray("data").getJSONObject(0).getString("id"),
                "instagram_business_account", account.accessToken
            ).flatMap {
                facebookService.getProfileInfo(
                    JSONObject(it).getJSONObject("instagram_business_account").getString("id"),
                    "username,profile_picture_url,followers_count,follows_count",
                    account.accessToken
                )
            }
        }
    }

    private fun convertToAccountDetails(
        viewsResponse: String,
        followersRsponse: String,
        account: InstagramAccount
    ): InstagramAccountDetails {
        try {
            val arrayLenth = JSONObject(viewsResponse).getJSONArray("data").getJSONObject(0)
                .getJSONArray("values").length()
            var profileViews = 0;
            for (i in 0 until arrayLenth)
                profileViews += JSONObject(viewsResponse).getJSONArray("data").getJSONObject(0)
                    .getJSONArray("values").getJSONObject(
                        i
                    ).getInt("value")

            val followersCount = JSONObject(followersRsponse).getInt("followers_count")
            val followsCount = JSONObject(followersRsponse).getInt("follows_count")
            return InstagramAccountDetails(
                account.id,
                ErrorTypes.NONE,
                followersCount,
                followsCount,
                profileViews
            )
        } catch (e: Exception) {
            println(e.message)
        }
        return InstagramAccountDetails(account.id, ErrorTypes.NONE, 0, 0, 0)
    }

    private fun checkConnection(accessToken: String) {
        facebookService.getAccount(accessToken).blockingSubscribe()
    }

}
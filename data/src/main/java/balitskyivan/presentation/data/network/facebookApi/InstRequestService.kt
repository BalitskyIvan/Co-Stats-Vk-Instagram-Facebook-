package balitskyivan.presentation.data.network.facebookApi

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InstRequestService {

    @GET("me/accounts")
    fun getAccount(@Query("access_token") accessToken: String): Observable<String>

    @GET("{page_id}")
    fun getInstagramBusinessAccount(
        @Path("page_id") pageId: String,
        @Query("fields") fields: String,
        @Query("access_token") accessToken: String
    ): Observable<String>

    @GET("{user_id}")
    fun getProfileInfo(
        @Path("user_id") pageId: String,
        @Query("fields") fields: String,
        @Query("access_token") accessToken: String
    ): Observable<String>

    @GET("{user_id}/insights")
    fun getMetrics(
        @Path("user_id") pageId: String,
        @Query("metric") metrics: String,
        @Query("period") period: String,
        @Query("access_token") accessToken: String
    ): Observable<String>
}
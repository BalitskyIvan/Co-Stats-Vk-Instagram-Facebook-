package balitskyivan.presentation.data.network

import balitskyivan.presentation.data.network.facebookApi.InstRequestService
import balitskyivan.presentation.data.network.vkApi.VkRequestService

object Common {

    private const val BASE_FACEBOOK_URL = "https://graph.facebook.com/"
    private const val BASE_VK_URL = "https://api.vk.com/method/"

    val vkRetrofitService: VkRequestService
        get() = RequestClient.getProfileInfo(BASE_VK_URL).create(VkRequestService::class.java)
    val facebookRetrofitService: InstRequestService
        get() = RequestClient.getProfileInfo(BASE_FACEBOOK_URL)
            .create(InstRequestService::class.java)
}
package com.test.user.gurushotstesttavor.features.photosList.network


import com.test.user.gurushotstesttavor.features.photosList.network.response.PhotosListResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PhotosListApi {
 @FormUrlEncoded
 @POST(PhotosListConstant.GET_PHOTOS)
 fun getPhotosList(@Field("member_id") memberId: String,@Field("get_likes") getLikes: Boolean,@Field("limit") limit: Int,@Field("start") start: Int) : Observable<PhotosListResponse>
}
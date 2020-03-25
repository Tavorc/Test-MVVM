package com.test.user.gurushotstesttavor.features.photosList.viewModel


import androidx.lifecycle.MutableLiveData
import com.test.user.gurushotstesttavor.features.photosList.network.PhotosListApiController
import com.test.user.gurushotstesttavor.features.photosList.network.response.PhotosListResponse
import com.test.user.tavorapplication.infrastructure.viewModel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class PhotoListViewModel : BaseViewModel() {

    private var mPhotosListApiController: PhotosListApiController = PhotosListApiController()

    fun getPhotosList(memberId: String, getLikes: Boolean, limit: Int,start: Int): MutableLiveData<PhotosListViewState> {

        val liveData = MutableLiveData<PhotosListViewState>()
        /** Notify to view that is loading state */

        liveData.postValue(PhotosListViewState.Loading(true))
        mPhotosListApiController.getPhotosList(memberId,getLikes,limit,start).subscribe(object: Observer<PhotosListResponse> {
            override fun onNext(response: PhotosListResponse) {
                liveData.postValue(PhotosListViewState.Success(response))
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onError(e: Throwable) {
                liveData.postValue(PhotosListViewState.Error(e))
            }

            override fun onComplete() {
            }
        })

        return liveData
    }
}
package com.test.user.gurushotstesttavor.features.photosList.viewModel

import com.test.user.gurushotstesttavor.features.photosList.network.response.PhotosListResponse

sealed class PhotosListViewState {

    class Error(var message: Throwable) : PhotosListViewState()

    class Success(var response: PhotosListResponse) : PhotosListViewState()

    class Loading(var isLoading: Boolean) : PhotosListViewState()
}
package com.test.user.gurushotstesttavor.features.photosList.view

import com.test.user.gurushotstesttavor.features.photosList.network.response.Item

interface OnClickOnePhoto {
    fun onPhotoSelected(numberScreen :  Int, itemPhoto: Item, position: Int)
}
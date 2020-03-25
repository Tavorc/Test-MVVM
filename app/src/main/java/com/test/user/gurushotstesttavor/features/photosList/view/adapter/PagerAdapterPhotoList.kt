package com.test.user.gurushotstesttavor.features.photosList.view.adapter



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.test.user.gurushotstesttavor.features.photosList.view.fragment.PhotoDetailsFragment
import com.test.user.gurushotstesttavor.features.photosList.view.fragment.PhotosListFragment

class PagerAdapterPhotoList(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0
            -> PhotosListFragment.newInstance()
            1
            -> PhotoDetailsFragment.newInstance()
            else -> null
        } as Fragment?
    }

    override fun getCount(): Int {
        return NUM_ITEMS
    }

    companion object {
        const val NUM_ITEMS = 2
    }
}
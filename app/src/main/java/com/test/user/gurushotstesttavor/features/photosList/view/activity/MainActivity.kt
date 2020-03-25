package com.test.user.gurushotstesttavor.features.photosList.view.activity

import android.os.Build
import android.os.Bundle

import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer
import com.test.user.gurushotstesttavor.R
import com.test.user.gurushotstesttavor.features.photosList.network.response.Item
import com.test.user.gurushotstesttavor.features.photosList.view.OnClickOnePhoto
import com.test.user.gurushotstesttavor.features.photosList.view.adapter.PagerAdapterPhotoList
import com.test.user.gurushotstesttavor.features.photosList.view.adapter.SwipeLockableViewPager
import com.test.user.gurushotstesttavor.features.photosList.view.fragment.PhotosListFragment


class MainActivity : FragmentActivity(),
    OnClickOnePhoto {

    private var mStatusLikeBeforeMoveScreen: Boolean? =false
    private var mPositionSelectedPhoto : Int = 0
    lateinit var mIdSelectedPhoto: String
    lateinit var mViewPager : SwipeLockableViewPager
    lateinit var mPagerAdapterPhotoList: PagerAdapterPhotoList
    lateinit var mDetailsFragmentListener: OnDetailsFragmentListener
    var mLikesList : HashMap<String?,Boolean> = HashMap()
    lateinit var mListFragmentListener : OnListFragmentListener

    companion object{
        const val FIFTY =50
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor()
        initView()
    }

    private fun setStatusBarColor() {
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =(ContextCompat.getColor(this, R.color.my_statusbar_color))
        }
    }

    private fun initView(){
        initViewPager()
    }

    private fun initViewPager(){
        mViewPager= findViewById(R.id.viewpager)
        mPagerAdapterPhotoList = PagerAdapterPhotoList(supportFragmentManager)
        mViewPager?.apply {
            adapter = mPagerAdapterPhotoList
            setPageTransformer(true, ZoomOutTranformer())
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is PhotosListFragment) {
            /**Register to the listener onClickPhoto from list */
            fragment.setOnClickPhoto(this)
        }
    }

    override fun onPhotoSelected(numberScreen: Int, itemPhoto: Item, position: Int) {
        moveToNextScreen(numberScreen)
        /** Save data of the selected photo*/
        mIdSelectedPhoto = itemPhoto.id.toString()
        mPositionSelectedPhoto = position
        mStatusLikeBeforeMoveScreen = mLikesList[mIdSelectedPhoto]
        mDetailsFragmentListener.onMoveToScreen(itemPhoto)
    }

    private fun moveToNextScreen(number : Int){
        mViewPager.currentItem = number
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            when (mViewPager.currentItem) {
                0 -> { }
                1 -> {
                        moveToNextScreen(0)
                        /** Notify to ListFragment that he need to update the counter of the likes by position in the list */
                        var isLiked = mLikesList[mIdSelectedPhoto]
                        if( (isLiked!! && !(mStatusLikeBeforeMoveScreen)!! ) || (!isLiked && (mStatusLikeBeforeMoveScreen)!!) ){
                            mListFragmentListener.onLikeClicked(mPositionSelectedPhoto,isLiked)
                        }
                     }
            }
            return true
        }
        else {
            return super.onKeyDown(keyCode, event)
        }
    }

    interface OnDetailsFragmentListener{
        fun onMoveToScreen(itemPhoto: Item)
    }

    interface OnListFragmentListener{
        fun onLikeClicked(position: Int, isLiked: Boolean?)
    }
}

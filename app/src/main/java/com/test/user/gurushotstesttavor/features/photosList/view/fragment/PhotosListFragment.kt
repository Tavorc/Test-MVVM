package com.test.user.gurushotstesttavor.features.photosList.view.fragment


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.test.user.gurushotstesttavor.R
import com.test.user.gurushotstesttavor.features.photosList.network.response.Item
import com.test.user.gurushotstesttavor.features.photosList.viewModel.PhotosListViewState
import com.test.user.gurushotstesttavor.features.photosList.view.GeneralConstant
import com.test.user.gurushotstesttavor.features.photosList.view.OnClickOnePhoto
import com.test.user.gurushotstesttavor.features.photosList.view.activity.MainActivity
import com.test.user.gurushotstesttavor.features.photosList.view.adapter.PhotosAdapter
import com.test.user.gurushotstesttavor.features.photosList.viewModel.PhotoListViewModel
import com.test.user.tavorapplication.infrastructure.view.BaseFragment


class PhotosListFragment : BaseFragment(), PhotosAdapter.PhotosListener,
    MainActivity.OnListFragmentListener {

    private var mIsPaging: Boolean=false
    private lateinit var mListener : OnClickOnePhoto
    var mRecyclerView: RecyclerView? = null
    var mAdapter: PhotosAdapter? = null
    var listPhotoItems: ArrayList<Item> = ArrayList()

    lateinit var mPhotoListViewModel : PhotoListViewModel
    private var mProgressBar: ProgressBar? = null

    fun setOnClickPhoto(listener: OnClickOnePhoto) {
        this.mListener = listener
    }
    companion object{
        const val NUMBER_OF_COLUMNS = 2
        fun newInstance(): PhotosListFragment {
            val args = Bundle()
            val fragment =
                PhotosListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_photos_list
    }

    override fun initView(view: ViewGroup) {
        mRecyclerView = view.findViewById(R.id.recycleViewPhoto)
        mProgressBar = view.findViewById(R.id.progressBar)

        /** Register to listFragment listener */
        (activity as MainActivity).mListFragmentListener = this

        initViewModel()
        getPhotoList(0)
    }

    private fun initViewModel(){
        mPhotoListViewModel = PhotoListViewModel()
    }

    /** Execute the server call to get list of photos */
    private fun getPhotoList(start : Int){
        mPhotoListViewModel.getPhotosList(GeneralConstant.MEMBER_ID,true,
            MainActivity.FIFTY,start).observe(this, Observer<PhotosListViewState> {
            if (it is PhotosListViewState.Error) {
                hideProgressBar()
                Toast.makeText(activity, it.message.message , Toast.LENGTH_SHORT).show()
            }
            if (it is PhotosListViewState.Success) {
                hideProgressBar()
                /** Insert data to hash map */
                /** Insert data to hash map */
                it.response.items?.let { addToHashMap(it) }
                if(!mIsPaging){
                    listPhotoItems = it.response.items!!
                    initRecycleView()
                }
                else{
                    val start = mAdapter!!.itemCount
                    it.response.items?.let { mAdapter?.addItems(it) }
                    mAdapter!!.notifyItemRangeInserted(start, it.response.items?.size!!)
                }

            }
            if(it is PhotosListViewState.Loading){
                showProgressBar()
            }
        })
    }

    /**
     *  Function that Init the recycleview with gridlayout
     */
    private fun initRecycleView(){
        mRecyclerView?.apply {
            layoutManager = GridLayoutManager(activity,
                NUMBER_OF_COLUMNS
            )
            mAdapter = PhotosAdapter(listPhotoItems,activity)
            adapter = mAdapter
        }
        mAdapter?.setListener(this)
    }

    private fun addToHashMap(items: ArrayList<Item>) {
            for(item in items){
                ( (activity) as MainActivity?)?.mLikesList?.set(item.id, false)
            }
    }

    private fun showProgressBar() {
        if (mProgressBar != null) mProgressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        if (mProgressBar != null) mProgressBar?.visibility = View.GONE
    }

    override fun onBottomReached(position: Int) {
        mIsPaging=true
        getPhotoList(position+1)
    }

    override fun onClickItem(position: Int, itemPhoto: Item) {
        mListener.onPhotoSelected(1,itemPhoto,position)
    }

    override fun onLikeClicked(position: Int, isLiked: Boolean?) {
        mAdapter?.updateItem(position,isLiked)
    }
}
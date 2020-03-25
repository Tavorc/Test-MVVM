package com.test.user.gurushotstesttavor.features.photosList.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.user.gurushotstesttavor.R
import com.test.user.gurushotstesttavor.features.photosList.network.response.Item
import com.test.user.gurushotstesttavor.features.photosList.view.GeneralConstant.Companion.BASE_URL_PHOTO
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosAdapter (val mList : ArrayList<Item>, private val mContext: Context? ) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

   private lateinit var mListener: PhotosListener


    fun setListener(listener: PhotosListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Picasso.with(mContext)
                .load(BASE_URL_PHOTO + mList[position].width + "x" + mList[position].height + "/" + mList[position].memberId + "/" + "3_" + mList[position].id + ".jpg")
                .fit()
                .error(R.drawable.default_image)
                .into(holder.photoView)

        holder.likes.text = mList[position].likes.toString()
        holder.votes.text = mList[position].ratio.toString()
        holder.views.text = mList[position].views.toString()

        holder.photoView.setOnClickListener {
            mListener.onClickItem(position,mList[position])
        }
        /** When the user scrolling and reach to the end of the list */
        if(position==mList.size -1){
            mListener.onBottomReached(position)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        Picasso.with(mContext).cancelRequest(holder.photoView)
    }

    fun addItems(photosItemsList: ArrayList<Item>) {
        mList.addAll(photosItemsList)
    }

    /**
     *  Update the counter of the likes by position
     *  @param position
     *  @param isLiked
     */
    fun updateItem(position: Int, isLiked: Boolean?){
        var likes = mList[position].likes
        if(isLiked!!){
            mList[position].likes = likes?.plus(1)
        }
        else{
            mList[position].likes = likes?.minus(1)
        }
        notifyItemChanged(position)
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var photoView : ImageView = view.photoView
        var likes : TextView = view.likes
        var votes : TextView = view.votes
        var views : TextView = view.views
    }

    interface PhotosListener{
        fun onBottomReached(position: Int)
        fun onClickItem(position: Int,itemPhoto : Item)
    }
}
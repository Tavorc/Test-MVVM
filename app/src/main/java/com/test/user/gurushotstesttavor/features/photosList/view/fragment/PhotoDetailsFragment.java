package com.test.user.gurushotstesttavor.features.photosList.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.test.user.gurushotstesttavor.R;
import com.test.user.gurushotstesttavor.features.photosList.network.response.Item;
import com.test.user.gurushotstesttavor.features.photosList.view.GeneralConstant;
import com.test.user.gurushotstesttavor.features.photosList.view.activity.MainActivity;
import com.test.user.tavorapplication.infrastructure.view.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PhotoDetailsFragment extends BaseFragment implements MainActivity.OnDetailsFragmentListener {

    private ImageView mPhotoImageView;
    private TextView mLikesTextView, mVotesTextView, mViewsTextView;
    private ImageView mLikeImageView;
    private Item mSelectedPhoto;

    public static PhotoDetailsFragment newInstance() {
        Bundle args = new Bundle();
        PhotoDetailsFragment fragment = new PhotoDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_photo_details;
    }

    @Override
    public void initView(@NotNull ViewGroup view) {
        mPhotoImageView = view.findViewById(R.id.photo);
        mLikesTextView = view.findViewById(R.id.likes);
        mVotesTextView = view.findViewById(R.id.votes);
        mViewsTextView = view.findViewById(R.id.views);
        mLikeImageView = view.findViewById(R.id.imageViewLike);
        setListener();
    }

    private void setListener(){
        /** Register to the listener of the MainActivity */
        ( (MainActivity)(Objects.requireNonNull(getActivity())) ).mDetailsFragmentListener = this;
        mLikeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Get the current status of the like and update the ui respectively */
                boolean isLiked = ((MainActivity) (getActivity())).getMLikesList().get(mSelectedPhoto.getId());
                updateLikeButton(isLiked);
            }
        });
    }

    /**
     * Change the color of the like button and update the number of like
     * @param isLiked
     */
    @SuppressLint("SetTextI18n")
    private void updateLikeButton(boolean isLiked){
        int numberOfLikes = Integer.parseInt(mLikesTextView.getText().toString());
        if(!isLiked){

            ((MainActivity) (getActivity())).getMLikesList().put(mSelectedPhoto.getId(),true);
            numberOfLikes++;
            mLikeImageView.setColorFilter(getResources().getColor(R.color.orange_like));
        }
        else{
            numberOfLikes--;
            ((MainActivity) (getActivity())).getMLikesList().put(mSelectedPhoto.getId(),false);
            mLikeImageView.setColorFilter(getResources().getColor(R.color.white));
        }
        String newValue = String.valueOf(numberOfLikes);
        mLikesTextView.setText(newValue);
    }

    /**
     * When the user press on one button in the list, this listener is execute.
     * Update the ui with the data
     * @param itemPhoto
     */
    @Override
    public void onMoveToScreen(@NotNull Item itemPhoto) {
        mSelectedPhoto = itemPhoto;
        setDataOnUI(itemPhoto);
    }

    @SuppressLint("SetTextI18n")
    private void setDataOnUI(Item itemPhoto){
        mLikesTextView.setText(Objects.requireNonNull(itemPhoto.getLikes()).toString());
        mVotesTextView.setText(Objects.requireNonNull(itemPhoto.getRatio()).toString());
        mViewsTextView.setText(Objects.requireNonNull(itemPhoto.getViews()).toString());
        setImage(itemPhoto);
        setLikeButtonColor(itemPhoto.getId());
    }

    private void setImage(Item itemPhoto){
        Picasso.with(getActivity())
                .load(GeneralConstant.BASE_URL_PHOTO + itemPhoto.getWidth() + "x" + itemPhoto.getHeight() + "/" + itemPhoto.getMemberId() + "/" + "3_" + itemPhoto.getId() + ".jpg")
                .fit()
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(mPhotoImageView);
    }


    private void setLikeButtonColor(String id){
        Boolean isLiked = ((MainActivity) (getActivity())).getMLikesList().get(id);
        if(!isLiked){
            mLikeImageView.setColorFilter(getResources().getColor(R.color.white));
        }
        else{
            mLikeImageView.setColorFilter(getResources().getColor(R.color.orange_like));
        }
    }
}

package com.example.android.learntobake.Layouts;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.Models.Step;
import com.example.android.learntobake.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;

public class RecipeDetailsStepFragment extends Fragment {

    private final String SAVED_INSTANCE_STEPS = "saved_steps";
    private final String SAVED_INSTANCE_INDEX = "saved_index";
    private final String SAVED_INSTANCE_VIDEOPOS = "saved_videopos";

    private ArrayList<Step> steps;
    private Step currentStep;
    private int stepIndex;
    private long videoPosition = 0;

    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;
    private String videoURL;
    private Dialog fullScreenDialog;
    private LinearLayout.LayoutParams params;
    private boolean fullscreen;
    TextView description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fullscreen = getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        View root = inflater.inflate(R.layout.fragment_step_details, container, false);
        initiliazeUI(root);

        if (savedInstanceState == null) {
            steps = getArguments().getParcelableArrayList(RecipeDetails.RECIPE_STEPS_FRAGMENT_BUNDLE_KEY);
            stepIndex = getArguments().getInt(RecipeDetails.RECIPE_STEP_FRAGMENT_NUMBER_KEY);
        } else {
            steps = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_STEPS);
            stepIndex = savedInstanceState.getInt(SAVED_INSTANCE_INDEX);
            videoPosition = savedInstanceState.getLong(SAVED_INSTANCE_VIDEOPOS);
        }

        if (steps != null) {
            currentStep = steps.get(stepIndex);
            description.setText(currentStep.getDescription());
            putThumbnailIntoArtwork();

            videoURL = currentStep.getVideoURL();
            if (videoURL == null || videoURL.length() == 0) {
                exoPlayerView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "No Video Available for Step " + stepIndex, Toast.LENGTH_LONG).show();
            }
            Button previousButton = root.findViewById(R.id.step_details_back_button);
            Button nextButton = root.findViewById(R.id.step_details_next_button);
            setButtonOnClickListener(previousButton, -1);
            setButtonOnClickListener(nextButton, 1);
        } else {
            System.out.println("Steps are null");
        }
        return root;
    }

    private void setButtonOnClickListener(Button button, final int amount) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Setting onclick listners for button");
                if (stepIndex > 0 || (stepIndex == 0 && amount > 0)) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(RecipeDetails.RECIPE_STEPS_FRAGMENT_BUNDLE_KEY, steps);
                    bundle.putInt(RecipeDetails.RECIPE_STEP_FRAGMENT_NUMBER_KEY, stepIndex + amount);
                    RecipeDetailsStepFragment recipeDetailsStepFragment = new RecipeDetailsStepFragment();
                    recipeDetailsStepFragment.setArguments(bundle);
                    if (getResources().getBoolean(R.bool.isTablet)) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_detail_steps_container, recipeDetailsStepFragment, RecipeDetails.TAG_RECIPE_DETAILS_STEP_FRAGMENT)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_detail_container, recipeDetailsStepFragment, RecipeDetails.TAG_RECIPE_DETAILS_STEP_FRAGMENT)
                                .addToBackStack(null)
                                .commit();
                    }
                } else {
                    if (amount > 0) {
                        Toast.makeText(getContext(), "Already at First Step", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "At the Last Step", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void initiliazeUI(View root) {
        description = root.findViewById(R.id.step_details_description);
        exoPlayerView = root.findViewById(R.id.step_details_exoplayer_view);
        params = (LinearLayout.LayoutParams) exoPlayerView.getLayoutParams();
        setUpFullscreenDialog(root);
    }

    private void setUpFullscreenDialog(final View root) {
        fullScreenDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                ((ViewGroup) exoPlayerView.getParent()).removeView(exoPlayerView);
                exoPlayerView.setLayoutParams(params);
                ((LinearLayout) root.findViewById(R.id.step_details_linear_layout)).addView(exoPlayerView, 1);
                fullscreen = false;
                fullScreenDialog.dismiss();
                super.onBackPressed();
            }
        };
    }

    private void putThumbnailIntoArtwork() {
        final String thumbnailUrl = currentStep.getThumbnailURL();
        if (thumbnailUrl != null && thumbnailUrl.length() != 0) {
            final Uri uri = Uri.parse(thumbnailUrl).buildUpon().build();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap thumbnail = Picasso.with(getActivity()).load(thumbnailUrl).get();
                        exoPlayerView.setDefaultArtwork(thumbnail);
                    } catch (IOException e) {
                        Log.v("Error", "Error getting artwork into thumbnail");
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(SAVED_INSTANCE_STEPS, steps);
        outState.putInt(SAVED_INSTANCE_INDEX, stepIndex);
        outState.putLong(SAVED_INSTANCE_VIDEOPOS, videoPosition);
    }

    @Override
    public void onResume() {
        setUpPlayer();
        super.onResume();
    }

    private void setUpPlayer() {
        if (exoPlayer == null && videoURL != null && videoURL.length() != 0) {
            Uri videoUri = Uri.parse(videoURL).buildUpon().build();
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(factory);

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), defaultTrackSelector);
            exoPlayerView.setPlayer(exoPlayer);

            DataSource.Factory factoryDataSource = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), "LearnToBake"), new DefaultBandwidthMeter());
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource source = new ExtractorMediaSource(videoUri, factoryDataSource, extractorsFactory, null, null);

            if (fullscreen && !getResources().getBoolean(R.bool.isTablet)) {
                ((ViewGroup) exoPlayerView.getParent()).removeView(exoPlayerView);
                fullScreenDialog.addContentView(exoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                fullScreenDialog.show();
            }
            exoPlayer.seekTo(videoPosition);
            exoPlayer.prepare(source);
            exoPlayer.setPlayWhenReady(true);
            exoPlayerView.hideController();
        }
    }

    private void exoPlayerReleaseHelper() {
        exoPlayer.stop();
        exoPlayer.release();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            exoPlayerReleaseHelper();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            exoPlayerReleaseHelper();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayerReleaseHelper();
            exoPlayer = null;
        }
    }
}

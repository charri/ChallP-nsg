package ch.hsr.nsg.themenrundgang.view;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;

public class TutorialFragmentSubjects extends TutorialFragment {


    @InjectView(R.id.video)
    VideoView video;

    public static TutorialFragmentSubjects newInstance() {
        TutorialFragmentSubjects f = new TutorialFragmentSubjects();
        Bundle args = new Bundle();
        args.putInt(KEY_RESOURCE, R.layout.fragment_tutorial_subjects);
        f.setArguments(args);
        return f;
    }


    @Override
    protected void onInjected() {
        super.onInjected();

        Uri videoUri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/movie_subjects");

        video.setVideoURI(videoUri);

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
            video.seekTo(0);
            video.start();
            mp.setLooping(true);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(hidden && video != null) {
            video.pause();
        } else if(!hidden) {
            video.seekTo(0);
            video.start();
        }
    }

}

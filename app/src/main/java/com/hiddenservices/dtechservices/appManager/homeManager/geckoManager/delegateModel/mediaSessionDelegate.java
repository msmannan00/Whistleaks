package com.hiddenservices.dtechservices.appManager.homeManager.geckoManager.delegateModel;

import static com.hiddenservices.dtechservices.constants.strings.GENERIC_EMPTY_STR;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hiddenservices.dtechservices.appManager.homeManager.geckoManager.dataModel.geckoDataModel;
import com.hiddenservices.dtechservices.constants.enums;
import com.hiddenservices.dtechservices.helperManager.helperMethod;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.MediaSession;
import java.lang.ref.WeakReference;

public class mediaSessionDelegate implements MediaSession.Delegate{

    /*Private Variables*/

    private MediaSession mMediaSession = null;
    private mediaDelegate mMediaDelegate;
    private WeakReference<AppCompatActivity> mContext;
    private geckoDataModel mGeckoDataModel;
    private boolean mIsRunning = false;

    private Bitmap mMediaImage;
    private String mMediaTitle = GENERIC_EMPTY_STR;

    /*Initializations*/

    public mediaSessionDelegate(WeakReference<AppCompatActivity> pContext, geckoDataModel pGeckoDataModel, mediaDelegate pMediaDelegate){
        this.mContext = pContext;
        this.mMediaDelegate = pMediaDelegate;
        this.mGeckoDataModel = pGeckoDataModel;
    }

    /*Local Listeners*/

    @Override
    public void onActivated(@NonNull GeckoSession session, @NonNull MediaSession mediaSession) {
        MediaSession.Delegate.super.onActivated(session, mediaSession);
        mIsRunning = true;
        mMediaSession = mediaSession;
    }

    @Override
    public void onDeactivated(@NonNull GeckoSession session, @NonNull MediaSession mediaSession) {
        MediaSession.Delegate.super.onPause(session, mediaSession);
        mMediaDelegate.onHideDefaultNotification();
    }

    @Override
    public void onMetadata(@NonNull GeckoSession session, @NonNull MediaSession mediaSession, @NonNull MediaSession.Metadata meta) {
        try {
            mMediaTitle = meta.title;
            mMediaImage = meta.artwork.getBitmap(250).poll(2500);
            mMediaDelegate.showNotification(mContext.get(), mMediaTitle, helperMethod.getHost(mGeckoDataModel.mCurrentURL), mMediaImage, true);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        MediaSession.Delegate.super.onMetadata(session, mediaSession, meta);
    }

    @Override
    public void onFeatures(@NonNull GeckoSession session, @NonNull MediaSession mediaSession, long features) {
        MediaSession.Delegate.super.onFeatures(session, mediaSession, features);
    }

    @Override
    public void onPlay(@NonNull GeckoSession session, @NonNull MediaSession mediaSession) {
        MediaSession.Delegate.super.onPlay(session, mediaSession);
        mMediaDelegate.showNotification(this.mContext.get(), mMediaTitle, helperMethod.getHost(mGeckoDataModel.mCurrentURL), mMediaImage, true);
    }

    @Override
    public void onPause(@NonNull GeckoSession session, @NonNull MediaSession mediaSession) {
        MediaSession.Delegate.super.onPause(session, mediaSession);
        mMediaDelegate.showNotification(this.mContext.get(), mMediaTitle, helperMethod.getHost(mGeckoDataModel.mCurrentURL), mMediaImage, false);
    }

    @Override
    public void onStop(@NonNull GeckoSession session, @NonNull MediaSession mediaSession) {
        MediaSession.Delegate.super.onStop(session, mediaSession);
        mMediaDelegate.showNotification(this.mContext.get(), mMediaTitle, helperMethod.getHost(mGeckoDataModel.mCurrentURL), mMediaImage, false);
    }

    @Override
    public void onPositionState(@NonNull GeckoSession session, @NonNull MediaSession mediaSession, @NonNull MediaSession.PositionState state) {
        MediaSession.Delegate.super.onPositionState(session, mediaSession, state);
    }

    @Override
    public void onFullscreen(@NonNull GeckoSession session, @NonNull MediaSession mediaSession, boolean enabled, @Nullable @org.jetbrains.annotations.Nullable MediaSession.ElementMetadata meta) {
        MediaSession.Delegate.super.onFullscreen(session, mediaSession, enabled, meta);
    }

    /*Triggers*/

    public void onTrigger(enums.MediaController pCommands){
        if(mMediaSession!=null){
            if(pCommands.equals(enums.MediaController.PLAY)){
                mMediaDelegate.showNotification(this.mContext.get(), mMediaTitle, helperMethod.getHost(mGeckoDataModel.mCurrentURL), mMediaImage, true);
                mMediaSession.play();
            }
            else if(pCommands.equals(enums.MediaController.PAUSE)){
                mMediaSession.pause();
                if(mIsRunning){
                    mMediaDelegate.showNotification(this.mContext.get(), mMediaTitle, helperMethod.getHost(mGeckoDataModel.mCurrentURL), mMediaImage, false);
                }
            }
            else if(pCommands.equals(enums.MediaController.STOP)){
                mMediaSession.stop();
            }
            else if(pCommands.equals(enums.MediaController.DESTROY)){
                mMediaSession.stop();
                mMediaDelegate.onHideDefaultNotification();
                mIsRunning = false;
            }
            else if(pCommands.equals(enums.MediaController.SKIP_BACKWARD)){
                mMediaSession.previousTrack();
            }
            else if(pCommands.equals(enums.MediaController.SKIP_FORWARD)){
                mMediaSession.nextTrack();
            }
        }else {
            mMediaDelegate.onHideDefaultNotification();
        }
    }
}

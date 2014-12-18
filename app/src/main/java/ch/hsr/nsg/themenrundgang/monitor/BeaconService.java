package ch.hsr.nsg.themenrundgang.monitor;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;

import javax.inject.Inject;

import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingService;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.vm.ItemViewModel;
import ch.hsr.nsg.themenrundgang.vm.model.UiItem;
import ch.hsr.nsg.themenrundgang.vm.model.UiSubject;


public class BeaconService extends InjectingService {

    private final int NOTIFICATION_PERM = 0;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    public void setSubjects(UiSubject[] subjects) {
        viewModel.setSubjects(subjects);
    }

    public class LocalBinder extends Binder {
        public BeaconService getService() {
            return BeaconService.this;
        }
    }

    @Inject BeaconMonitor mBeaconMonitor;
    @Inject NotificationManager notificationManager;
    @Inject ItemViewModel viewModel;

    ArrayList<Region> mAllBeaconRegions;
    UiItemListener mListener;

    @Override
    public void onCreate() {
        super.onCreate();

        mBeaconMonitor.connect(new MonitorReadyCallback() {
            @Override
            public void onServiceReady() {
                mBeaconMonitor.setMonitoringListener(new BeaconListCallback());
                mAllBeaconRegions = viewModel.getRegionsForAllBeacons();
                try {
                    startMonitoringAllRegions();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        createScanningNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mBeaconMonitor.disconnect();
        notificationManager.cancelAll();
    }

    public void toggleBeaconUpdates(boolean fragmentPaused) {
        if (fragmentPaused) {
            mBeaconMonitor.setMonitoringListener(new BeaconNotificationCallback());
        } else {
            mBeaconMonitor.setMonitoringListener(new BeaconListCallback());
        }

    }

    private void startMonitoringAllRegions() throws RemoteException {
        for (Region region : mAllBeaconRegions) {
            mBeaconMonitor.startMonitoring(region);
        }
    }

    private void createScanningNotification() {
        Notification.Builder builder = new Notification
                .Builder(this)
                .setTicker("Naturmuseum Beacon-Suche")
                .setSmallIcon(R.drawable.ic_stat_device_bluetooth_searching)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle(getResources().getString(R.string.notification_scanning_title))
                .setContentText(getResources().getString(R.string.notification_scanning_text));

        Notification notification = builder.build();

        notification.flags |= Notification.FLAG_NO_CLEAR
                | Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(NOTIFICATION_PERM, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setUiItemListener(UiItemListener listener) {
        this.mListener = listener;
    }

    public interface UiItemListener {
        void addItem(UiItem item);
        void removeItem(UiItem item);
        void scrollTop();
    }

    public class BeaconNotificationCallback implements MonitoringListenerCallback {

        @Override
        public void onEnterRegion(Region region) {
            if (mListener == null) return;

            final int beaconUid = getBeaconUid(region.getMajor(), region.getMinor());
            createBeaconDetectedNotification(region);
        }

        @Override
        public void onExitRegion(Region region) {
            if (mListener == null) return;

            final int beaconUid = getBeaconUid(region.getMajor(), region.getMinor());
            notificationManager.cancel(beaconUid);
        }

        private final Uri[] SOUNDS = new Uri[] {
            Uri.parse("android.resource://" + getPackageName() + "/raw/sound_blop"),
            Uri.parse("android.resource://" + getPackageName() + "/raw/sound_rooster"),
            Uri.parse("android.resource://" + getPackageName() + "/raw/sound_grizzly"),
        };

        private int pos = -1;

        private void createBeaconDetectedNotification(Region region) {
            final int beaconUid = getBeaconUid(region.getMajor(), region.getMinor());

            Item[] interestingItems = viewModel.getItemsForSelectedSubjectsAndBeacon(region.getMajor(), region.getMinor());

            if(interestingItems == null) return;

            for(Item item : interestingItems) {

                String content = String.format(
                        getResources().getString(R.string.notification_beacon_text),
                        item.getName()
                );

                Notification.Builder builder = new Notification
                        .Builder(BeaconService.this)
                        .setTicker("Naturmuseum Beacon-Suche")
                        .setSmallIcon(R.drawable.ic_stat_dot_circle)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                        .setContentTitle(getResources().getString(R.string.notification_beacon_title))
                        .setContentText(content)
                        .setVibrate(new long[]{200})
                        .setSound(SOUNDS[++pos % SOUNDS.length]);

                Notification notification = builder.build();

                notification.flags |= Notification.FLAG_AUTO_CANCEL
                        | Notification.FLAG_SHOW_LIGHTS;

                notificationManager.notify(beaconUid, notification);
            }
        }

        private int getBeaconUid(int major, int minor) {
            final int greaterThanMinor = 100000;
            return greaterThanMinor * major + minor;
        }
    }

    public class BeaconListCallback implements MonitoringListenerCallback {

        @Override
        public void onEnterRegion(Region region) {
            if (mListener == null) return;

            UiItem[] uiItemsForRegion = viewModel.getUiItemsFrom(region);

            for (UiItem uiItem : uiItemsForRegion) {
                mListener.addItem(uiItem);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            mListener.scrollTop();
        }

        @Override
        public void onExitRegion(Region region) {
            if (mListener == null) return;

            UiItem[] uiItemsForRegion = viewModel.getUiItemsFrom(region);

            for (UiItem uiItem : uiItemsForRegion) {
                mListener.removeItem(uiItem);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

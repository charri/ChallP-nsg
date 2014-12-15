package ch.hsr.nsg.themenrundgang.monitor;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import javax.inject.Inject;

import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingService;


public class BeaconService extends InjectingService {

    private final int NOTIFICATION_PERM = 0;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        BeaconService getService() {
            return BeaconService.this;
        }
    }

    @Inject BeaconMonitor beaconMonitor;
    @Inject NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();


        createScanningNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        notificationManager.cancel(NOTIFICATION_PERM);
    }

    private void createScanningNotification() {
        Notification.Builder builder = new Notification
                .Builder(this)
                .setTicker("Naturmuseum Beacon-Suche")
                .setSmallIcon(R.drawable.ic_stat_device_bluetooth_searching)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle(getResources().getString(R.string.notification_perm_title));

        Notification notification = builder.build();

        notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(NOTIFICATION_PERM, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}

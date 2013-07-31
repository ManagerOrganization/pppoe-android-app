package pppoe.school;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class PPPoEService extends Service {

	private Handler handlerCheck = null;
	private Runnable runnableCheck = null;

	@Override
	public void onCreate() {
		super.onCreate();

		final AppStatus appStatus = new AppStatus(PPPoEService.this);
		final AppNotify appNotify = new AppNotify(PPPoEService.this);

		handlerCheck = new Handler();
		runnableCheck = new Runnable() {
			@Override
			public void run() {

				int nStatus = appStatus.getStatus();
				switch (nStatus) {
				case AppStatus.STATE_CONNECT:
					break;

				case AppStatus.STATE_ERROR:
					stopThis();
					appNotify.notifyStatus("�����쳣��", "�����Ѿ��Ͽ����磬����˴��鿴��Ϣ��");
					return;

				case AppStatus.STATE_DISCONNECT:
					stopThis();
					appNotify.notifyStatus("�����ѶϿ�!", "����˴��鿴��Ϣ��");
					return;
				}

				// ÿ����ִ��һ��_handler_Net.
				handlerCheck.postDelayed(this, 2000);
			}
		};

		handlerCheck.postDelayed(runnableCheck, 0);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		
		AppLog.Log("server: start");
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		stopRunning();
		
		AppLog.Log("server: stop");
		super.onDestroy();
	}

	private void stopRunning() {
		if (handlerCheck != null) {
			handlerCheck.removeCallbacks(runnableCheck);
			handlerCheck = null;
		}
	}

	private void stopThis() {
		stopRunning();
		stopSelf();
	}
}

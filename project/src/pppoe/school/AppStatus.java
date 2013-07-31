package pppoe.school;

import pppoe.encrypt.Command;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@SuppressLint("NewApi") public class AppStatus {

	public static final int STATE_CONNECT = 1;
	public static final int STATE_DISCONNECT = 0;
	public static final int STATE_ERROR = -1;
	public static final int STATE_HAS_PPPD = -2;

	private Context context = null;

	public AppStatus(Context context) {
		this.context = context;
	}

	public int getStatus() {
		final Command command = new Command();
		
		if (command.getEthnet("wlan").isEmpty() && command.getEthnet("eth").isEmpty()) {
			return STATE_ERROR;
		} else if (!getNetConnected()) {
			return STATE_ERROR;
		} else if (command.getEthnet("ppp").isEmpty()) {
			return STATE_DISCONNECT;
		}

		return STATE_CONNECT;
	}

	public boolean getNetConnected() {
		final ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifi.isConnected();
	}

	public String getError() {
		Command command = new Command();

		String strError = "�������⣺";

		// �������
		if (command.getEthnet("wlan").isEmpty() && command.getEthnet("eth").isEmpty()) {
			strError += "\nû�п���������PS:�������������ӣ��������Ѳ�ã�";
		} else if (!getNetConnected()) {
			strError += "\n��...PS:����û�����ӣ���ȷ���ѽ���Wifi�ȵ���������磡";
		}
		// ���rootȨ��
		else if (!command.isRoot()) {
			strError += "\nû��RootȨ�ޣ�PS:���ֻ�Root�������԰ɣ�";
		} else {
			strError += "\n��...PS:Ҫ�������԰ɣ������о͵���̳ˢ��֧�ֲ��ŵ�Rom�ɣ�";
		}

		return strError;
	}
}

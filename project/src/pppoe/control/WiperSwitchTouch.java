package pppoe.control;

import pppoe.school.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


/**
 * 
 * �򵥵��л��ؼ�
 * 
 * @author yawen
 *
 */
public class WiperSwitchTouch extends View implements OnTouchListener {

	private Bitmap bitmapOn = null;
	private Bitmap bitmapOff = null;
	private Bitmap bitmapSlipper = null;

	private boolean bStatus = false;

	private OnChangedListener listenerChange;

	public WiperSwitchTouch(Context context) {
		super(context);
		initSwitch();
	}

	public WiperSwitchTouch(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSwitch();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		float x = 0;

		if (!bStatus) {
			canvas.drawBitmap(bitmapOff, matrix, paint);
			x = 0;
		} else {
			canvas.drawBitmap(bitmapOn, matrix, paint);
			x = bitmapOn.getWidth() - bitmapSlipper.getWidth();
		}

		// ��������
		canvas.drawBitmap(bitmapSlipper, x, 0, paint);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP: {
			bStatus = !bStatus;
			if (listenerChange != null) {
				listenerChange.OnChanged(WiperSwitchTouch.this, bStatus);
			}
			break;
		}
		}
		// ˢ�½���
		invalidate();
		return true;
	}

	private void initSwitch() {
		// ����ͼƬ��Դ
		bitmapOn = BitmapFactory.decodeResource(getResources(), R.drawable.switch_on);
		bitmapOff = BitmapFactory.decodeResource(getResources(), R.drawable.switch_off);
		bitmapSlipper = BitmapFactory.decodeResource(getResources(), R.drawable.switch_slider);

		setOnTouchListener(this);
	}

	/**
	 * ΪWiperSwitch����һ�����������ⲿ���õķ���
	 * 
	 * @param listener
	 */
	public void setOnChangedListener(OnChangedListener listener) {
		this.listenerChange = listener;
	}

	/**
	 * ���û������صĳ�ʼ״̬�����ⲿ����
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		bStatus = checked;
	}

	/**
	 * �õ��������صĳ�ʼ״̬�����ⲿ����
	 * 
	 * @param checked
	 */
	public boolean getChecked() {
		return bStatus;
	}

	/**
	 * �ص��ӿ�
	 * 
	 * @author len
	 * 
	 */
	public interface OnChangedListener {
		public void OnChanged(WiperSwitchTouch wiperSwitch, boolean checkState);
	}
}
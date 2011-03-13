package com.Bounce;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.Bounce.PaintableObjects.IPaintObject;
import com.Bounce.PaintableObjects.TestCircle;
import com.Bounce.PaintableObjects.VisualObjectBase;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;

public class Game {
	

	
	public List<IPaintObject> Objects = new ArrayList<IPaintObject>();
	public int TICK_SIZE = 100;
	
	private Handler mHandler = new Handler();
	private FrameLayout mView;
	private Context mContext;
	
	
	private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
			   long timeConsumedByTick;
			   do{
				   long	beforeTick = System.currentTimeMillis();
				   Tick();
				   mView.invalidate();
				   long	afterTick = System.currentTimeMillis();
				   timeConsumedByTick = afterTick - beforeTick;
			   }
			   while(timeConsumedByTick<Constants.TIME_BETWEEN_TICKS);
			   long delay = Constants.TIME_BETWEEN_TICKS -timeConsumedByTick; 
		       mHandler.postDelayed(mUpdateTimeTask, delay);
		   }
		};
	
	public Game(Context context)
	{
		mContext = context;
	}	
		
	public void Start(){

		 mHandler.postDelayed(mUpdateTimeTask, 100);

	}
	public void Stop(){
		
	}

	
	public void Tick(){

			List<IPaintObject> ObjectsToDelete = new ArrayList<IPaintObject>();
			for (IPaintObject obj : Objects) {
				if (!obj.Tick()) {
					ObjectsToDelete.add(obj);
				}
			}
			Objects.removeAll(ObjectsToDelete);
	}
	
	public void CreateObjects(FrameLayout view)
	{
		mView = view;
		// Add objects here
		AddObject(new TestCircle(mContext, 200, 50));

	}	
	private void AddObject(IPaintObject object)
	{
		Objects.add(object);
		mView.addView((View)object);
	}
}

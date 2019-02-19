package com.exmper.administrator.decompress2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.view.ActionMode;
import android.view.InflateException;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2018/12/1.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback , Runnable{
    private boolean flag;
    public static int screenW, screenH;

    private SurfaceHolder sfh;


     private  static int GAME_MENU = 0 ;
     private  static int GAMEING = 1;
     private  static int GAME_WIN = 2;
     private static int GAME_FIAL = 3;
     private static int  GAME_PAUSE = -1;
     public static int gameState = GAME_MENU;

    private Bitmap boomBitmap;
    private Paint paint;
    private Canvas canvas;

    public MySurfaceView(Context context) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    private void initGame() {
        boomBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.boom);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();

        initGame();
        flag = true;
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        while(flag)
        {
            //long start = System.currentTimeMillis();
            myDraw();
            //logic();
           // long end = System.currentTimeMillis();
            try{

            }
            catch (InflateException e){
                e.printStackTrace();
            }
        }
    }

    private void myDraw() {
        Boom boom = new Boom(boomBitmap,100,100,7);
        boom.draw(this.canvas,paint);

    }

   /* private void logic() {
        switch (gameState){
            case GAME_MENU:
                break;
            case GAMEING:
                break;
            case GAME_WIN:
                break;
            case GAME_FIAL:
                break;
            case GAME_PAUSE:
                break;
            default:
                break;
        }
    }*/

    //SurfaceView
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    //SurfaceView
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }
    

}

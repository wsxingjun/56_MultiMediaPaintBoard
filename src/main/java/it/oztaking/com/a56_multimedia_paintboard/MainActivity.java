package it.oztaking.com.a56_multimedia_paintboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private Bitmap copyBitmap;
    private Paint paint;
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.将图片设置到imageView中；
        iv = (ImageView) findViewById(R.id.iv);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.backpic);

        //2.创建模板
        copyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //3.以copyBitmap为模板，创建画板
        final Canvas canvas = new Canvas(copyBitmap);
        //4.创建画笔
        paint = new Paint();


        //开始作画
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        iv.setImageBitmap(copyBitmap);

        iv.setOnTouchListener(new View.OnTouchListener(){
            int startX = 0;
            int startY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //【6】获取触摸的事件
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //【7】获取手指按下坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        System.out.println("按下-x:" + startX + "--y:" + startY);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //【7】获取手指移动
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        System.out.println("移动-x:" + stopX + "--y:" + stopY);

                        //【8】划线
                        canvas.drawLine(startX, startY, stopX, stopY, paint);

                        //【9】更新起点坐标
                        startX = stopX;
                        startY = stopY;

                        iv.setImageBitmap(copyBitmap);

                        break;
                }
                //return false;
                return true;
            }
        });

}

    public void ClickPaintRed(View v) {

        paint.setColor(Color.RED);
    }

    public void ClickPaintBold(View v) {
        paint.setStrokeWidth(15);
    }

    //保存图片的功能
    public void ClickSavePic(View v)  {
        File file = new File(Environment.getExternalStorageDirectory().getPath(),"dazuo.png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            copyBitmap.compress(Bitmap.CompressFormat.PNG,100, fos);

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            sendBroadcast(intent);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}





















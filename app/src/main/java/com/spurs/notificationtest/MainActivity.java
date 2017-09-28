package com.spurs.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View v){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);

        //빌더에게 여러속성들(제목, 내용, 아이콘들) 설정

        //상태표시줄에 보이는 내용
        builder.setTicker("Notification"); //상태표시줄에 잠시 보이는 글씨
        builder.setSmallIcon(R.mipmap.ic_launcher);

        //확장된 상태표시줄(content영역)에 보이는 내용
        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        builder.setLargeIcon(bm);
        builder.setContentTitle("Title");
        builder.setContentText("message..........");
        builder.setSubText("sub text");

        builder.setWhen(System.currentTimeMillis()+3600000);
        builder.setNumber(13);

        //알림을 확인했을때(확장된 상태바를 클릭) secondActivity 실행
        Intent intent=new Intent(this,SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //확장상태바에 액션추가
        builder.addAction(android.R.drawable.ic_dialog_email,"더보기",pendingIntent);

        //진동추가
        builder.setVibrate(new long[]{0, 500});//0초대기 후 1초 진동

        //사운드추가
        Uri soundUri= RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(soundUri);

        //알림을 확인했을때 알림 자동 삭제
        builder.setAutoCancel(true);

        //확장상태바의 추가 style
        //1. BigPictureStyle
/*        android.support.v4.app.NotificationCompat.BigPictureStyle pictureStyle=new android.support.v4.app.NotificationCompat.BigPictureStyle(builder);
        pictureStyle.bigLargeIcon(bm);
        pictureStyle.bigPicture(bm);
        pictureStyle.setBigContentTitle("제목");
        pictureStyle.setSummaryText("this is summaryText");*/

        //2.BitTextStyle
/*        android.support.v4.app.NotificationCompat.BigTextStyle textStyle=new android.support.v4.app.NotificationCompat.BigTextStyle(builder);
        textStyle.setBigContentTitle("타이틀");
        textStyle.setSummaryText("summary");
        textStyle.bigText("bigtext\n\n\nbigtext\neaaaa");*/

        //3. InboxStyle
        android.support.v4.app.NotificationCompat.InboxStyle inboxStyle=new android.support.v4.app.NotificationCompat.InboxStyle(builder);
        inboxStyle.setBigContentTitle("인박스 타이틀");
        inboxStyle.setSummaryText("한줄씩 텍스트를 추가할 수 있는 스타일");

        inboxStyle.addLine("aaa");
        inboxStyle.addLine("ddd");
        inboxStyle.addLine("bbb");
        inboxStyle.addLine("ccc");

        //빌더에게 설정된 값을 기반으로 notification 객체 생성하도록
        Notification notification=builder.build();

        //Notification 객체를 상태표시줄에 보여주는 작업을 수행하는 매니져 얻어오기
        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification);

    }
}

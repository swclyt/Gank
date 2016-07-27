package com.example.swchalu.gank.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by swchalu on 2016/7/27.
 */
public class MyDownLoadManager {

    private final String Tag = "MyDownLoadManager";
    private DownloadManager.Request request;
    private DownloadManager myDownLoadManager;
    private DownLoadCompleteReceiver receiver;

    public long download(Context context, String url, String savePath) {
        String[] path = url.split("/");
        String filename = path[path.length - 1];
        Log.i(Tag, "filename = " + filename);
        request = new DownloadManager.Request(Uri.parse(url));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("保存图片");
        request.setDescription("图片正在下载");
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalPublicDir("/com.example.swchalu.gank/download/", filename);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        receiver = new DownLoadCompleteReceiver();
        context.registerReceiver(receiver, filter);

        myDownLoadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long id = myDownLoadManager.enqueue(request);
        return id;
    }

    public DownloadManager get() {
        return myDownLoadManager;
    }

    public void remove(long id) {
        if (myDownLoadManager != null) {
            myDownLoadManager.remove(id);
        }
    }

    public class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(context, "图片已保存", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

package com.song.zzb.wyzzb.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.song.zzb.wyzzb.R;

import com.song.zzb.wyzzb.adapter.MyWrongAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2016/2/27.
 */
public class MyWrongActivity extends BaseActivity{
    public static final String WAsetFilename = "My_WrongSet.txt";
    ListView listView;
    private MyWrongAdapter adapter;
    FileInputStream fis;
    FileOutputStream fos;
    List<String> list;
    int removePosition;
//    Cursor cursor;
//    DBAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywrong);
        Init();
        OnPaint();// 初始化
		/*
		 * 清空题库
		 */
        rightMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clearAllDialog();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String tmp = list.get(arg2);
                tmp = tmp.substring(0, tmp.indexOf('.'));
                Intent intent = new Intent();
                intent.putExtra("option", 2);
                intent.putExtra("startfrom", Integer.parseInt(tmp)-1);
                intent.setClass(MyWrongActivity.this,ExameActivity.class);
                startActivity(intent);
            }
        });



    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        OnPaint();
        super.onResume();
    }


    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        OnPaint();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
//        dbAdapter.close();
        super.onDestroy();
    }

    public void removeItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除这道题吗？");

        builder.setTitle("注意");

        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface v, int which) {
                        // TODO Auto-generated method stub
                        // dialog.dismiss();
                        removeItem();
                    }
                });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    public void removeItem() {
        String Text = "";
        try {
            fos = openFileOutput(MyWrongActivity.WAsetFilename, MODE_PRIVATE);
            for (int i = 0; i < list.size(); i++) {
                if (i != removePosition) {
                    Text += list.get(i) + "#";
                }
            }
            if (Text.compareTo("") == 0)
                Text = "#";
            fos.write(Text.getBytes());
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                }
            }
            OnPaint();
        }
    }

    public void clearAll() {
        try {
            fos = openFileOutput(MyWrongActivity.WAsetFilename, MODE_PRIVATE);
            String Text = "#";
            fos.write(Text.getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //ShowToast(e.toString());
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        OnPaint();
    }

    protected void clearAllDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认清空题库吗？");

        builder.setTitle("注意");

        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface v, int which) {
                        // TODO Auto-generated method stub
                        // dialog.dismiss();
                        clearAll();
                    }
                });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    public void ShowToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    private void Init() {
        // TODO Auto-generated method stub

        listView = (ListView) findViewById(R.id.walist);
        setUpTitle("我的错题");
        setUpRightMenu("清空题库");
        list = new ArrayList<String>();
//        try{
//            dbAdapter = new DBAdapter(this);
//            dbAdapter.open();
//            cursor = dbAdapter.getAllData();
//        }catch (Exception e) {
//            // TODO: handle exception
//        }

    }

    public void OnPaint() {
        InputStream in;
        BufferedReader br = null;
        String tmp;
        list.clear();
        try {
            String Text = "";
            fis = openFileInput(MyWrongActivity.WAsetFilename);
            byte[] readBytes = new byte[fis.available()];
            while (fis.read(readBytes) != -1) {
                Text = new String(readBytes);
                //ShowToast("fuck");
            }
            String[] tmp_waset = Text.split("#");
            if (tmp_waset[0].compareTo("") != 0) {
                for (int i = 0; i < tmp_waset.length; i++)
                {
                    //cursor.moveToPosition(Integer.parseInt(tmp_waset[i]));
                    list.add(tmp_waset[i]);// + cursor.getString(cursor.getColumnIndex(DBAdapter.TESTSUBJECT)));
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //ShowToast(e.toString());
        }

        adapter = new MyWrongAdapter(this, list, listView);
        listView.setAdapter(adapter);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//				android.R.layout.test_list_item, list);
//		listView.setAdapter(adapter);
        if (list.size() != 0) {

            Toast.makeText(MyWrongActivity.this, "当前错题库数量为：" + list.size(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MyWrongActivity.this, "暂无错题记录",
                    Toast.LENGTH_SHORT).show();
        }
    }

}


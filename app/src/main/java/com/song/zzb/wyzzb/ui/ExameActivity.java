package com.song.zzb.wyzzb.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.bean.TiKuContent;
import com.song.zzb.wyzzb.util.ActivityUtil;
import com.song.zzb.wyzzb.util.DeletableEditText;
import com.song.zzb.wyzzb.util.ToastUtil;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by song on 2016/2/24.
 */
public class ExameActivity extends BaseActivity{
    private CheckBox cb1,cb2,cb3,cb4;

    private Button btn_submit,answer_btn;//复选、填空按钮
    private TextView tv_title,tv_explain,tv_record,tv_type;
   private DeletableEditText det_answer_input;
    private RadioButton radioA,radioB,radioC,radioD;
    private RadioGroup radioGroup;
    private TextView forword_btn,next_btn,tv_submittest,tv_addwrong,answer_tv,tv_explain_btn,core_tv;//底部布局
    private LinearLayout ly_wrong,ly_title,ly_edt,sc_ly;
    public static final int TESTLIMIT = 100;
    boolean isHandIn;// 表示交卷后
    private int Option;// 表示是随机 or 顺序 or错题
    private int isexplain;//判断是否点了解析
    private String[] mySelect = new String[100];// 我做的题目答案，是我已经选择的题目
    private String[] testAnswer = new String[100];//题目的正确答案
    private int curIndex = 0;//题目编号
    private Double resultInt;//答题统计
    private int[] myWAset = new int[1074];// 以往错题
    public  int problemLimit = 0;
    FileInputStream fis;
    FileOutputStream fos;
    private List<TiKuContent> tiKuContentList=new ArrayList<TiKuContent>();
    private int[] value;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame);
        initView();
        //给数组初始化
        for(int i= 0;i<=99;i++){
            mySelect[i]="";
            testAnswer[i]="";
        }
        value = new int[2];
        Bundle bundle= getIntent().getExtras();
        value=bundle.getIntArray("value");
        Log.i("value的值",value[0]+"///"+value[1]);
        //得到题库数据
        queryAnswer(value[0], value[1]);
        forword_btn.setOnClickListener(new View.OnClickListener() {
            // 上一题
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //当前为第一题时，给出提示，使上一题按钮不可用
                if (curIndex == 0) {
                    ToastUtil.showToast(getApplicationContext(), "当前为第一题");
//                    tv_submit.setEnabled(false);
                } else {
                    //当前为错题
                    if (Option == 2) {
                    // 为真，是交卷的情况
                        int tindex = curIndex;
                        while (--tindex >= 0) {
                            if (myWAset[tindex]==1) {
                                curIndex = tindex;
                                initSubject();
                                return;
                            }
                        }
                        ToastUtil.showToast(getApplicationContext(), "当前为第一题");
                        return;
                    } else {
                        //为假，如果没有交卷，则返回上一题
                        curIndex--;
                        initSubject();
//                        tv_submit.setEnabled(false);
                    }
                }

            }
        });

         /*
		 * 下一题
		 */
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("tiKuContentList.size()", "" + tiKuContentList.size());
                if (curIndex == tiKuContentList.size() - 1) {
                    ToastUtil.showToast(getApplicationContext(), "当前为最后一题");


                } else {
                    //当前为错题
                    if (Option == 2) {
                        // 为真，是交卷的情况
                        int tindex = curIndex;
                        while (++tindex < problemLimit) {
                            if (myWAset[tindex]==1) {
                                curIndex = tindex;
                                initSubject();
                                return;
                            }
                        }
                        ToastUtil.showToast(getApplicationContext(), "当前为第一题");
                        return;
                    } else {
                        curIndex++;
                        initSubject();
                    }
                }
            }

        });
        /*
		 * 交卷
		 */
        tv_submittest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeOutDialog(true, "2");
            }
        });
        //返回键
        leftMenu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // finish();

                showTimeOutDialog(true, "1");

            }
        });
        /*
		 * 加入错题库
		 */
        tv_addwrong.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Option == 2){
                    myWAset[curIndex] = 0;
                    saveWaset();
                    ToastUtil.showToast(getApplicationContext(), "移除成功");

                }else{
                    myWAset[curIndex] = 1;
                    saveWaset();
                    ToastUtil.showToast(getApplicationContext(), "加入成功");
                }


            }
        });
        		/*
		 * 选择radio
		 */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (!isHandIn) {//没交卷的情况下
                    if (radioA.isChecked() && !mySelect[curIndex].equals("1")) {
                        mySelect[curIndex] = "1";
                        next_btn.performClick();//模拟点击了下一题，是使用代码主动去调用控件的点击事件（模拟人手去触摸控件）
                    } else if (radioB.isChecked() && !mySelect[curIndex].equals("2")) {
                        mySelect[curIndex] = "2";
                        next_btn.performClick();
                    } else if (radioC.isChecked() && !mySelect[curIndex].equals("3")) {
                        mySelect[curIndex] = "3";
                        next_btn.performClick();
                    } else if (radioD.isChecked() && !mySelect[curIndex].equals("4")) {
                        mySelect[curIndex] = "4";
                        next_btn.performClick();
                    }
                }
            }
        });
	     /*复选框监听事件*/
        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String result = "";  //用来存放结果
                /*取出选中的内容，换行显示*/
                if (cb1.isChecked()) result += 1;
                if (cb2.isChecked()) result += 2;
                if (cb3.isChecked()) result += 3;
                if (cb4.isChecked()) result += 4;
                if (!result.equals("")) {
                    mySelect[curIndex] = result;
                    next_btn.performClick();
                } else {
                    ToastUtil.showToast(getApplicationContext(), "请选择");

                }

            }
        });
        /*复选框监听事件*/
        answer_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!det_answer_input.getText().toString().equals("")){
                    mySelect[curIndex] =det_answer_input.getText().toString();
                    next_btn.performClick();
                } else{
                    ToastUtil.showToast(getApplicationContext(),"请填空");
                }

            }
        });

    }
    /*
 * 保存错题库;
 */
    public void saveWaset() {
        try {
            String text = "";
            fos = openFileOutput("My_WrongSet.txt", MODE_PRIVATE);
            for (int i = 0; i < problemLimit; i++) {
                if (myWAset[i] == 1) {
                    text += (i + 1)
                            + "."
                            + tiKuContentList.get(i).getTitleSubject()
                            + "#";
                }
            }
            if (text.compareTo("") == 0)
                text = "#";
            fos.write(text.getBytes());
        } catch (Exception e) {
            // TODO: handle exception
            // ShowToast(e.toString());
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
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            showTimeOutDialog(true, "1");

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void showScore() {
        final Dialog builder = new Dialog(this, R.style.dialog);
        builder.setContentView(R.layout.result_dialog);
        TextView title = (TextView) builder.findViewById(R.id.dialog_title);
        TextView content = (TextView) builder.findViewById(R.id.dialog_content);
        if (resultInt == 100) {
            content.setText("哇！满分！棒棒哒！");
        } else{
            content.setText("您的得分为"+resultInt);
        }
        final Button confirm_btn = (Button) builder
                .findViewById(R.id.dialog_sure);
        Button cancel_btn = (Button) builder.findViewById(R.id.dialog_cancle);
        confirm_btn.setText("查看错题");
        cancel_btn.setText("退出");
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    builder.dismiss();


            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
                    builder.dismiss();
            }
        });
        builder.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        builder.show();
    }
    // 弹出对话框通知用户答题时间到
    protected void showTimeOutDialog(final boolean flag, final String backtype) {
        final Dialog builder = new Dialog(this, R.style.dialog);
        builder.setContentView(R.layout.my_dialog);
        TextView title = (TextView) builder.findViewById(R.id.dialog_title);
        TextView content = (TextView) builder.findViewById(R.id.dialog_content);
        if (backtype.equals("0")) {
            content.setText("您的答题时间结束,是否提交试卷?");
        } else if(backtype.equals("1")){
            content.setText("您要结束本次模拟答题吗？");
        }else if(backtype.equals("2")){
            content.setText("您确定交卷吗?");
        }else{
            content.setText("");
        }
        final Button confirm_btn = (Button) builder
                .findViewById(R.id.dialog_sure);
        Button cancel_btn = (Button) builder.findViewById(R.id.dialog_cancle);
        if (backtype.equals("0")) {
            confirm_btn.setText("提交");
            cancel_btn.setText("退出");
        } else if(backtype.equals("1")){
            confirm_btn.setText("退出");
            cancel_btn.setText("继续答题");
        }
        else if(backtype.equals("2")){
            confirm_btn.setText("提交");
            cancel_btn.setText("继续答题");
        }else{
            confirm_btn.setText("确定");
            cancel_btn.setVisibility(View.GONE);
        }
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backtype.equals("0")) {
                    builder.dismiss();
//						uploadExamination(pagerAdapter.errorTopicNum());
                } else if (backtype.equals("2")) {
                    builder.dismiss();
                    handlerAfterHandIn();
                } else {
                    builder.dismiss();
                    finish();
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backtype.equals("0")) {
                    finish();
                    builder.dismiss();
                } else {
//					isPause = false;
                    builder.dismiss();

//					handlerStopTime.sendMessage(msg);
                }
            }
        });
        builder.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                return flag;
            }
        });
        builder.show();
    }
    // 处理交卷后
    protected void handlerAfterHandIn() {
        // TODO Auto-generated method stub
		/*
		 * 成绩统计 错题统计 isHandIn标志修改 时间标志改成加入错题库标志 上下题变成错题的上下题
		 */
        tv_addwrong.setVisibility(View.VISIBLE);
        tv_submittest.setEnabled(false);
        isHandIn = true;
        int radiocount=0;//1分题
        int checkcount=0;//1.5分题
        for (int i = tiKuContentList.size()-1; i >= 0; i--) {

            //将正确答案存入testAnswer
            if(!tiKuContentList.get(i).getAnswerCode().equals(""))
             testAnswer[i]=tiKuContentList.get(i).getAnswerCode().trim();
            Log.i("testAnswer[i]my",testAnswer[i]+"/"+i+"/"+mySelect[i]);


            //进行比较得出结果
            if (testAnswer[i].equals(mySelect[i])) {
                if(i<=30 || (i>=41 && i<=50)||(i>=71 && i<=85)){
                    radiocount++;

                }if((i>=30 && i<=40) || (i>=51 && i<=70)){
                    checkcount++;
                }

            }
        }
        resultInt=radiocount+(checkcount * 1.5);
        showScore();
        curIndex = 0;
        for (int i = 0; i < 99; i++) {
            //如果我做的答案不等于正确答案，则将当前
            if (!mySelect[i].equals( testAnswer[i])) {
                curIndex = i;
                break;
            }
        }
        initSubject();
    }

    private void initView(){
        setUpTitle(getIntent().getExtras().getString("title"));
        setUpLeftMenu(1);
        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
           Option = bundle.getInt("option");
        }
        sc_ly=(LinearLayout)findViewById(R.id.sc_ly);
        ly_title=(LinearLayout)findViewById(R.id.ly_title);
        ly_edt=(LinearLayout)findViewById(R.id.ly_edt);//填空题布局
        tv_record=(TextView)findViewById(R.id.tv_record);//做题记录
        tv_type=(TextView)findViewById(R.id.tv_type);//题目类型
        tv_title = (TextView) findViewById(R.id.tv_title); // 题目 标题
        tv_explain = (TextView) findViewById(R.id.tv_explain);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioA = (RadioButton) findViewById(R.id.radioA);//单选
        radioB = (RadioButton) findViewById(R.id.radioB);
        radioC = (RadioButton) findViewById(R.id.radioC);
        radioD = (RadioButton) findViewById(R.id.radioD);
        forword_btn = (TextView) findViewById(R.id.tv_up);//上一题
        next_btn = (TextView) findViewById(R.id.nextBtn);//下一题
        tv_submittest = (TextView) findViewById(R.id.tv_submittest);//交卷
        tv_explain_btn =(TextView)findViewById(R.id.tv_explain_btn);//判断用户是否点了解析
        ly_wrong = (LinearLayout) findViewById(R.id.ly_wrong);//答案解析布局
        tv_addwrong = (TextView) findViewById(R.id.tv_addwrong);//加入错题库
        answer_tv = (TextView) findViewById(R.id.answer_tv);//解析，显示答案位置
        core_tv = (TextView) findViewById(R.id.core_tv);
        cb1 = (CheckBox) findViewById(R.id.cb1);//复选框
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        btn_submit= (Button) findViewById(R.id.btn_submit);//提交答案
        answer_btn=(Button)findViewById(R.id.answer_btn);
        det_answer_input=(DeletableEditText)findViewById(R.id.det_answer_input);
        tv_explain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isexplain==0){
                    isexplain=1;
                    tv_explain_btn.setTextColor(getResources().getColor(R.color.color_theme));
                    ly_wrong.setVisibility(View.VISIBLE);
                }else {
                    ly_wrong.setVisibility(View.GONE);
                    tv_explain_btn.setTextColor(Color.BLACK);
                    isexplain=0;
                }

            }
        });
        /*
		 * 错误题目读取
		 */
        try {
            String Text = "";
            fis = openFileInput(MyWrongActivity.WAsetFilename);
            byte[] readBytes = new byte[fis.available()];
            while (fis.read(readBytes) != -1) {
                Text = new String(readBytes);
            }
            String[] tmp_waset = Text.split("#");
            String tmpString;
            if (tmp_waset[0].compareTo("") != 0) {
                for (int i = 0; i < tmp_waset.length; i++) {
                    tmpString = tmp_waset[i].substring(0,
                            tmp_waset[i].indexOf('.'));
                    // ShowToast(tmpString);
                    myWAset[Integer.parseInt(tmpString) - 1] = 1;

                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            // ShowToast(e.toString());
        }
        //我的错题
        if (Option == 2) {
            curIndex = bundle.getInt("startfrom");
            queryAllAnswer();
            tv_addwrong.setText("移除错题库");
        }
    }
    //得到模拟对应编号的模拟试题题目,试题类型为多少多少套题
    protected void queryAnswer(int index,int type ){
        // TODO Auto-generated method stub
        BmobQuery<TiKuContent> tiKuContent = new BmobQuery<TiKuContent>();
        //按照试题类型
        tiKuContent.addWhereEqualTo("questiontype", type);
        tiKuContent.addWhereGreaterThanOrEqualTo("indexID", (index - 1) * 100 + 1);//题号>=0      1  1 100  2 101 200 3 301 400
         tiKuContent.addWhereLessThanOrEqualTo("indexID", (index - 1) * 100 + 100);//题号<=100
        Log.i("indexID",""+(index - 1) * 100 + 1+"////"+(index - 1) * 100 + 100);
        //按照时间降序
        tiKuContent.order("indexID");
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = tiKuContent.hasCachedResult(this, TiKuContent.class);
        if(isCache){
            if(ActivityUtil.hasNetwork(this)){
                tiKuContent.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则
            }else{
                //--此为举个例子，并不一定按这种方式来设置缓存策略
                tiKuContent.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            }
        }else{
            tiKuContent.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        tiKuContent.findObjects(this, new FindListener<TiKuContent>() {
            @Override
            public void onSuccess(List<TiKuContent> data) {
                // TODO Auto-generated method stub
                if (data != null) {
                    tiKuContentList.addAll(data);
                    problemLimit =tiKuContentList.size();
                    initSubject();
                    for (int i= 0;i<tiKuContentList.size();i++) {
                         Log.i("tiku", "" +i+tiKuContentList.isEmpty() + "/" + tiKuContentList.get(i).getTitleSubject());

                   }
                }
            }
            @Override
            public void onError(int errorCode, String errorString) {
                // TODO Auto-generated method stub
            }
        });
    }

    //得到题库中的所有试题题目
    protected void queryAllAnswer(){
        // TODO Auto-generated method stub
        BmobQuery<TiKuContent> tiKuContent = new BmobQuery<TiKuContent>();
        //按照时间降序
        tiKuContent.order("indexID");
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = tiKuContent.hasCachedResult(this,TiKuContent.class);
        if(isCache){
            if(ActivityUtil.hasNetwork(this)){
                tiKuContent.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则
            }else{
                //--此为举个例子，并不一定按这种方式来设置缓存策略
                tiKuContent.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            }
        }else{
            tiKuContent.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        tiKuContent.findObjects(this, new FindListener<TiKuContent>() {
            @Override
            public void onSuccess(List<TiKuContent> data) {
                // TODO Auto-generated method stub
                if (data != null) {
                    tiKuContentList.addAll(data);
                    problemLimit =tiKuContentList.size();
                    initSubject();
//                    for (int i= 0;i<tiKuContentList.size();i++) {
//                        Log.i("tiku", "" +i+tiKuContentList.isEmpty() + "/" + tiKuContentList.get(i).getTitleSubject());
//
//                    }
                }
            }
            @Override
            public void onError(int errorCode, String errorString) {
                // TODO Auto-generated method stub
            }
        });
    }
    private void initSubject() {
        //1、第一次加载、上一题、下一题、交卷后的时候调用此方法；
        //当题库数据不为空时才显示
        if (!tiKuContentList.isEmpty()) {
            //初始化单选框
            sc_ly.setVisibility(View.VISIBLE);
            ly_title.setVisibility(View.VISIBLE);

//             Log.i("mySelect[curIndex].zhi",mySelect[curIndex]);
            if (mySelect[curIndex].equals("")) {//如果为0则说明题目还没选好答案，就初始化单选框
                radioGroup.clearCheck();
                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
                cb4.setChecked(false);
            }
            tv_title.setText("\u0020" + tiKuContentList.get(curIndex).getTitleSubject());
            tv_record.setText((curIndex + 1) + "/"+tiKuContentList.size());
            //判断是否交卷，交卷则显示答案显示答案
            answer_tv.setText(tiKuContentList.get(curIndex).getAnswer());
            core_tv.setText(tiKuContentList.get(curIndex).getCore());
//            answer_tv.setTextColor(Color.RED);
            tv_explain.setText(tiKuContentList.get(curIndex).getExplain());
            //0单选，1多选，2判断，3填空，4问答
//            多选
            if (tiKuContentList.get(curIndex).getSubjectType() == 0) {
                // 单选择题
                tv_type.setText("--单选题（本题1分，共50题）");
                ToastUtil.showToast(getApplicationContext(), "单选题" + tiKuContentList.get(curIndex).getSubjectType());
                radioA.setText("A." + tiKuContentList.get(curIndex).getOptionA());
                radioB.setText("B." + tiKuContentList.get(curIndex).getOptionB());
                radioC.setText("C." + tiKuContentList.get(curIndex).getOptionC());
                radioD.setText("D." + tiKuContentList.get(curIndex).getOptionD());
                radioA.setVisibility(View.VISIBLE);
                radioB.setVisibility(View.VISIBLE);
                radioC.setVisibility(View.VISIBLE);
                radioD.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
                cb1.setVisibility(View.GONE);
                cb3.setVisibility(View.GONE);
                cb4.setVisibility(View.GONE);
                cb2.setVisibility(View.GONE);
                btn_submit.setVisibility(View.GONE);
                ly_edt.setVisibility(View.GONE);
            } else if (tiKuContentList.get(curIndex).getSubjectType() == 1) {
                tv_type.setText("--多选题（本题1分，共10题）");
                ToastUtil.showToast(getApplicationContext(), "多选题" + tiKuContentList.get(curIndex).getSubjectType());
                cb1.setText("A." + tiKuContentList.get(curIndex).getOptionA());
                cb2.setText("B." + tiKuContentList.get(curIndex).getOptionB());
                cb3.setText("C." + tiKuContentList.get(curIndex).getOptionC());
                cb4.setText("D." + tiKuContentList.get(curIndex).getOptionD());
                cb1.setVisibility(View.VISIBLE);
                cb2.setVisibility(View.VISIBLE);
                cb3.setVisibility(View.VISIBLE);
                cb4.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.VISIBLE);
                radioA.setVisibility(View.GONE);
                radioB.setVisibility(View.GONE);
                radioC.setVisibility(View.GONE);
                radioD.setVisibility(View.GONE);
                radioGroup.setVisibility(View.GONE);
                ly_edt.setVisibility(View.GONE);
                //判断
            } else if (tiKuContentList.get(curIndex).getSubjectType() == 2) {
                tv_type.setText("--判断题（本题1分，共20题）");
                ToastUtil.showToast(getApplicationContext(), "判断题" + tiKuContentList.get(curIndex).getSubjectType());
                radioA.setText("对");
                radioB.setText("错");
                radioGroup.setVisibility(View.VISIBLE);
                radioA.setVisibility(View.VISIBLE);
                radioB.setVisibility(View.VISIBLE);
                radioC.setVisibility(View.GONE);
                radioD.setVisibility(View.GONE);
                cb1.setVisibility(View.GONE);
                cb2.setVisibility(View.GONE);
                cb3.setVisibility(View.GONE);
                cb4.setVisibility(View.GONE);
                btn_submit.setVisibility(View.GONE);
                ly_edt.setVisibility(View.GONE);
            } else if (tiKuContentList.get(curIndex).getSubjectType() == 3) {
                ly_edt.setVisibility(View.VISIBLE);
                tv_type.setText("--填空题（本题1分，共20题）");
                radioA.setVisibility(View.GONE);
                radioB.setVisibility(View.GONE);
                radioC.setVisibility(View.GONE);
                radioD.setVisibility(View.GONE);
                radioGroup.setVisibility(View.GONE);
                cb1.setVisibility(View.GONE);
                cb2.setVisibility(View.GONE);
                cb3.setVisibility(View.GONE);
                cb4.setVisibility(View.GONE);
                btn_submit.setVisibility(View.GONE);
                // 填空题
                 ToastUtil.showToast(getApplicationContext(),"填空题"+tiKuContentList.get(curIndex).getSubjectType());
            }
            //初始化已做的题目
//            Log.i("mySelect[curIndex]的值是",""+mySelect[curIndex]);
            switch (mySelect[curIndex]) {
                case "1":
                    radioA.setChecked(true);
                    break;
                case "2":
                    radioB.setChecked(true);
                    break;
                case "3":
                    radioC.setChecked(true);
                    break;
                case "4":
                    radioD.setChecked(true);
                    break;
                case "12":
                    cb1.setChecked(true);
                    cb2.setChecked(true);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                    break;
                case "13":
                    cb1.setChecked(true);
                    cb3.setChecked(true);
                    cb2.setChecked(false);
                    cb4.setChecked(false);
                    break;
                case "14":
                    cb1.setChecked(true);
                    cb4.setChecked(true);
                    cb3.setChecked(false);
                    cb2.setChecked(false);
                    break;
                case "1234":
                    cb1.setChecked(true);
                    cb2.setChecked(true);
                    cb3.setChecked(true);
                    cb4.setChecked(true);
                    break;
                case "123":
                    cb1.setChecked(true);
                    cb2.setChecked(true);
                    cb3.setChecked(true);
                    cb4.setChecked(false);
                    break;
                case "124":
                    cb1.setChecked(true);
                    cb2.setChecked(true);
                    cb3.setChecked(false);
                    cb4.setChecked(true);
                    break;
                case "134":
                    cb1.setChecked(true);
                    cb3.setChecked(true);
                    cb2.setChecked(false);
                    cb4.setChecked(true);
                    break;
                case "24":
                    cb2.setChecked(true);
                    cb1.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(true);
                    break;
                case "23":
                    cb2.setChecked(true);
                    cb3.setChecked(true);
                    cb1.setChecked(false);
                    cb4.setChecked(false);
                    break;
                case "34":
                    cb4.setChecked(true);
                    cb3.setChecked(true);
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    break;
                default:
                    det_answer_input.setText(mySelect[curIndex]);
                    break;
            }
        }else{
            //数据没加载好的时显示的内容
            ly_title.setVisibility(View.GONE);
            tv_title.setText("单项选择题、多项选择题、填空题、判断题");
            radioA.setVisibility(View.GONE);
            radioB.setVisibility(View.GONE);
            radioC.setVisibility(View.GONE);
            radioD.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
            cb1.setVisibility(View.GONE);
            cb2.setVisibility(View.GONE);
            cb3.setVisibility(View.GONE);
            cb4.setVisibility(View.GONE);

        }
    }
    @Override
    protected void onDestroy() {
        // 保存错题库
        saveWaset();
        super.onDestroy();
    }
}

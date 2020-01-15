package com.sunsty.alidd.view.activity.ocr;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.msd.ocr.idcard.LibraryInitOCR;
import com.sunsty.alidd.R;
import com.sunsty.alidd.view.activity.ocr.permissions.EasyPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements EasyPermission.PermissionCallback {
    private Context context;
    private String broadcastAction = "com.idCard.result";
    private Button button;
    private Button button2;
    private TextView textView;
    private TextView tip;
    private Spinner type;
    private Spinner saveImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ocr);
        context = this;
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        textView = findViewById(R.id.textview);
        tip = findViewById(R.id.tip);
        type = findViewById(R.id.type);
        saveImage = findViewById(R.id.saveImage);

        LibraryInitOCR.initOCR(context);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("saveImage", saveImage.getSelectedItemPosition() == 0 ? true : false); // 是否保存识别图片
                bundle.putBoolean("showSelect", true);                          // 是否显示选择图片
                bundle.putBoolean("showCamera", true);                          // 显示图片界面是否显示拍照(驾照选择图片识别率比扫描高)
                bundle.putInt("requestCode", REQUEST_CODE);                     // requestCode
                bundle.putInt("type", type.getSelectedItemPosition());  // 0身份证, 1驾驶证

                //broadcastAction 将扫描结果广播出去, 注意增加 intent.addCategory(context.getPackageName());
                //如果不需要广播,就不会传这个参数
                bundle.putString("broadcastAction", broadcastAction);
                LibraryInitOCR.startScan(context, bundle);


                //如果您不想集成aar, 那么可以通过隐式意图拉起示例中的扫描界面

                /*
                //身份证:com.msd.ocr.idcard.ICVideo, 驾驶证: com.msd.ocr.idcard.id.DIVideoActivity
                Intent intent = new Intent("com.msd.ocr.idcard.ICVideo");
                intent.putExtra("bundle", bundle);                         //具体参数如上
                intent.addCategory(getPackageName());                      //调用demo中的扫描界面使用: com.tomcat.ocr.idcard
                startActivityForResult(intent, REQUEST_CODE);
                 */
            }
        });



        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tip.setVisibility(View.GONE);
                return true;
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SimpleCameraActivity.class));
            }
        });


        //扫描之前,提前申请权限..
        requestPermission();



        //注册扫描广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(broadcastAction);
        intentFilter.addCategory(getPackageName());
        resultReceiver = new ResultReceiver();
        registerReceiver(resultReceiver, intentFilter);
    }

    private ResultReceiver resultReceiver;
    private class ResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(broadcastAction.equals(intent.getAction())){
                String result = intent.getStringExtra("OCRResult");
                Toast.makeText(context, "从广播中接收到数据: " + result, Toast.LENGTH_LONG).show();
            }
        }
    }


    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private boolean isHavePermission = true;
    private void requestPermission(){
        if(EasyPermission.hasPermissions(context, permissions)){
            isHavePermission = true;
        }else{
            EasyPermission.with(this)
                    .rationale(getString(com.msd.ocr.idcard.R.string.rationale_camera))
                    .addRequestCode(REQUEST_PERMISS)
                    .permissions(permissions)
                    .request();
        }
    }

    private final int REQUEST_CODE = 1;
    private final int REQUEST_PERMISS = 2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            String result = data.getStringExtra("OCRResult");
            try {
                JSONObject jo = new JSONObject(result);
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("正面 = %s\n", jo.opt("type")));
                sb.append(String.format("姓名 = %s\n", jo.opt("name")));
                sb.append(String.format("性别 = %s\n", jo.opt("sex")));
                sb.append(String.format("民族 = %s\n", jo.opt("folk")));
                sb.append(String.format("日期 = %s\n", jo.opt("birt")));
                sb.append(String.format("号码 = %s\n", jo.opt("num")));
                sb.append(String.format("住址 = %s\n", jo.opt("addr")));
                sb.append(String.format("签发机关 = %s\n", jo.opt("issue")));
                sb.append(String.format("有效期限 = %s\n", jo.opt("valid")));
                sb.append(String.format("整体照片 = %s\n", jo.opt("imgPath")));
                sb.append(String.format("头像路径 = %s\n", jo.opt("headPath")));
                sb.append("\n驾照专属字段\n");
                sb.append(String.format("国家 = %s\n", jo.opt("nation")));
                sb.append(String.format("初始领证 = %s\n", jo.opt("startTime")));
                sb.append(String.format("准驾车型 = %s\n", jo.opt("drivingType")));
                sb.append(String.format("有效期限 = %s\n", jo.opt("registerDate")));
                textView.setText(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        isHavePermission = true;
    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        Toast.makeText(context, "没有相机权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(resultReceiver); //记得要注销
    }
}

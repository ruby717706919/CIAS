/*package com.example.testit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testit.util.LoginUtil;
import com.example.testit.util.sqlConnect;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText username;
    private EditText userpassword;
    private LoginUtil loginUtil=new LoginUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin= this.<Button>findViewById(R.id.btn_Login);
        username= this.<EditText>findViewById(R.id.userName);
        userpassword= this.<EditText>findViewById(R.id.userPassword);
        //final sqlConnect sc=new sqlConnect();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un=username.getText().toString(),pw=userpassword.getText().toString();
                if (loginUtil.login(un,pw)) {
                   // Intent intent=new Intent(LoginActivity.this,UserActivity.class);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this,"登录失败，请检查用户名与密码！",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}*/
package com.example.testit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.os.*;
//import com.example.testit.util.LoginUtil;
//import java.lang.ref.WeakReference;
import java.io.*;
import java.net.*;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText username;
    private EditText userpassword;
    private static Thread t = null;
    //    private MyHandler myhandler = new MyHandler(this);
    //private LoginUtil loginUtil=new LoginUtil();
    static boolean match = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = this.<Button>findViewById(R.id.btn_Login);
        username = this.<EditText>findViewById(R.id.userName);
        userpassword = this.<EditText>findViewById(R.id.userPassword);
        InitView();//执行
    }

    void InitView() {
        //登录按钮响应函数
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("test");
                //获取文本框内容
                final String uname = username.getText().toString();
                final String psd = userpassword.getText().toString();

                //判断非空
                if (uname.isEmpty() || psd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "学号或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //开启访问数据库线程
                    t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println(uname);
                            //System.out.println(psd);
                            //Url方式连接，设置Url
                            String fPath = String.format("http://citeam.chinaeast.cloudapp.chinacloudapi.cn:8888/WebServer/login?username=%s&password=%s",uname,psd);
                            //System.out.println(fPath);
                            //String path = "http://citeam.chinaeast.cloudapp.chinacloudapi.cn:8888/WebServer/login?username=" + uname + "&password=" + psd;

                            try {
                                URL url = new URL(fPath);
                                //建立连接
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                //设置get方式
                                connection.setRequestMethod("GET");
                                //设置超时时间
                                connection.setReadTimeout(8000);
                                connection.setConnectTimeout(8000);
                                //设置输入流对象
                                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                                BufferedReader reader = new BufferedReader(in);
                                //匹配结果保存
                                String result = "";
                                String readLine = null;
                                //读取服务器数据
                                while ((readLine = reader.readLine()) != null) {
                                    result += readLine;
                                }
                                //System.out.println(result);
                                //关闭输入流
                                in.close();
                                //断开连接
                                connection.disconnect();

                                //判断账号密码配对是否成功
                                if (result.equals("Successful!"))
                                    match = true;
                                System.out.println(result + "  " + match);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                                System.out.println("error");
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("error");
                            }
                        }
                    });
                    t.start();
                }

                try {
                    //等待线程执行完毕
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Android中判断是否跳转
                if(match){
                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    startActivity(intent);
                }else{
                    //失败
                    Toast.makeText(LoginActivity.this, "登录失败，请检查用户名与密码！", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

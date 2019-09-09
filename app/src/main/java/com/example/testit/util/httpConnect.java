package com.example.testit.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class httpConnect {
    private Thread t=null;
    private boolean match;
    private String fPath;
    private String defaultPathSet;

    private String username;
    public httpConnect(){
        defaultPathSet = "http://citeam.chinaeast.cloudapp.chinacloudapi.cn:8888/WebServer/login?username=";
    }

    public boolean login(String uname,String psd){
        //System.out.println("test");
        match=false;
        username=uname;
        fPath = String.format(defaultPathSet+"%s&password=%s",username,psd);

        resetT();
        return match;
    }

    public boolean att(){
        match=false;
        fPath = String.format(defaultPathSet+"%s&symbol=%s",username,"start");
        resetT();

        return match;
    }

    private void resetT(){
        t=new Thread(new Runnable() {
            @Override
            public void run() {
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
        try {
            //等待线程执行完毕
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

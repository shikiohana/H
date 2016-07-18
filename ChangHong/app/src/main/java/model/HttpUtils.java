package model;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Http请求的工具类
 *
 * @author awx
 */
public class HttpUtils {

    private static int SOCKET_TIME_OUT = 20000;
    public final static String HTTP_GET_USERS = "http://192.168.11.237/index.php/Home/Api/getusers";
    public final static String HTTP_GET_DATES = "http://192.168.11.237/index.php/Home/Api/dateinfo";

    private JSONObject jsonResult;
    private HttpClient httpClient;
    private HttpResponse httpResponse;
    private HttpGet httpGet;
    private StringBuilder Url, UrlParam;// URL地址 拼接参数 返回结果
    private String result;
    private BasicHttpParams basicHttpParams;

    private final int TIMEOUT_IN_MILLIONS = 5000;//连接时间

    private static HttpUtils httpUtils = new HttpUtils();

    /**
     * 单例饿汉式
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    /**
     * 回调
     */
    public interface CallBack {
        void onRequestComplete(String result);
    }


   /* *//**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     *//*
    public void doGetAsyn(final String urlStr, final CallBack callBack) {
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }
*/

    /**
     * 异步的Post请求
     *
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public void doPostAsyn(final String urlStr, final String params, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public String doPost(String url, String param) {
        PrintWriter out = null;
        StringBuilder stringBuilder = new StringBuilder();//初始化StringBuilder对象
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.connect();//建立连接
            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            //获取输入流，得到响应内容
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));//使用BuffereadReader读取接收到的数据
                String line = bufferedReader.readLine();
                while (line != null && line.length() > 0) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                inputStream.close();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            if (out != null) {
                out.close();
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 通过client response用get方法访问获取json数据,注意自己开启异步使用此方法
     *
     * @param httpUrl 接口的URL地址 例如 http://192.168.11.237/index.php/Home/Api/getusers
     * @param params  访问接口的参数名和对应值
     * @return JSONObject的数据
     */
    public String doHttpGet(String httpUrl, HashMap<String, String> params, HashMap<String, String> headers) {
        UrlParam = new StringBuilder();
        Url = new StringBuilder();
        result = null;
        basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, TIMEOUT_IN_MILLIONS);// 设置连接超时
        HttpConnectionParams.setSoTimeout(basicHttpParams, SOCKET_TIME_OUT);// 设置socket时间
        // 创建一个httpClient实例
        httpClient = new DefaultHttpClient(basicHttpParams);
        if (params != null && !params.isEmpty()) {// 如果有参数
            for (String key : params.keySet()) {
                // 拼接参数语句
                UrlParam.append("&" + encodeUTF8(key) + "=" + encodeUTF8(params.get(key)));
            }
            UrlParam.replace(0, 1, "?");

            Url.append(httpUrl + UrlParam.toString());
             Log.i("URL", "" + UrlParam.toString());
        } else {
            Url.append(httpUrl);
             Log.i("URL", "" + httpUrl);
        }

        try {

            httpGet = new HttpGet(Url.toString());// 实例化httpGet对象
            Log.i("URLnoParam", "" + Url.toString());
            if (headers != null && !headers.isEmpty()) {
                for (String headerKey : headers.keySet()) {
                    httpGet.addHeader(headerKey, headers.get(headerKey));

                }
            } else {
                httpGet.setHeader("accept", "*/*");// 设置header
            }
            httpResponse = httpClient.execute(httpGet);// 执行连接
             Log.i("Result",httpResponse.getStatusLine().getStatusCode()+"");
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");// 将返回数据以utf-8编码成字符串

                Log.i("Result", result);
                jsonResult = new JSONObject(result);
                // 转化为json并返回
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /*
     * 转译字符串
     */
    private String encodeUTF8(String str) {
        try {
            str = URLEncoder.encode(str, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str;
    }


    /**
     * 异步的Post请求
     *
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public void doPostUpLoadAsyn(final String urlStr, final HashMap<String, String> params, final String filePath, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = upLoadImg(urlStr, params, filePath);

                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    String boundary = "******";
    String end = "\r\n";
    String twoHyphens = "--";

    /**
     * @param httpurl     上传的post地址
     * @param keyAndValue 文字数据
     * @param filePath    图片路径
     * @return
     */
    public String upLoadImg(String httpurl, HashMap<String, String> keyAndValue, String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader in = null;
        if (httpurl != null && filePath != null) {
            try {
                URL url = new URL(httpurl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(10000);// 设置超时
                con.setRequestMethod("POST");// post方法
                con.setUseCaches(false);// 不适用缓存
                con.setDoOutput(true);// 允许输出
                con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);// 构建表单
                con.setRequestProperty("charset", "utf-8");
                con.connect();
                DataOutputStream ds = new DataOutputStream(con.getOutputStream());
                createTable(ds, keyAndValue);
                ds.writeBytes(twoHyphens + boundary + end);
                // 编辑文件信息
                Date date = new Date();
                ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + "\";filename=\"" + date.toString() + ".jpg" + "\"" + end);
                ds.writeBytes(end);
                FileInputStream inputStream = new FileInputStream(new File(filePath));
                byte[] bytes = new byte[1024];
                int length = -1;
                while ((length = inputStream.read(bytes)) != -1) {
                    ds.write(bytes, 0, length);
                    ds.flush();
                }
                ds.writeBytes(end);
                ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
                ds.flush();
                ds.close();
                inputStream.close();
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                int statusCode = con.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    char[] buf = new char[1024];
                    int len = -1;
                    while ((len = in.read(buf, 0, buf.length)) != -1) {
                        stringBuilder.append(buf, 0, len);
                    }
                }
                in.close();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private void createTable(DataOutputStream ds, HashMap<String, String> map) {
        if (ds != null && map != null && map.size() > 0) {
            Set<Map.Entry<String, String>> paramEntrySet = map.entrySet();
            Iterator paramIterator = paramEntrySet.iterator();
            while (paramIterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) paramIterator.next();

                String key = entry.getKey();
                String value = entry.getValue();

                try {
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; " + "name=\"" + key + "\"" + end + end + value);

                    ds.writeBytes(end);

                    ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

                    ds.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }

    }


    /**
     * 无文件post
     *
     * @param httpUrl
     * @param map
     * @return 返回的json result
     */
    public String postData(String httpUrl, Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader in = null;
        String result = "";
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000); // 杩炴帴瓒呮椂涓�10绉�
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);// 璁剧疆璇锋眰鏁版嵁绫诲瀷骞惰缃産oundary閮ㄥ垎锛�
            connection.connect();
            DataOutputStream ds = new DataOutputStream(
                    connection.getOutputStream());

            Set<Map.Entry<String, String>> paramEntrySet = map.entrySet();
            Iterator paramIterator = paramEntrySet.iterator();
            while (paramIterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) paramIterator
                        .next();
                String key = entry.getKey();
                String value = entry.getValue();
                ds.writeBytes(twoHyphens + boundary + end);
                // ds.writeBytes("Content-Disposition: form-data; " + "name=\""
                // + key + "\"" + end + end + value + end+ boundary + end);
                ds.writeBytes("Content-Disposition: form-data; " + "name=\""
                        + key + "\"" + end + end + value);
                ds.writeBytes(end);
                ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
                ds.flush();
            }
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"file"
                    + "\";filename=\"" + "image1.png" + "\"" + end);
            ds.writeBytes(end);


            // 瀹氫箟BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴�
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                char[] buf = new char[1024];
                int len = -1;
                while ((len = in.read(buf, 0, buf.length)) != -1) {
                    stringBuilder.append(buf, 0, len);
                }
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.i("Post", "result===========>" + stringBuilder.toString());

        return stringBuilder.toString();
    }


    /**
     * 无文件post
     *
     * @param httpUrl
     * @param json
     * @return 返回的json result
     */
    public String postJsonData(String httpUrl, String json) {

        String returnLine = "";
        try {

            System.out.println("**************开始http通讯**************");
            System.out.println("**************调用的接口地址为**************" + httpUrl);
            System.out.println("**************请求发送的数据为**************" + json);
            URL my_url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
            connection.setDoOutput(true);

            connection.setDoInput(true);

            connection.setRequestMethod("POST");

            connection.setUseCaches(false);

            connection.setInstanceFollowRedirects(true);

            connection.setRequestProperty("Content-Type", "application/json");

            connection.connect();
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());

            byte[] content = json.getBytes("utf-8");

            out.write(content, 0, content.length);
            out.flush();
            out.close(); // flush and close

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            //StringBuilder builder = new StringBuilder();

            String line = "";

            System.out.println("Contents of post request start");

            while ((line = reader.readLine()) != null) {
                // line = new String(line.getBytes(), "utf-8");
                returnLine += line;

                System.out.println(line);

            }

            System.out.println("Contents of post request ends");

            reader.close();
            connection.disconnect();
            System.out.println("========返回的结果的为========" + returnLine);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnLine;


    }



    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "801c5e1aca87ed2ba21840465887ae68");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

import java.io.IOException;  
import java.util.ArrayList;  
import java.util.List;  
  
import net.sf.json.JSONObject;  
  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
public class TestHttp {
	 /** 
     * @param args 
     */  
    public static void main(String[] args) {  
      
        String uid="北京";  
        String title="json";  
        String content="W69oaDTCfuGwzNwmtVvgWfGH";  
        String ret=sendSms(uid ,title,content);  
        System.out.println(ret);  
  
        if(ret.indexOf("失败")<0)  
        {  
            System.out.println("成功发送sms");  
        }  
        else  
        {  
            System.out.println("失败发送");  
        }  
  
    }  
      
      
  
    public static String sendSms(String uid,String title,String content){  
        HttpClient httpclient = new DefaultHttpClient();  
        String smsUrl="http://127.0.0.1:8080/Test/servlet/ServletDemo1";  
        HttpPost httppost = new HttpPost(smsUrl);  
        String strResult = "";  
          
        try {  
              
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
                JSONObject jobj = new JSONObject();  
                jobj.put("北京", uid);  
                jobj.put("output", title);  
                jobj.put("ak",content);  
                  
                  
                nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));  
                httppost.addHeader("Content-type", "application/jsonp");  
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));  
             
                HttpResponse response = httpclient.execute(httppost);  
                if (response.getStatusLine().getStatusCode() == 200) {  
                    /*读返回数据*/  
                    String conResult = EntityUtils.toString(response  
                            .getEntity());  
                    JSONObject sobj = new JSONObject();  
                    sobj = sobj.fromObject(conResult);  
                    String result = sobj.getString("result");  
                    String code = sobj.getString("code");  
                    if(result.equals("1")){  
                        strResult += "发送成功";  
                    }else{  
                        strResult += "发送失败，"+code;  
                    }  
                      
                } else {  
                    String err = response.getStatusLine().getStatusCode()+"";  
                    strResult += "发送失败:"+err;  
                }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return strResult;  
    }  
  
      
    private static String getStringFromJson(JSONObject adata) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("{");  
        for(Object key:adata.keySet()){  
            sb.append("\""+key+"\":\""+adata.get(key)+"\",");  
        }  
        String rtn = sb.toString().substring(0, sb.toString().length()-1)+"}";  
        return rtn;  
    }  
}

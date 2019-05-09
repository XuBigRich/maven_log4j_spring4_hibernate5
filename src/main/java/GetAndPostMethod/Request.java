package GetAndPostMethod;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Request.java
 * @Description TODO
 * @createTime 2019年03月30日 07:36:00
 */
public class Request {
    public final static String URL="127.0.0.1";
    public String doGetResult(Map<String, String> params)throws Exception {
        PostMethod getMethod = new PostMethod(URL);
        getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");// 对含有中文的字符进行编码
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        for (String nameKey: params.keySet()) {
            nameValuePairList.add(new NameValuePair(nameKey, params.get(nameKey))); //构造参数
        }
//        getMethod.setQueryString((NameValuePair[])nameValuePairList.toArray());
        getMethod.setQueryString(nameValuePairList.toArray(new NameValuePair[nameValuePairList.size()]));
        System.out.println(getMethod);
        return null;
    }
    public static void main(String[] args) throws Exception {
        Map<String,String> map=new HashMap();
        map.put("name","许鸿志");
        map.put("sex","男");
        Request request=new Request();
        request.doGetResult(map);
    	}
}

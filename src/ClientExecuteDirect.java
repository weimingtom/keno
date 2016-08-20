import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ClientExecuteDirect {

    public static void main(String[] args) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpHost target = new HttpHost("www.gdfc.org.cn", 80, "http");
            HttpGet req = new HttpGet("/datas/history/keno/history_787.html");
            System.out.println("executing request to " + target);
            HttpResponse rsp = httpclient.execute(target, req);
            HttpEntity entity = rsp.getEntity();
            System.out.println("----------------------------------------");
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

}

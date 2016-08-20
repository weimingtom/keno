import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Main {
    public final static void main(String[] args) {
	System.out.println("Start parsing...");
	FileWriter writer = null;
	try {
	    writer = new FileWriter("output.txt");
	    for (int page = 1; page < 788; page++) {
		try {
		    System.out.println("=>Start parsing page " + page + "...");
		    ripString(page, true, false, writer);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	} catch (IOException e2) {
	    e2.printStackTrace();
	} finally {
	    if (writer != null) {
		try {
		    writer.close();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
	    }
	}
    }

    public final static void ripString(int page, boolean isLocalFile,
	    boolean isVerbose, FileWriter writer)
	    throws ClientProtocolException, IOException {
	String text = null;
	if (isLocalFile) {
	    text = getFileText("pages/history_" + page + ".html");
	} else {
	    text = getWebText("www.gdfc.org.cn", "/datas/history/keno/history_"
		    + page + ".html");
	}
	// System.out.println(text);

	Pattern pattern = Pattern.compile(
		">([0-9]*)</td>.*\\r*\\n.*luckyNo=\"([0-9]*)\"",
		Pattern.MULTILINE);
	Matcher matcher = pattern.matcher(text);

	while (matcher.find()) {
	    String str = matcher.group(1) + ", " + matcher.group(2);
	    if (isVerbose) {
		System.out.println(str);
	    }
	    writer.write(str + "\n");
	}
    }

    public final static String getFileText(String filename) throws IOException {
	FileInputStream reader = new FileInputStream(filename);
	byte[] content = new byte[reader.available()];
	reader.read(content, 0, content.length);
	return new String(content, "gb2312");
    }

    public final static String getWebText(String site, String uri)
	    throws ClientProtocolException, IOException {
	DefaultHttpClient httpclient = new DefaultHttpClient();
	String text = null;
	try {
	    HttpHost target = new HttpHost(site, 80, "http");
	    HttpGet req = new HttpGet(uri);
	    HttpResponse rsp = httpclient.execute(target, req);
	    HttpEntity entity = rsp.getEntity();
	    if (entity != null) {
		text = EntityUtils.toString(entity);
	    }
	} finally {
	    httpclient.getConnectionManager().shutdown();
	}
	return text;
    }
}

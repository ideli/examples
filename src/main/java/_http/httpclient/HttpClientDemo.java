package _http.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import _encrypt.MD5Encrypter;


public class HttpClientDemo {

	public void sendGet() {
		int iGameId = 1000;
		String strGameKey = "38adfb4ff9d6f2c76184f752cd588531";

		String strUrl = "http://billingtest.locojoy.com/api/open/VerifySession.ashx";
		int iServerId = 10100;

		int iSelfChannel = 200100000;
		String strChildChannel = "";
		String strSession = "ewogICJhdmF0YXIiIDogIiIsCiAgImNoYW5uZWwiIDogImxvY29qb3kiLAogICJwbGF0Zm9ybWlkIiA6ICIyMTE2NjMxNjAiLAogICJuaWNrIiA6ICIiLAogICJsb2dpbnRpbWUiIDogIjIwMTUtMDgtMTcgMTE6MzY6MDgiLAogICJjcGluZm8iIDogIiIsCiAgInNpZ24iIDogImExZWJkMzgxMmFmZjA3YjM5NTE2NTM1ODM3MjAxNzM4IiwKICAic2V4IiA6ICIiLAogICJhcHBpZCIgOiAiMTAwMDQiLAogICJuYW1lIiA6ICIiCn0=";
		String strIp = "192.168.1.1";
		String strDeviceId = "88:e3:ab:41:72:01";
		long lRoleId = 1010023678;

		// MD5(GameKey+GameId+Session+ServerId+SelfChannel+ChildChannel+Ip+DeviceId)
		String strPrepare = strGameKey + iGameId + strSession + iServerId
				+ iSelfChannel + strChildChannel + strIp + strDeviceId;
		String strSign = MD5Encrypter.MD5(strPrepare);

		List<NameValuePair> listParams = new ArrayList<NameValuePair>();
		listParams.add(new BasicNameValuePair("GameId", iGameId + ""));
		listParams.add(new BasicNameValuePair("Session", strSession));
		listParams.add(new BasicNameValuePair("ServerId", iServerId + ""));
		listParams.add(new BasicNameValuePair("SelfChannel", iSelfChannel + ""));
		listParams.add(new BasicNameValuePair("ChildChannel", strChildChannel));
		listParams.add(new BasicNameValuePair("Ip", strIp));
		listParams.add(new BasicNameValuePair("DeviceId", strDeviceId));
		listParams.add(new BasicNameValuePair("Sign", strSign));
		listParams.add(new BasicNameValuePair("RoleId", lRoleId + ""));

		// String strParams =
		// MessageFormat.format("?GameId={0}&Session={1}&ServerId={2}&SelfChannel={3}&ChildChannel={4}&Ip={5}&DeviceId={6}&Sign={7}&RoleId={8}",
		// iGameId, strSession, iServerId, iSelfChannel, strChildChannel, strIp,
		// strDeviceId, strSign, lRoleId);
		// strUrl += strParams;

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet getMethod = new HttpGet(strUrl);
		String strParams;
		CloseableHttpResponse response = null;
		try {
			strParams = EntityUtils.toString(new UrlEncodedFormEntity(
					listParams));
			getMethod.setURI(new URI(getMethod.getURI().toString() + "?"
					+ strParams));
			response = httpclient.execute(getMethod);
			HttpEntity entity = response.getEntity();
			String strJson = EntityUtils.toString(entity);
			System.out.println(strJson);
			EntityUtils.consume(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

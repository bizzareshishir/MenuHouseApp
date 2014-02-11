package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class WebServiceUtil {
	public static String SUCCESS = "SUCCESS";
	public static String FAILURE = "FAILURE";

	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				is.close();

			} catch (IOException e) {

				e.printStackTrace();

			}
		}
		return sb.toString();
	}

	public static String getPostResponce(List<NameValuePair> nameValuePair,
			String Url) throws Exception {
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(Url);

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}

		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();

				String resp = WebServiceUtil.convertStreamToString(instream);

				// Closing the input stream will trigger connection release

				instream.close();

				return resp;
			}

			// writing response to log

		} catch (ClientProtocolException e) {
			// writing exception to log

			e.printStackTrace();
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();

		}

		return null;
	}

	public static String getResponse(String url) {
		// TODO Auto-generated method stub
		HttpResponse response = null;
		String respString = "";
		try {

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			Log.e("newlog", url);
			request.setURI(new URI(url));

			response = client.execute(request);

			respString = WebServiceUtil.convertStreamToString(response
					.getEntity().getContent());

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return respString;

	}
}

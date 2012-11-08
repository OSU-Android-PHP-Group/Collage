package com.unitedware.collage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import android.util.Log;

public class ServerClient {
	private static String collageServerURI = "http://collage.united-ware.com/api/image";
	private static String TAG = "POST_COLLAGE";

	private ServerClient() {
	}

	public static InputStream postCollage(Collage coll) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);

		HttpPost post = new HttpPost(ServerClient.collageServerURI);
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		for (File img : coll.getImages()) {
			entity.addPart("images[]", new FileBody(img,
					"application/octet-stream"));
		}

		post.setEntity(entity);
		InputStream result = null;
		try {
			client.execute(post);
			HttpResponse response = client.execute(post);
			HttpEntity respEntity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				result = respEntity.getContent();
			} else {
				ServerClient.handleError(respEntity);
				result =  null;
			}
		} catch (IOException ioe) {
			Log.e(ServerClient.TAG, "Error with request/response: " + ioe.getMessage());
		}
		return result;
	}

	/**
	 * Helper method called when the server's response is an error. Takes the
	 * entity of the response and attempts to display an error message using the
	 * content of the server's response.
	 *
	 * @param respEntity
	 *            The body of the server's response which has an error code that
	 *            is not 200.
	 */
	private static void handleError(HttpEntity respEntity) {
		if (respEntity.getContentType().equals("text/html")) {
			ByteArrayOutputStream respBodyStream = new ByteArrayOutputStream();
			try {
				respEntity.writeTo(respBodyStream);
				String error = respBodyStream.toString(respEntity
						.getContentEncoding().getValue());
				Log.e(ServerClient.TAG, "Received error from server: " + error);
			} catch (IOException e) {
				Log.e(ServerClient.TAG,
						"Could not parse body on error response: "
								+ e.getMessage() + "\n"
								+ Log.getStackTraceString(e));
			}
		} else {
			Log.e(ServerClient.TAG,
					"Bad response from server. Status code greater than 400 without an error message.");
		}
	}
}

package com.unitedware.collage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

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
	private static final String COLLAGE_SERVER_URL = "http://collage.united-ware.com/api/image";
	private static final String TAG = "POST_COLLAGE";

	private ServerClient() {
	}

	/**
	 * Construct an HTTP POST request containing images of the given collage.
	 *
	 * @param coll
	 *            Local representation of the collage to be generated.
	 * @return The multipart post containing images to be turned into a collage.
	 */
	private static HttpPost constructPost(Collage coll) {
		// Construct the POST request
		HttpPost post = new HttpPost(ServerClient.COLLAGE_SERVER_URL);
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		for (File img : coll.getImages()) {
			String contentType = URLConnection.guessContentTypeFromName(img
					.getName());
			entity.addPart("images[]", new FileBody(img, contentType));
		}
		post.setEntity(entity);
		return post;
	}

	/**
	 * Execute the given post and handle the response nicely.
	 *
	 * @param post
	 *            A POST request containing several images to turn into a
	 *            collage.
	 * @return The content of the server's response (an image) if successful
	 *         else null.
	 */
	private static InputStream execute(HttpPost post) {
		// Create an HttpClient
		HttpClient http = new DefaultHttpClient();
		http.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);

		// Create the return value
		InputStream result = null;
		try {
			HttpResponse response = http.execute(post);
			HttpEntity respEntity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				// A successful request means overrides result with the
				// response's content.
				result = respEntity.getContent();
			} else {
				ServerClient.handleError(respEntity);
				result = null;
			}
		} catch (IOException ioe) {
			Log.e(ServerClient.TAG,
					"Error with request/response: " + ioe.getMessage());
		}
		return result;
	}

	/**
	 * Construct an HTTP POST request containing images of the given collage.
	 *
	 * @param coll
	 *            Local representation of the collage to be generated.
	 * @return The content of the server's response (an image) if successful
	 *         else null.
	 */
	public static InputStream postCollage(Collage coll) {
		HttpPost post = ServerClient.constructPost(coll);
		return ServerClient.execute(post);
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

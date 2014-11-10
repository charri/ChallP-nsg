package ch.hsr.nsg.themenrundgang.applicationService;

import java.io.IOException;

import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Subject;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class NsgApiServiceHttp implements NsgApi {

	private final OkHttpClient httpClient;
	private final Gson gson;
	
	private final static String BASE = "http://152.96.56.11/group3/json";
	
	// https://drive.google.com/file/d/0B2yjoamctmVAZ2taR3lJWU9aWGc/view
	
	public NsgApiServiceHttp() {
		httpClient = new OkHttpClient();
		gson = new Gson();
	}
	
	
	private <T> void enqueue(String url, final Class<T> classOfT, final ApiCallback<T> callback) {
				
		httpClient.newCall(httpRequest(url)).enqueue(new Callback() {

			@Override
			public void onFailure(Request request, IOException e) {
				callback.failure();
			}

			@Override
			public void onResponse(Response response) throws IOException {
				if(!response.isSuccessful()) throw new IOException(response.toString());
				
				callback.result(gson.fromJson(response.body().string(), classOfT));
			}
			
		});
	}
	
	private Request httpRequest(String url) {
		return new Request.Builder()
	      .url(url)
	      .addHeader("Accept", "application/json")
	      .build();
	}
	
	@Override
	public void getItems(ApiCallback<Item[]> callback) {
		
		if(callback == null) return;
		
		enqueue(BASE + "/items", Item[].class, callback);
	}

	@Override
	public void getSubjects(final ApiCallback<Subject[]> callback) {
		
		if(callback == null) return;
		
		enqueue(BASE + "/subjects", Subject[].class, callback);
	}

	@Override
	public void getBeacons(final ApiCallback<Beacon[]> callback) {
		if(callback == null) return;
		
		enqueue(BASE + "/beacons", Beacon[].class, callback);		
	}

	@Override
	public void getAdditions(final ApiCallback<Addition[]> callback) {
		
		if(callback == null) return;
		
		enqueue(BASE + "/additions", Addition[].class, callback);
	}

}

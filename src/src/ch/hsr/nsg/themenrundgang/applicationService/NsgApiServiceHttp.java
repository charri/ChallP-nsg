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
	
	private <T> T execute(String url, final Class<T> classOfT)  {
		Response response;
		try {
			response = httpClient.newCall(httpRequest(url)).execute();
			
			if(!response.isSuccessful()) return null;
			
			return gson.fromJson(response.body().string(), classOfT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private <T> void enqueue(String url, final Class<T> classOfT, final ApiCallback<T> callback) {
		
		httpClient.newCall(httpRequest(url)).enqueue(new Callback() {

			@Override
			public void onFailure(Request request, IOException e) {
				callback.failure();
			}

			@Override
			public void onResponse(Response response) throws IOException {
				if(!response.isSuccessful()) {
					callback.failure();
					throw new IOException(response.toString());
				}
				
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
	public void getItemsAsync(ApiCallback<Item[]> callback) {
		
		if(callback == null) return;
		
		enqueue(BASE + "/items", Item[].class, callback);
	}

	@Override
	public void getSubjectsAsync(final ApiCallback<Subject[]> callback) {
		
		if(callback == null) return;
		
		enqueue(BASE + "/subjects", Subject[].class, callback);
	}

	@Override
	public void getBeaconsAsync(final ApiCallback<Beacon[]> callback) {
		if(callback == null) return;
		
		enqueue(BASE + "/beacons", Beacon[].class, callback);		
	}

	@Override
	public void getAdditionsAsync(final ApiCallback<Addition[]> callback) {
		
		if(callback == null) return;
		
		enqueue(BASE + "/additions", Addition[].class, callback);
	}


	@Override
	public Item[] getItems() {
		
		return execute(BASE + "/items", Item[].class);
	}


	@Override
	public Subject[] getSubjects() {
		return execute(BASE + "/subjects", Subject[].class);
	}


	@Override
	public Beacon[] getBeacons() {
		return execute(BASE + "/beacons", Beacon[].class);
	}


	@Override
	public Addition[] getAdditions() {
		return execute(BASE + "/additions", Addition[].class);
	}

}

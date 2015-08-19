package com.pkesslas.esport42;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.pkesslas.esport42.helper.UserHelper;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Pierre-Elie on 24/02/15.
 */
public class Esport42 extends Application {

	private static Context context;
	private static RestAdapter restAdapter;
	private static UserHelper userHelper;

	@Override
	public void onCreate() {
		Esport42.context = this;
	}

	public static UserHelper userHelper() {
		if (userHelper != null) {
			return userHelper;
		}
		userHelper = new UserHelper(Esport42.context);
		return userHelper;
	}

	public static RestAdapter restAdapter() {
		if (restAdapter != null) {
			return restAdapter;
		}
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(BuildConfig.API_URL)
				.setRequestInterceptor(COOKIES_REQUEST_INTERCEPTOR)
				.build();

		return restAdapter;
	}

	private static String getCookieString(Response response) {
		for (Header header : response.getHeaders()) {
			if (null!= header.getName() && header.getName().equals("Set-Cookie")) {
				return header.getValue();
			}
		}
		return null;
	}

	private static final RequestInterceptor COOKIES_REQUEST_INTERCEPTOR = new RequestInterceptor() {
		@Override
		public void intercept(RequestInterceptor.RequestFacade request) {
			String token = userHelper().getUser().getToken();
			if (null != token && token.length() > 0) {
				request.addHeader("Authorization", "Token " + token);
			}
		}
	};
}

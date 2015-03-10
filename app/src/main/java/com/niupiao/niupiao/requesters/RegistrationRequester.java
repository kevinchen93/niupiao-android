package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.ApiKeyDeserializer;
import com.niupiao.niupiao.deserializers.UserDeserializer;
import com.niupiao.niupiao.models.ApiKey;
import com.niupiao.niupiao.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevinchen on 3/8/15.
 */
public class RegistrationRequester {

    private static final String TAG = RegistrationRequester.class.getSimpleName();

    public interface OnRegistrationListener extends VolleyCallback {
        public void onRegistration(User user, ApiKey apiKey);

        public void onRegistrationFailure(String errorMessage);
    }

    public static void register(final OnRegistrationListener listener, String legalName, String username, String cellPhone, String email, String password, String passwordConfirm) {
        // we provide our login credentials so server knows who's requesting an access token
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put(Constants.JsonApi.Register.LEGAL_NAME, legalName);
            jsonObject.put(Constants.JsonApi.Register.USERNAME, username);
            jsonObject.put(Constants.JsonApi.Register.CELL_PHONE, cellPhone);
            jsonObject.put(Constants.JsonApi.Register.EMAIL, email);
            jsonObject.put(Constants.JsonApi.Register.PASSWORD, password);
            jsonObject.put(Constants.JsonApi.Register.PASSWORD_CONFIRM, passwordConfirm);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ResourceRequest request = new ResourceRequest(
                listener,
                Request.Method.POST,
                Constants.JsonApi.EndPoints.SIGNUP_URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, jsonObject.toString());
                        try {
                            boolean success = jsonObject.getBoolean(Constants.JsonApi.Response.SUCCESS);
                            if (success) {
                                ApiKey apiKey = ApiKeyDeserializer.fromJsonObject(jsonObject.getJSONObject(Constants.JsonApi.Response.API_KEY));
                                User user = UserDeserializer.fromJsonObject(jsonObject.getJSONObject(Constants.JsonApi.Response.USER));
                                listener.onRegistration(user, apiKey);
                            } else {
                                listener.onRegistrationFailure(jsonObject.getString(Constants.JsonApi.Response.MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        NiupiaoApplication.getRequestQueue().add(request);
    }

}
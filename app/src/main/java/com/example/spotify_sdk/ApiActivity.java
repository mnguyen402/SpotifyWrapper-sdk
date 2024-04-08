package com.example.spotify_sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "ada5f9c6b3874bd9914586161232d0aa";
    public static final String REDIRECT_URI = "spotify-sdk://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken, mAccessCode;
    private Call mCall;
    private TextView tokenTextView, codeTextView, profileTextView;
    private static final String TAG = ApiActivity.class.getSimpleName();

    private Handler mainHandler;
    private Request request;
    private StringBuilder builder;
    private String top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_layout);

        // Initialize the handler with the main looper
        mainHandler = new Handler(Looper.getMainLooper());

        // Initialize the views
        tokenTextView = (TextView) findViewById(R.id.token_text_view);
        codeTextView = (TextView) findViewById(R.id.code_text_view);
        profileTextView = (TextView) findViewById(R.id.response_text_view);

        // Initialize the buttons
        Button tokenBtn = (Button) findViewById(R.id.token_btn);
        Button codeBtn = (Button) findViewById(R.id.code_btn);
        Button profileBtn = (Button) findViewById(R.id.profile_btn);
        Button topSongBtn = findViewById(R.id.topSongbutton);
        Button spotifyWrapperBtn = findViewById(R.id.spotifyWrapperBtn);

        // Set the click listeners for the buttons

        tokenBtn.setOnClickListener((v) -> {
            getToken();
        });

        codeBtn.setOnClickListener((v) -> {
            getCode();
        });

        spotifyWrapperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApiActivity.this, SpotifyWrapperActivity.class);
                intent.putExtra("topSong", ((TextView) findViewById(R.id.getTopSongTextView)).getText().toString());
                intent.putExtra("topArtist", ((TextView) findViewById(R.id.response_text_view)).getText().toString());
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener((v) -> {
            request = new Request.Builder()
                    .url("https://api.spotify.com/v1/me/top/artists?time_range=short_term&limit=5&offset=0")
                    .addHeader("Authorization", "Bearer " + mAccessToken)
                    .build();
            profileTextView = findViewById(R.id.response_text_view);
            top = "Top Artists";
            onGetTopStatsClicked();
        });
        topSongBtn.setOnClickListener((v) -> {
            request = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks?limit=5&time_range=medium_term&locale=en-US%2Cen%3Bq%3D0.9")
                    .addHeader("Authorization", "Bearer " + mAccessToken)
                    .build();
            profileTextView = findViewById(R.id.getTopSongTextView);
            top = "Top Song";
            onGetTopStatsClicked();
        });
    }


    public void onGetTopStatsClicked() {
        if (mAccessToken == null) {
            Toast.makeText(getApplicationContext(), "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        cancelCall();
        mCall = mOkHttpClient.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(getApplicationContext(), "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Server error: " + response + " Message: " + response.message());
                    String errorMessage = response.body() != null ? response.body().string() : "Unknown error";
                    mainHandler.post(() -> {
                        Toast.makeText(getApplicationContext(), "Error fetching data: " + errorMessage, Toast.LENGTH_SHORT).show();
                    });
                    return;
                }

                builder = new StringBuilder();

                try {
                    String responseBodyString = response.body().string(); // Get the response body as string
                    Log.d(TAG, "Response Body: " + responseBodyString); // Log the entire response body
                    JSONObject responseObject = new JSONObject(responseBodyString);
                    JSONArray itemsArray = responseObject.getJSONArray("items");
                    builder.append(top);
                    builder.append("\n\n");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject artistObject = itemsArray.getJSONObject(i);
                        String artistName = artistObject.getString("name");
                        //JSONArray genresArray = artistObject.getJSONArray("name");
                        builder.append((i+1)+". ").append(artistName);
                    //.append("\nGenres: ");

                        //for (int j = 0; j < genresArray.length(); j++) {
                          //  builder.append(genresArray.getString(j));
                            //if (j < genresArray.length() - 1) {
                               // builder.append(", ");
                           // }
                        //}
                        builder.append("\n\n"); // Separate each artist's info
                    }

                    mainHandler.post(() -> {
                            profileTextView.setText(builder.toString());
                    });

                } catch (JSONException e) {
                    Log.e(TAG, "Failed to parse data: " + e);
                    mainHandler.post(() -> {
                        Toast.makeText(getApplicationContext(), "Failed to parse data, watch Logcat for more details", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    /**
     * Get token from Spotify
     * This method will open the Spotify login activity and get the token
     * What is token?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(ApiActivity.this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    /**
     * Get code from Spotify
     * This method will open the Spotify login activity and get the code
     * What is code?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getCode() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
        AuthorizationClient.openLoginActivity(ApiActivity.this, AUTH_CODE_REQUEST_CODE, request);
    }


    /**
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();
            setTextAsync(mAccessToken, tokenTextView);

        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
            setTextAsync(mAccessCode, codeTextView);
        }
    }

    /**
     * Get user profile
     * This method will get the user profile using the token
     */

    /**
     * Creates a UI thread to update a TextView in the background
     * Reduces UI latency and makes the system perform more consistently
     *
     * @param text the text to set
     * @param textView TextView object to update
     */
    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

    /**
     * Get authentication request
     *
     * @param type the type of the request
     * @return the authentication request
     */
    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-top-read" }) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }

}
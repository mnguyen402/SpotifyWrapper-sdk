package com.example.spotify_sdk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private TextView tokenTextView, codeTextView, profileTextView, getTopSongTextView;
    private static final String TAG = ApiActivity.class.getSimpleName();

    private Handler mainHandler;
    private Request request;
    private StringBuilder builder;
    private String top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_layout);
        Button backBth = findViewById(R.id.backBtn);
        backBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to the Login activity
                Intent intent = new Intent(ApiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Initialize the handler with the main looper
        mainHandler = new Handler(Looper.getMainLooper());

        // Initialize the views
        profileTextView = (TextView) findViewById(R.id.response_text_view);
        getTopSongTextView = findViewById(R.id.getTopSongTextView);

        // Initialize the buttons
        Button spotifyWrapperBtnShort = findViewById(R.id.spotifyWrapperBtnShort);
        Button spotifyWrapperBtnMed = findViewById(R.id.spotifyWrapperBtnMed);
        Button spotifyWrapperBtnLong = findViewById(R.id.spotifyWrapperBtnLong);
        Button spotifyWrapperBtn = findViewById(R.id.SpotifyWrapperBtn);

        // Set the click listeners for the buttons
        mainHandler.postDelayed(() -> {
            getToken();
        }, 500);

        spotifyWrapperBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("dd/ MM");
                String dateToday = df.format(Calendar.getInstance().getTime());
                Intent intent = null;
                switch (dateToday) {
                    case "24/ 12":
                        intent = new Intent(getApplicationContext(), ChristmasWrappedActivity.class);
                        break;
                    case "01/ 01":
                        intent = new Intent(getApplicationContext(), NewyearWrappedActivity.class);
                        break;
                    case "28/ 10":
                        intent = new Intent(getApplicationContext(), ThanksgivingWrappedActivity.class);
                        break;
                    case "14/ 02":
                        intent = new Intent(getApplicationContext(), ValentineWrappedActivity.class);
                        break;
                    default:
                        intent = new Intent(getApplicationContext(), SpotifyWrapperActivity.class);
                }
                intent.putExtra("topArtist",profileTextView.getText().toString());
                intent.putExtra("topSong",getTopSongTextView.getText().toString());
                startActivity(intent);
                finish();
            }
        });
        spotifyWrapperBtnShort.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final boolean[] isArtistsFetched = {false};
                final boolean[] isSongsFetched = {false};

                Runnable checkAndStartActivity = () -> {
                    if (isArtistsFetched[0] && isSongsFetched[0]) {
                        startSpotifyWrapperActivity();
                    }
                };

                fetchTopArtists("short_term", () -> {
                    isArtistsFetched[0] = true;
                    checkAndStartActivity.run();
                });

                fetchTopSongs("short_term", () -> {
                    isSongsFetched[0] = true;
                    checkAndStartActivity.run();
                });
            }

            private void startSpotifyWrapperActivity() {
                Intent intent = new Intent(ApiActivity.this, SpotifyWrapperActivity.class);
                intent.putExtra("topSong", ((TextView) findViewById(R.id.getTopSongTextView)).getText().toString());
                intent.putExtra("topArtist", ((TextView) findViewById(R.id.response_text_view)).getText().toString());
                startActivity(intent);
            }
        });

        spotifyWrapperBtnMed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final boolean[] isArtistsFetched = {false};
                final boolean[] isSongsFetched = {false};

                Runnable checkAndStartActivity = () -> {
                    if (isArtistsFetched[0] && isSongsFetched[0]) {
                        startSpotifyWrapperActivity();
                    }
                };

                fetchTopArtists("medium_term", () -> {
                    isArtistsFetched[0] = true;
                    checkAndStartActivity.run();
                });

                fetchTopSongs("medium_term", () -> {
                    isSongsFetched[0] = true;
                    checkAndStartActivity.run();
                });
            }

            private void startSpotifyWrapperActivity() {
                Intent intent = new Intent(ApiActivity.this, SpotifyWrapperActivity.class);
                intent.putExtra("topSong", ((TextView) findViewById(R.id.getTopSongTextView)).getText().toString());
                intent.putExtra("topArtist", ((TextView) findViewById(R.id.response_text_view)).getText().toString());
                startActivity(intent);
            }
        });

        spotifyWrapperBtnLong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final boolean[] isArtistsFetched = {false};
                final boolean[] isSongsFetched = {false};

                Runnable checkAndStartActivity = () -> {
                    if (isArtistsFetched[0] && isSongsFetched[0]) {
                        startSpotifyWrapperActivity();
                    }
                };

                fetchTopArtists("long_term", () -> {
                    isArtistsFetched[0] = true;
                    checkAndStartActivity.run();
                });

                fetchTopSongs("long_term", () -> {
                    isSongsFetched[0] = true;
                    checkAndStartActivity.run();
                });
            }

            private void startSpotifyWrapperActivity() {
                Intent intent = new Intent(ApiActivity.this, SpotifyWrapperActivity.class);
                intent.putExtra("topSong", ((TextView) findViewById(R.id.getTopSongTextView)).getText().toString());
                intent.putExtra("topArtist", ((TextView) findViewById(R.id.response_text_view)).getText().toString());
                startActivity(intent);
            }
        });
    }

    private void fetchTopArtists(String timeRange, Runnable onSuccess) {
        String url = "https://api.spotify.com/v1/me/top/artists?time_range=" + timeRange + "&limit=5&offset=0";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();
        TextView artistTextView = findViewById(R.id.response_text_view); // The TextView for artists
        top = "Top Artists";

        mCall = mOkHttpClient.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch top artists data: " + e);
                mainHandler.post(() -> Toast.makeText(getApplicationContext(), "Failed to fetch top artists data, watch Logcat for more details", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Server error: " + response);
                    mainHandler.post(() -> Toast.makeText(getApplicationContext(), "Error fetching top artists data", Toast.LENGTH_SHORT).show());
                    return;
                }

                // Process the response for top artists
                String responseBodyString = response.body().string(); // Get the response body as a string
                mainHandler.post(() -> {
                    onGetTopStatsClicked(responseBodyString, artistTextView, "Top Artists");
                });
                fetchTopSongs(timeRange, onSuccess); // Call fetchTopSongs() after successful completion of fetchTopArtists()
            }
        });
    }

    private void fetchTopSongs(String timeRange, Runnable onSuccess) {
        String url = "https://api.spotify.com/v1/me/top/tracks?time_range=" + timeRange + "&limit=5&offset=0";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();
        TextView songTextView = findViewById(R.id.getTopSongTextView); // The TextView for songs
        top = "Top Songs";

        mCall = mOkHttpClient.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch top songs data: " + e);
                mainHandler.post(() -> Toast.makeText(getApplicationContext(), "Failed to fetch top songs data, watch Logcat for more details", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Server error: " + response);
                    mainHandler.post(() -> Toast.makeText(getApplicationContext(), "Error fetching top songs data", Toast.LENGTH_SHORT).show());
                    return;
                }

                // Process the response for top songs
                String responseBodyString = response.body().string(); // Get the response body as a string
                mainHandler.post(() -> {
                    onGetTopStatsClicked(responseBodyString, songTextView, "Top Songs"); // Pass the response body and the target TextView for songs
                });
            }
        });
    }

    public void onGetTopStatsClicked(String responseBodyString, TextView targetTextView, String title) {
        try {
            JSONObject responseObject = new JSONObject(responseBodyString);
            JSONArray itemsArray = responseObject.getJSONArray("items");
            StringBuilder builder = new StringBuilder(title).append("\n\n"); // Use the title parameter here
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                String name = itemObject.getString("name");
                builder.append(i + 1).append(". ").append(name).append("\n\n");
            }
            mainHandler.post(() -> targetTextView.setText(builder.toString())); // Ensure UI updates are posted to the main thread
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse data: " + e);
            Toast.makeText(getApplicationContext(), "Failed to parse data, watch Logcat for more details", Toast.LENGTH_SHORT).show();
        }
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
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == AUTH_TOKEN_REQUEST_CODE) {
            final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

            if (response.getType() == AuthorizationResponse.Type.TOKEN) {
                mAccessToken = response.getAccessToken();

                if (mAccessToken != null && !mAccessToken.isEmpty()) {

                } else {
                    Log.e(TAG, "Token is null or empty");
                }
            } else if (response.getType() == AuthorizationResponse.Type.ERROR) {
                Log.e(TAG, "Authentication Error: " + response.getError());
            }
        } else {
            Log.e(TAG, "Intent data is null");
        }
    }

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
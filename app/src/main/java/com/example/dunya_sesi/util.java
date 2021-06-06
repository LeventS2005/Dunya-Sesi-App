package com.example.dunya_sesi;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class util {

    // This your own private app name
    private static String app_name = "levent";
    // Message that shows up at the bottom of the forgot password email sent to user
    private static String app_forgot_password_message = "Thank you for choosing us for your world noise.";
    // Your company email so users can reach you when they face any issues.
    private static String app_email = "123@support.com";

    private static String loginUrlBackEnd = "https://mohamedmnete.com/login_user.php";
    private static String registerUrlBackEnd = "https://mohamedmnete.com/create_user.php";
    private static String forgotPasswordUrlBackEnd = "https://mohamedmnete.com/forgot_password.php";


    private static String default_caption_new_user = "Hello World, I am live right now! Yay!";

    static class LoginTask extends AsyncTask<String, Void, String> {
        String response;
        String email;
        String password;

        public LoginTask(String response, String email, String password) {
            this.response = response;
            this.email = email;
            this.password = password;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");

                data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                        + URLEncoder.encode(password, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8")
                        + "=" + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(loginUrlBackEnd);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    response += line;
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String result) {
            response = result;
        }
    }

    static class RegisterTask extends AsyncTask<String, Void, String> {
        String response;
        String email;
        String password;
        String username;

        public RegisterTask (String response, String email, String password, String username) {
            this.response = response;
            this.email = email;
            this.password = password;
            this.username = username;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");

                data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                        + URLEncoder.encode(password, "UTF-8");

                data += "&" + URLEncoder.encode("username", "UTF-8") + "="
                        + URLEncoder.encode(username, "UTF-8");

                data += "&" + URLEncoder.encode("caption", "UTF-8") + "="
                        + URLEncoder.encode(default_caption_new_user, "UTF-8");

                data += "&" + URLEncoder.encode("profile_photo_url", "UTF-8") + "="
                        + URLEncoder.encode("default", "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8")
                        + "=" + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(registerUrlBackEnd);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    response += line;
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String result) {
            response = result;
        }
    }

    static class ForgotPasswordTask extends AsyncTask<String, Void, String> {
        String response;
        String email;

        public ForgotPasswordTask (String response, String email) {
            this.response = response;
            this.email = email;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");

                data += "&" + URLEncoder.encode("app_message", "UTF-8") + "="
                        + URLEncoder.encode(app_forgot_password_message, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                data += "&" + URLEncoder.encode("app_email", "UTF-8") + "="
                        + URLEncoder.encode(app_email, "UTF-8");

                URL url = new URL(forgotPasswordUrlBackEnd);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    response += line;
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String result) {
            response = result;
        }
    }

    static boolean isNetworkAvailable(ConnectivityManager connectivityManager) {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }


}

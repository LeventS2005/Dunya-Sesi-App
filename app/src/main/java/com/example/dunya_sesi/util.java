package com.example.dunya_sesi;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import com.example.dunya_sesi.ui.main.WorldSearchUserResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private static String getUserProfileUrlBackEnd = "https://mohamedmnete.com/select_user.php";
    private static String searchUserUrlBackEnd = "https://mohamedmnete.com/search_user.php";
    private static String updateUsernameUrlBackEnd = "https://mohamedmnete.com/update_username.php";
    private static String updateCaptionUrlBackEnd = "https://mohamedmnete.com/update_caption.php";
    private static String updateEmailUrlBackEnd = "https://mohamedmnete.com/update_email.php";
    private static String updatePasswordUrlBackEnd = "https://mohamedmnete.com/update_password.php";
    private static String friendshipStatusUrlBackEnd = "https://mohamedmnete.com/get_friendship_status.php";
    private static String sentFriendRequestUrlBackEnd = "https://mohamedmnete.com/did_i_send_request.php";
    private static String reciviedFriendRequestUrlBackEnd = "https://mohamedmnete.com/did_i_recieve_request.php";
    private static String addFriendUrlBackEnd = "https://mohamedmnete.com/send_friend_request.php";
    private static String removeFriendUrlBackEnd = "https://mohamedmnete.com/respond_friendship_status.php";
    private static String getFriendReqeustsUrlBackEnd = "https://mohamedmnete.com/get_friend_requests.php";
    private static String getFriendListUrlBackEnd = "https://mohamedmnete.com/get_friend_list.php";
    private static String clearChatUrlBackEnd = "https://mohamedmnete.com/clear_chat.php";
    private static String getMessagesUrlBackEnd = "https://mohamedmnete.com/get_messages.php";
    private static String sendMessageUrlBackEnd = "https://mohamedmnete.com/send_message.php";
    private static String deleteMessageUrlBackEnd = "https://mohamedmnete.com/delete_message.php";
    private static String getRecievedMessagesUrlBackEnd = "https://mohamedmnete.com/get_recieved_messages.php";

    public static String default_caption_new_user = "Hello World, I am live right now! Yay!";

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

    public static class GetUserProfileTask extends AsyncTask<String, Void, String> {
        String response;
        String email;

        public GetUserProfileTask (String response, String email) {
            this.response = response;
            this.email = email;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("email_query", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(getUserProfileUrlBackEnd);

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

    public static class SearchUserProfileTask extends AsyncTask<String, Void, String> {
        String response;
        String searchQuery;

        public SearchUserProfileTask (String response, String searchQuery) {
            this.response = response;
            this.searchQuery = searchQuery;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("search_query", "UTF-8")
                        + "=" + URLEncoder.encode(searchQuery, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(searchUserUrlBackEnd);

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

                System.out.println(response);

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

    public static class UpdateUsernameTask extends AsyncTask<String, Void, String> {
        String response;
        String email;
        String newUsername;

        public UpdateUsernameTask (String response, String email, String newUsername) {
            this.response = response;
            this.email = email;
            this.newUsername = newUsername;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");

                data += "&" + URLEncoder.encode("new_username", "UTF-8") + "="
                        + URLEncoder.encode(newUsername, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(updateUsernameUrlBackEnd);

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

    public static class UpdateCaptionTask extends AsyncTask<String, Void, String> {
        String response;
        String email;
        String newCaption;

        public UpdateCaptionTask (String response, String email, String newCaption) {
            this.response = response;
            this.email = email;
            this.newCaption = newCaption;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");

                data += "&" + URLEncoder.encode("new_caption", "UTF-8") + "="
                        + URLEncoder.encode(newCaption, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(updateCaptionUrlBackEnd);

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

    public static class UpdateEmailTask extends AsyncTask<String, Void, String> {
        String response;
        String email;
        String newEmail;

        public UpdateEmailTask (String response, String email, String newEmail) {
            this.response = response;
            this.email = email;
            this.newEmail = newEmail;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");

                data += "&" + URLEncoder.encode("new_email", "UTF-8") + "="
                        + URLEncoder.encode(newEmail, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(updateEmailUrlBackEnd);

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

    public static class UpdatePasswordTask extends AsyncTask<String, Void, String> {
        String response;
        String email;
        String newPassword;

        public UpdatePasswordTask (String response, String email, String newPassword) {
            this.response = response;
            this.email = email;
            this.newPassword = newPassword;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");

                data += "&" + URLEncoder.encode("new_password", "UTF-8") + "="
                        + URLEncoder.encode(newPassword, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(updatePasswordUrlBackEnd);

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

    public static class GetFriendshipStatusTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;

        public GetFriendshipStatusTask (String response, String myId, String friendId) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(friendshipStatusUrlBackEnd);

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

    public static class GetSentFriendRequestTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;

        public GetSentFriendRequestTask (String response, String myId, String friendId) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(sentFriendRequestUrlBackEnd);

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

                System.out.println("did i send a request"+response);

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

    public static class GetRecievedFriendRequestTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;

        public GetRecievedFriendRequestTask (String response, String myId, String friendId) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(reciviedFriendRequestUrlBackEnd);

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

                System.out.println("recieved"+response);

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

    public static class AddFriendTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;

        public AddFriendTask (String response, String myId, String friendId) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                URL url = new URL(addFriendUrlBackEnd);

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

                System.out.println("adding"+response);

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

    public static class RemoveFriendTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;

        public RemoveFriendTask (String response, String myId, String friendId) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                data += "&" + URLEncoder.encode("update", "UTF-8") + "="
                        + URLEncoder.encode("REMOVE", "UTF-8");

                URL url = new URL(removeFriendUrlBackEnd);

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

    public static class AcceptFriendRequestTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;

        public AcceptFriendRequestTask (String response, String myId, String friendId) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("app_name", "UTF-8") + "="
                        + URLEncoder.encode(app_name, "UTF-8");

                data += "&" + URLEncoder.encode("update", "UTF-8") + "="
                        + URLEncoder.encode("ACCEPT", "UTF-8");

                URL url = new URL(removeFriendUrlBackEnd);

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

    public static class GetFriendRequestsTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;

        public GetFriendRequestsTask (String response, String myId) {
            this.response = response;
            this.myId = myId;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                URL url = new URL(getFriendReqeustsUrlBackEnd);

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

    public static class GetFriendListTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;

        public GetFriendListTask (String response, String myId) {
            this.response = response;
            this.myId = myId;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                URL url = new URL(getFriendListUrlBackEnd);

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

    public static class ClearChatTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;

        public ClearChatTask (String response, String myId, String friendId) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");


                URL url = new URL(clearChatUrlBackEnd);

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

    public static class GetAllMessagesTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        public GetAllMessagesTask (String response, String myId, String friendId) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
        }

        public GetAllMessagesTask (String response, String myId, String friendId, String timeStamp) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
            this.timeStamp = timeStamp;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("last_time_stamp", "UTF-8") + "="
                        + URLEncoder.encode(timeStamp, "UTF-8");

                URL url = new URL(getMessagesUrlBackEnd);

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

    public static class GetRecievedMessagesTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;
        String timeStamp;

        public GetRecievedMessagesTask (String response, String myId, String friendId, String timeStamp) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
            this.timeStamp = timeStamp;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("last_time_stamp", "UTF-8") + "="
                        + URLEncoder.encode(timeStamp, "UTF-8");

                URL url = new URL(getRecievedMessagesUrlBackEnd);

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

                System.out.println(response);

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

    public static class DeleteMessageTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;
        String time_contact;

        public DeleteMessageTask (String response, String myId, String friendId, String time_contact) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
            this.time_contact = time_contact;
        }

        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("time_contact", "UTF-8") + "="
                        + URLEncoder.encode(time_contact, "UTF-8");

                URL url = new URL(deleteMessageUrlBackEnd);

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

    public static class SendMessageTask extends AsyncTask<String, Void, String> {
        String response;
        String myId;
        String friendId;
        String newMessage;

        public SendMessageTask (String response, String myId, String friendId, String newMessage) {
            this.response = response;
            this.myId = myId;
            this.friendId = friendId;
            this.newMessage = newMessage;
        }


        protected String doInBackground(String... urls) {

            try {
                BufferedReader reader=null;

                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(myId, "UTF-8");

                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "="
                        + URLEncoder.encode(friendId, "UTF-8");

                data += "&" + URLEncoder.encode("message", "UTF-8") + "="
                        + URLEncoder.encode(newMessage, "UTF-8");

                URL url = new URL(sendMessageUrlBackEnd);

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

    public static boolean isNetworkAvailable(ConnectivityManager connectivityManager) {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static String getEmailFromSharePreferences (Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        return sharedPref.getString("EMAIL","mmnete@trinity.edu");
    }

    public static String getUserIdFromSharePreferences (Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        return sharedPref.getString("USER_ID","mmnete@trinity.edu");
    }

    public static ArrayList<WorldSearchUserResult> searchResultToUserList(String response) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<WorldSearchUserResult> results = new ArrayList<>();

        if (response.equals("USER-NOT-FOUND")) {
            return results;
        }

        try {
            WorldSearchUserResult[] myObjects = mapper.readValue(response, WorldSearchUserResult[].class);
            for (WorldSearchUserResult i : myObjects) {
                results.add(i);
            }
            return results;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static ArrayList<ChatMessage> messageResultToChatMessageList(String response) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<ChatMessage> results = new ArrayList<>();

        if (response.equals("USER-NOT-FOUND") || response.length() == 0) {
            return results;
        }

        try {
            ChatMessage[] myObjects = mapper.readValue(response, ChatMessage[].class);
            for (ChatMessage i : myObjects) {
                results.add(i);
            }
            return results;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return results;
    }


}

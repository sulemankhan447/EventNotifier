package com.suleman.eventnotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowEventActivity extends AppCompatActivity {
    TextView tvEventName;
    TextView tvFrom;
    TextView tvTo;
    TextView tvLocation;
    TextView tvContact;
    RequestQueue requestQueue;
    private static final String server_url = "http://192.168.1.105/EventNotifier/eventDetail.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        tvEventName=(TextView)findViewById(R.id.tvEventName);
        tvFrom=(TextView)findViewById(R.id.tvFrom);
        tvTo=(TextView)findViewById(R.id.tvTo);
        tvLocation=(TextView)findViewById(R.id.tvLocation);
        tvContact=(TextView)findViewById(R.id.tvContact);
        final int event_id = getIntent().getExtras().getInt("event_id");
//        Toast.makeText(ShowEventActivity.this,"Event is "+event_id,Toast.LENGTH_LONG).show();
        requestQueue = Volley.newRequestQueue(ShowEventActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ShowEventActivity.this,response,Toast.LENGTH_SHORT).show();
                try {
//                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject obj = new JSONObject(response);

                    Toast.makeText(ShowEventActivity.this,obj.toString(),Toast.LENGTH_SHORT).show();
                    String event_name = obj.getString("name");
                    String from = obj.getString("from");
                    String to = obj.getString("to");
                    String location = obj.getString("location");
                    String contact = obj.getString("contact");
                    tvEventName.setText(event_name);
                    tvFrom.setText(from);
                    tvTo.setText(to);
                    tvLocation.setText(location);
                    tvContact.setText(contact);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ShowEventActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("event_id",Integer.toString(event_id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

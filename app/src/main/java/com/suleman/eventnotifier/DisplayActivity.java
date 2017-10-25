package com.suleman.eventnotifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    public static String url ="http://192.168.1.105/EventNotifier/getEvents.php";
    ListView lstEvents;
    List<Event> eventList;
    ListAdapter adapter;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        lstEvents= (ListView) findViewById(R.id.lstEvents);
//        arrayAdapter=new ArrayAdapter<String>(DisplayActivity.this,R.layout.listview_items,);
        requestQueue= Volley.newRequestQueue(DisplayActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Log.d("Volley Response Received",response.toString());

                eventList = new ArrayList<Event>();
                Event event;
                try
                {
                    for(int i=0;i<response.length();i++)
                    {
                        event =new Event();
                        JSONObject jsonObject = response.getJSONObject(i);
                       String event_name = jsonObject.getString("name");
                       // Toast.makeText(DisplayActivity.this,event_name,Toast.LENGTH_LONG).show();
                        event.eventName=jsonObject.getString("name");
                        eventList.add(event);

                    }
                    adapter= new ListAdapter(eventList,DisplayActivity.this);
                    lstEvents.setAdapter(adapter);
                }
                 catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Log.d("Volley Error Occured",error.toString());
                Toast.makeText(DisplayActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
        lstEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(DisplayActivity.this,"Clicked Index is "+position,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DisplayActivity.this,ShowEventActivity.class);
                i.putExtra("event_id",position+1);
                startActivity(i);
            }
        });
    }
}

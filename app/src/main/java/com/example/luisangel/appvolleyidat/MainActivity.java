package com.example.luisangel.appvolleyidat;

import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView tvDatos;
    private Button btnWS;
    private RequestQueue mQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDatos = (TextView)findViewById(R.id.tvDatos);

        btnWS = (Button)findViewById(R.id.btnWS);
        //Instanciar a Volley a través de una COLA DE CONSULTA
        mQueue = Volley.newRequestQueue(this);

        btnWS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsumirWS();
            }
        });

    }

    private void ConsumirWS() {
        //Inicializar el URL del Web service
        String url = "https://api.myjson.com/bins/kp9wz";

        //Instanciar el objeto request.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                //La respuesta de la llamada
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("employees");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject employee = jsonArray.getJSONObject(i);
                                String nombre = employee.getString("firstname");
                                int edad = employee.getInt("age");
                                String email = employee.getString("mail");
                                tvDatos.append(nombre + "-"+String.valueOf(edad)+"-"+email );

                            }


                        } catch (JSONException ex) {
                            ex.printStackTrace();

                        }
                    }
                },
                    //Enviamos error de consulta a WS
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        //Agrega la consulta a la cola.
        mQueue.add(request);


    }


}

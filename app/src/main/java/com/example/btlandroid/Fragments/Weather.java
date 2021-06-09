package com.example.btlandroid.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.btlandroid.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Weather extends Fragment {

    private EditText city, country;
    private TextView result;
    private Button button;

    private final String URL = "http://api.openweathermap.org/data/2.5/weather";
    private final String apikey = "ded89ed57affea7a6cda23019ba88e72";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        city = v.findViewById(R.id.city);
        country = v.findViewById(R.id.country);
        result = v.findViewById(R.id.tvResult);
        button = v.findViewById(R.id.get);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWheather(v);
            }
        });
    }

    public void getWheather(View view) {
        String tempURL = "";
        String cityName = city.getText().toString().trim();
        String countryName = country.getText().toString().trim();

        if (cityName.equals("")) {
            result.setText("City field can not be empty !");
        } else {
            if (!countryName.equals("")) {
                tempURL = URL + "?q=" + cityName + "," + countryName + "&appid=" + apikey;

            } else {
                tempURL = URL + "?q=" + cityName + "&appid=" + apikey;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = "";
                    try {
                        JSONObject jsonObjectRespone = new JSONObject(response);

                        JSONArray jsonArray = jsonObjectRespone.getJSONArray("weather");

                        JSONObject jsonObjectWheather = jsonArray.getJSONObject(0);
                        String des = jsonObjectWheather.getString("description");

                        JSONObject jsonObjectMain = jsonObjectRespone.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        int humidity = jsonObjectMain.getInt("humidity");

                        JSONObject jsonObjectWind = jsonObjectRespone.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");

                        JSONObject jsonObjectSys = jsonObjectRespone.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonObjectRespone.getString("name");

                        output += "Thời tiết hiện tại của: " + cityName + "(" + countryName + ")" +
                                "\nNhiệt độ: " + df.format(temp) + "độ C" +
                                "\nCảm giác như: " + df.format(feelLike) + "độ C" +
                                "\nĐộ ẩm: " + humidity + "%" +
                                "\nTốc độ gió: " + wind + "m/s" +
                                "\nMô tả: " +des;
                        result.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
    }
}
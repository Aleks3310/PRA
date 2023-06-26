package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class new_theatre_film extends AppCompatActivity {
    TextView tvInfo;
    EditText tvName;
    EditText tvAddress;
    Button tvButton;
    new_theatre_film.MyTask mt;
    String line;
    String total="";
    new_theatre_film.MyTaskN mtn;
    //    ArrayList <new_theatre_film.MyTaskTF>mttf=new ArrayList <new_theatre_film.MyTaskTF>();
    new_theatre_film.MyTaskTF mttf;
    ListView lvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_theatre_film);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvName = (EditText) findViewById(R.id.editTextTextPersonName);
        tvAddress = (EditText) findViewById(R.id.editTextTextPersonAddress);
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        tvButton = (Button) findViewById(R.id.but);
        mt = new new_theatre_film.MyTask();
        mt.execute();

    }

    class MyTask extends AsyncTask<Void, Void, ArrayList<String[]>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Begin");
        }
        @Override
        protected ArrayList<String[]> doInBackground(Void... params) {
            ArrayList<String[]> res = new ArrayList<>();
            HttpURLConnection myConnection = null;
            try {
                URL githubEndpoint = new URL("http://10.0.2.2:8080/kino/allfilms");
                myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                tvInfo.setText("1");

            } catch (IOException e) {
                e.printStackTrace();
                tvInfo.setText("2");

            }

            int i=0;
            try {
                i = myConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i==200) {
                InputStream responseBody=null;
                try {
                    responseBody = myConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InputStreamReader responseBodyReader =null;
                try {
                    responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
                while (true) {
                    try {
                        if (!((line = r.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    total=total+line;
                }
                JSONArray JA=null;
                try {
                    JA=new JSONArray(total);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int j=0;j<JA.length();j++) {
                    JSONObject JO=null;
                    try {
                        JO=JA.getJSONObject(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String[] st= new String[2];
                    try {
                        st[0] = JO.getString("name").toString();
                        st[1] = JO.getString("id").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    res.add(st);
                }

            }
            myConnection.disconnect();

            return res;
        }
        @Override
        protected void onPostExecute(ArrayList<String[]> result) {
            super.onPostExecute(result);
            new_theatre_film.ClAdapter clAdapter=new new_theatre_film.ClAdapter(tvInfo.getContext(),result);
//            lvMain = (ListView) findViewById(R.id.lvMain);
            lvMain.setAdapter(clAdapter);
//            tvInfo.setText("End");
            tvButton.setEnabled(true);
        }
    }
    class ClAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater lInflater;
        List<String[]> lines;
        ClAdapter(Context context, List<String[]> elines){
            ctx = context;
            lines = elines;
            lInflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return lines.size();
        }
        @Override
        public Object getItem(int position) {
            return lines.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.activity_adapt, parent, false);
            };
            String[] p =(String[]) getItem(position);
            ((TextView) view.findViewById(R.id.tvText)).setText(p[0]);
            return view;
        };
        public boolean getCheck (int position){

            return true;
        }
    }
    public void onclick(View v) {
        mtn = new new_theatre_film.MyTaskN();
        mtn.execute(tvName.getText().toString(), tvAddress.getText().toString());
    }
    class MyTaskN extends AsyncTask<String, Void, String > {
        @Override
        protected String doInBackground(String... params) {
            String line = null;
            String total = null;
            BufferedOutputStream os = null;
            HttpURLConnection myConnection = null;
            JSONObject obj=null;
            try {
                URL githubEndpoint = new URL("http://10.0.2.2:8080/kino/writetheatre");
                myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myConnection.setRequestProperty("Accept",
                    "application/vnd.github.v3+kino");
            myConnection.setRequestProperty("Contact-Me",
                    "hathibelagal@example.com");
            try {
                myConnection.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            myConnection.setDoOutput(true);
            myConnection.setDoOutput(true);
            myConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            myConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", params[0]);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }try {
                jsonObject.put("address", params[1]);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            String message = jsonObject.toString();
            try {
                os = new BufferedOutputStream(myConnection.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                os.write(message.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//clean up
            try {
                os.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            int i = 0;
            try {
                i = myConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                tvInfo.setText(str);
            if (i == 200) {
                InputStream responseBody = null;
                try {
                    responseBody = myConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
                while (true) {
                    try {
                        if (!((line = r.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    total = line;
                }
                try {
                    obj = new JSONObject(total);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    total=obj.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return total;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            ID=result;
            int n = lvMain.getChildCount();
            int m = 0;
            JSONArray JA = new JSONArray();
            for (int i = 0; i < n; i++) {
                String[] st = (String[]) lvMain.getAdapter().getItem(i);
                LinearLayout ll = (LinearLayout) lvMain.getChildAt(i);
                CheckBox ch = (CheckBox) ll.getChildAt(0);
                if (ch.isChecked()) {
//                    mttf.add(new new_film.MyTaskTF());
//                    mttf.get(m).execute(st[1],result);
                    try {
                        JA.put(m,new JSONObject());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JA.getJSONObject(m).put("theatreID",result);
                        JA.getJSONObject(m).put("filmID",st[1]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    m++;
                }
            }
            mttf = new new_theatre_film.MyTaskTF();
            mttf.execute(JA);

        }
    }
    class MyTaskTF extends AsyncTask<JSONArray, Void, String > {

        @Override
        protected String doInBackground(JSONArray... jsonArrays) {
            String line = null;
            String total = null;
            ArrayList<String[]> res=new ArrayList <>();
            BufferedOutputStream os = null;
            HttpURLConnection myConnection = null;
            JSONObject obj=null;
            try {
                URL githubEndpoint = new URL("http://10.0.2.2:8080/kino/films_theatres");
                myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myConnection.setRequestProperty("Accept",
                    "application/vnd.github.v3+kino");
            myConnection.setRequestProperty("Contact-Me",
                    "hathibelagal@example.com");
            try {
                myConnection.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            myConnection.setDoOutput(true);
            myConnection.setDoOutput(true);
            myConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            myConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            String message = jsonArrays[0].toString();

            try {
                os = new BufferedOutputStream(myConnection.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                os.write(message.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//clean up
            try {
                os.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            int i = 0;
            try {
                i = myConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                tvInfo.setText(str);
            if (i == 200) {
                InputStream responseBody = null;
                try {
                    responseBody = myConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
                while (true) {
                    try {
                        if (!((line = r.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    total = line;
                }
                JSONArray JA=null;
                try {
                    JA=new JSONArray(total);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int j=0;j<JA.length();j++) {
                    JSONObject JO=null;
                    try {
                        JO=JA.getJSONObject(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String[] st= new String[2];
                    try {
                        st[0] = JO.getString("result").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    res.add(st);
                }            }
            return total;        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvInfo.setText("End");
            return;
        }
    }
}
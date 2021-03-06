package com.example.karkapazhagu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Csesem1sub6 extends AppCompatActivity {


    ListView listView;


    DatabaseReference mDatabaseReference;
    private ProgressDialog progressDialog;


    List<Uploads> uploadList;

    public class Constants5
    {
        public static final String DATABASE_PATH_UPLOADS ="sem1sub6";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csesem1sub6);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uploadList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview);
        progressDialog = new ProgressDialog(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Uploads upload = uploadList.get(i);

                Intent intent = new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(upload.getUrl()));
                startActivity(intent);
            }
        });

        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants5.DATABASE_PATH_UPLOADS);


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Uploads upload = postSnapshot.getValue(Uploads.class);
                    uploadList.add(upload);
                }

                String[] uploads = new String[uploadList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i]=uploadList.get(i).getDescription();
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads){
                    @Override
                    public View getView(int position, View convertView,  ViewGroup parent) {

                        View view=super.getView(position, convertView, parent);

                        TextView myText=(TextView) view.findViewById(android.R.id.text1);
                        myText.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myalertBuilder = new AlertDialog.Builder(Csesem1sub6.this);
                myalertBuilder.setTitle("Attention!!!");
                myalertBuilder.setMessage("Kindly ensure to upload the correct file");
                myalertBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), pop_up_cse_sem1_sub1.class));

                    }
                });
                myalertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Csesem1sub6.this,"You clicked cancel",Toast.LENGTH_SHORT).show();

                    }

                });
                myalertBuilder.show();
            }
        });
    }

}
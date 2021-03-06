package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HospitalEventMain extends AppCompatActivity {

    public static final String Extra_Title = "com.example.lifeline.Extra_Title";
    public static final String Extra_pos="com.example.lifeline.Extra_pos";
    public static final String Extra_image="com.example.lifeline.Extra_image";
    public static final String Extra_description="com.example.lifeline.Extra_description";
    public static final String Extra_Phone="com.example.lifeline.Extra_Phone";
    public static final String Extra_Address="com.example.lifeline.Extra_Address";
    public static final String Extra_Latitude="com.example.lifeline.Extra_Latitude";
    public static final String Extra_Longitude="com.example.lifeline.Extra_Longitude";

    String Text1;
    String Image1;
    String Description1;
    String Phone1;
    String Address1;
    String Latitude1;
    String Longitude1;
    int position1;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookref =db.collection("Events");
    private EventAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_event_main);
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalEventMain.this,NewEventActivity.class));
            }
        });
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        Query query = notebookref;
        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query,Event.class)
                .build();

        adapter=new EventAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteitem(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new EventAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Event event =documentSnapshot.toObject(Event.class);
                //startActivity(new Intent(MainActivity.this,Event_Inside_Main.class));
                Image1=event.getImage();
                Text1= event.getTitle();
                position1=position;
                Description1=event.getDescription();
                Phone1=event.getPhone();
                Address1=event.getAddress();
                Latitude1=event.getLatitude();
                Longitude1=event.getLongitude();
                Event_Inside_Main_open();
            }
        });
    }

    public void Event_Inside_Main_open(){
        // EditText Title = (EditText)findViewById(R.id.)
        // String
        Intent intent = new Intent(this,Event_Inside_Main.class);
        intent.putExtra(Extra_Title,Text1);
        intent.putExtra(Extra_pos,position1);
        intent.putExtra(Extra_image,Image1);
        intent.putExtra(Extra_description,Description1);
        intent.putExtra(Extra_Phone,Phone1);
        intent.putExtra(Extra_Address,Address1);
        intent.putExtra(Extra_Latitude,Latitude1);
        intent.putExtra(Extra_Longitude,Longitude1);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

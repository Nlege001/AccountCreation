
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accounttcreation.AdapterClass;
import com.example.accounttcreation.DataClass;
import com.example.accounttcreation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;



public class Searchbatkt extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<DataClass> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_db_recycler);

        ref = FirebaseDatabase.getInstance().getReference().child("Data Class");
        recyclerView = findViewById(R.id.recylcerView);
        searchView = findViewById(R.id.searchView);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(ref != null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){

                        list =new ArrayList<>();
                        for(DataSnapshot ds :dataSnapshot.getChildren()){

                            list.add(ds.getValue(DataClass.class));
                        }
                        AdapterClass adapterClass = new AdapterClass(list);
                        recyclerView.setAdapter(adapterClass);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Searchbatkt.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }
    private void search(String str){
        ArrayList<DataClass> myList = new ArrayList<>();
        for(DataClass object : list){
            if(object.getName().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        AdapterClass adapterClass = new AdapterClass(myList);
        recyclerView.setAdapter(adapterClass);

    }
}
package com.grup2.jaestic_user.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grup2.jaestic_user.Models.Command;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grup2.jaestic_user.R;
import com.grup2.jaestic_user.RecyclerViewAdapters.RepeatOrderRecyclerViewAdapter;
import java.util.ArrayList;

public class MainFragment extends Fragment {
    private DatabaseReference ordersDatabase;
    private DatabaseReference dishDatabase;
    private ArrayList<Command> arrayCommands =  new ArrayList<Command>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        // Get current user information: e-mail
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentEmail = user.getEmail();
        // Firebase
        ordersDatabase = FirebaseDatabase.getInstance().getReference("Command");
        dishDatabase = FirebaseDatabase.getInstance().getReference("Command").child("cartItem").child("dish");

        // Add dishes in an ArrayList and send it to RecyclerView
        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                arrayCommands.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Command command = postSnapshot.getValue(Command.class);
                    // Only add commands from current user
                    if (command.getEmail().equals(currentEmail)) {
                        arrayCommands.add(command);
                    }
                }

                // Creates Recycler View "Top Ventas"
                RecyclerView orderRecyclerView = view.findViewById(R.id.lastOrdersRecyclerView);
                RepeatOrderRecyclerViewAdapter adapter = new RepeatOrderRecyclerViewAdapter(getContext(), arrayCommands);
                orderRecyclerView.setAdapter(adapter);
                orderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("JaEstic", "Failed to read value.", error.toException());
            }
        });

        return view;
    }
}


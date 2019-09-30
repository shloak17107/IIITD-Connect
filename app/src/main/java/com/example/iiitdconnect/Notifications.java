package com.example.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        RecyclerView notification_list= findViewById(R.id.notification_recycler);
        notification_list.setLayoutManager(new LinearLayoutManager(this));
        String[] l={"java","c","python","ggg5g","c5gret","3clp6c","3po6k","The recyclerview-selection library enables users to select items in RecyclerView list using touch or mouse input. You retain control over the visual presentation of a selected item. You can also retain control over policies controlling selection behavior, such as items that can be eligible for selection, and how many items can be selected.","ItemDetailsLookup enables the selection library to access information about RecyclerView items given a MotionEvent. It is effectively a factory for ItemDetails instances that are backed up by (or extracted from) a RecyclerView.ViewHolder instance. ","Register a SelectionTracker.SelectionObserver to be notified when selection changes. When a selection is first created, start ActionMode to represent this to the user, and provide selection-specific actions. For example, you may add a delete button to the ActionMode bar, and connect the back arrow on the bar to clear the selection. When the selection becomes empty (if the user cleared the selection the last time), don't forget to terminate action mode. ","At the end of the event processing pipeline, the library may determine that the user is attempting to activate an item by tapping it, or is attempting to drag and drop an item or set of selected items. React to these interpretations by registering the appropriate listene","The list row layout is pretty simple — some image and a text next to it. To make that happen we used LinearLayout with horizontal orientation. Note warning on the LinearLayout. It’s telling us that we should design our layout in a more efficient way. "};
<<<<<<< Updated upstream
        notification_list.setAdapter(new Adapter(l));
=======

        notification_list.setAdapter(new Adapter(l));
        String a ="pol";

>>>>>>> Stashed changes

    }
}

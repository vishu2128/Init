package com.practice.mnnit.init;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class contactUs extends AppCompatActivity implements SearchView.OnQueryTextListener {
    String[] c_name={"Shivani","Apurva","Arjita","Vaishali","Neeraj"};
    String[] c_registration={"20155114","20155094","20152041","20152068","20154114"};
    String[] c_course={"Btech","Btech","Btech","Btech","Btech"};
    String[] c_branch={"ECE","ECE","ECE","ECE","ECE"};
    String[] c_contact={"99999999","888888888","999999999","0000000000","66666666"};
    String[] c_email={"abc@gmail.com","abc@gmail.com","abc@gmail.com","abc@gmail.com","abc@gmail.com"};
    int[] c_flagid={R.drawable.shivani,R.drawable.shivani,R.drawable.shivani,R.drawable.shivani,R.drawable.shivani};

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecycleAdapterStudentContact adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Student> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        toolbar=(Toolbar)findViewById(R.id.recycleToolbar);
        setSupportActionBar(toolbar);
        recyclerView =(RecyclerView)findViewById(R.id.recycleStudentContact);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerRecyclerView(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);


        for(int i=0;i<5;i++)
        {
            arrayList.add(new Student(c_name[i],c_registration[i],c_course[i],c_branch[i],c_contact[i],c_email[i],c_flagid[i]));


        }
        adapter=new RecycleAdapterStudentContact(arrayList,this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return  true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Student> newList=new ArrayList<>();
        for(Student country1 : arrayList)
        {
            String name=country1.getName().toLowerCase();
            if(name.contains(newText))
            {
                newList.add(country1);
            }

        }
        adapter.setFilter(newList);
        return true;
    }
}
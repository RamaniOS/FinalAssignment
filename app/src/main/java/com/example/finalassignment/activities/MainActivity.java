package com.example.finalassignment.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.finalassignment.R;
import com.example.finalassignment.adapter.PersonAdapter;
import com.example.finalassignment.database.PersonServiceImpl;
import com.example.finalassignment.models.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listView;
    private PersonAdapter personAdapter;
    private List<Person> personList = new ArrayList<>();
    private PersonServiceImpl personService;
    private TextView totalTxtView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search by name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                fetchData(newText); // search
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getPlacesFromDB();
                return false;
            }
        });
        totalTxtView = findViewById(R.id.txtView_total);
        personService = new PersonServiceImpl(getApplicationContext());
        initRecyclerView();
        getPlacesFromDB();
        FloatingActionButton floatingActionButton = findViewById(R.id.btn_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void fetchData(String query) {
        personList.removeAll(personList);
        String param = "%"+query+"%";
        personList.addAll(personService.searchBy(param));
        personAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        listView = findViewById(R.id.list_view);
        personAdapter = new PersonAdapter(personList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(personAdapter);
    }

    public void getPlacesFromDB() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                personList.removeAll(personList);
                personList.addAll(personService.getAll());
                return null;
            }
            @Override
            protected void onPostExecute(Void agentsCount) {
                totalTxtView.setText("Total contacts: " + personList.size());
                personAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            getPlacesFromDB();
        }
    }
}

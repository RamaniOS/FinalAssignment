package com.example.finalassignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalassignment.R;
import com.example.finalassignment.activities.AddPersonActivity;
import com.example.finalassignment.activities.MainActivity;
import com.example.finalassignment.database.PersonServiceImpl;
import com.example.finalassignment.helper.Helper;
import com.example.finalassignment.models.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<Person> personList;

    public PersonAdapter(List<Person> personList) {
        this.personList = personList;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        final Person person = personList.get(position);
        holder.txtName.setText(person.getFullName());
        holder.txtPhn.setText(person.getPhoneNumber());
        holder.txtAddress.setText(person.getAddress());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performDelete(person, v.getContext());
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performUpdate(person, v.getContext());
            }
        });
    }

    private void performUpdate(Person person, Context context) {
        Intent intent = new Intent(context, AddPersonActivity.class);
        intent.putExtra("person", person);
        ((Activity) context).startActivityForResult(intent, 0);
    }

    private void performDelete(Person person, Context context) {
        PersonServiceImpl personService = new PersonServiceImpl(context);
        personService.delete(person);
        refreshList(context);
        Helper.showAlertCommon(context, "Deleted successfully.");
    }

    private void refreshList(Context context) {
        ((MainActivity) context).getPlacesFromDB();
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtAddress, txtPhn;
        public ImageView imgEdit, imgDelete;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPhn = itemView.findViewById(R.id.txtPhn);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }
    }

}

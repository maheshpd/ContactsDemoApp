package com.example.contactslistdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleContactsAdapter extends RecyclerView.Adapter<SimpleContactsAdapter.ContactsViewHolder> {

    Context context;
    ArrayList<ContactsModel> list;

    public SimpleContactsAdapter(Context context, ArrayList<ContactsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item,parent,false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        ContactsModel cm = list.get(position);
        holder.displayname.setText(cm.getDisplayName());
        holder.phoneno.setText(cm.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView displayname,phoneno;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);


            displayname = itemView.findViewById(R.id.contacts_person_name_txt);
            phoneno = itemView.findViewById(R.id.phone_txt);
        }
    }
}

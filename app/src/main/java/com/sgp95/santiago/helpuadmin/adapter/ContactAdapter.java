package com.sgp95.santiago.helpuadmin.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgp95.santiago.helpuadmin.R;
import com.sgp95.santiago.helpuadmin.model.Contacts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactAdapter extends SelectableAdapter<ContactAdapter.ViewHolder> {

    private List<Contacts> contactsList;
    private Context context;
    private MyItemClickListener myItemClickListener;


    public ContactAdapter(RecyclerView recyclerView, List<Contacts> contactsList, Context context) {
        super(recyclerView);
        this.contactsList = contactsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts,parent,false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(contactsList.get(position));
        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder{
        @BindView(R.id.contact_row_name)
        TextView fullName;

        @BindView(R.id.contact_row_cargo)
        TextView txtCargo;

        @BindView(R.id.contact_row_email)
        TextView txtEmail;

        @BindView(R.id.contact_row_phone01)
        TextView txtPhone01;

        @BindView(R.id.opt_phone02)
        TextView txtOptPhone02;

        @BindView(R.id.contact_row_phone02)
        TextView txtPhone02;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(Contacts contacts){

            fullName.setText(contacts.getLastName()+", "+contacts.getFirstName());
            txtCargo.setText(contacts.getCargo());
            txtEmail.setText(contacts.getEmail());
            txtPhone01.setText(contacts.getTelefono01());
            if (contacts.getTelefono02()!=null){
                txtPhone02.setText(contacts.getTelefono02());
            } else {
                txtOptPhone02.setVisibility(View.GONE);
            }
        }
    }

    public interface MyItemClickListener {
        void onItemClick(Contacts contacts);
    }
}

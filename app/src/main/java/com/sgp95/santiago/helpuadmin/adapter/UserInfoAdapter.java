package com.sgp95.santiago.helpuadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgp95.santiago.helpuadmin.R;
import com.sgp95.santiago.helpuadmin.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoAdapter extends SelectableAdapter<UserInfoAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;
    private MyItemClickListener myItemClickListener;

    public UserInfoAdapter(RecyclerView recyclerView, List<User> userList, Context context) {
        super(recyclerView);
        this.userList = userList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_user,parent,false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(userList.get(position));
        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder{

        @BindView(R.id.info_user_row_image)
        ImageView imgUser;

        @BindView(R.id.info_user_row_name)
        TextView txtName;

        @BindView(R.id.info_user_row_code)
        TextView txtCode;

        @BindView(R.id.info_user_row_email)
        TextView txtEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(User user){
            Picasso.with(context)
                    .load(user.getImage())
                    .resize(80,80)
                    .into(imgUser);
            txtCode.setText(user.getCode());
            txtEmail.setText(user.getEmail());
            txtName.setText(user.getLastName()+", "+user.getFirstName());
        }
    }

    public interface MyItemClickListener {
        void onItemClick(User user);
    }
}

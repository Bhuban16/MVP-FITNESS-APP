package com.example.mvpfitnessapp;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private Context mContext;
    private List<UploadAdmin> mUploads;
    private OnItemClickListener mListener;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;

    public ImageAdapter(Context context, List<UploadAdmin> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(itemView);
    }


        @Override
        public void onBindViewHolder (@NonNull ImageViewHolder holder,int position){
            UploadAdmin uploadCurrent = mUploads.get(position);
            holder.textViewName.setText(uploadCurrent.getmName());
            holder.textViewName2.setText(uploadCurrent.getmName2());
            holder.textViewName3.setText(uploadCurrent.getmName3());
            holder.menu.setText(String.valueOf("Menu Option " + (position + 1)));
            Picasso.get()
                    .load(uploadCurrent.getmImageuri())
                    .fit()

                    .into(holder.imageView);
            Picasso.get()
                    .load(uploadCurrent.getmImageuri2())
                    .fit()

                    .into(holder.imageView2);
            Picasso.get()
                    .load(uploadCurrent.getmImageuri3())
                    .fit()

                    .into(holder.imageView3);

            ((ImageViewHolder)holder).delete.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        int position = holder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }


            });
            ((ImageViewHolder)holder).card.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        int position = holder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }


            });

        }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewName , textViewName2 , textViewName3 , menu ;
        public ImageView imageView , imageView2 , imageView3 , delete, edit;
        public LinearLayout card;


        public ImageViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewName2 = itemView.findViewById(R.id.text_view_name2);
            textViewName3 = itemView.findViewById(R.id.text_view_name3);
            imageView = itemView.findViewById(R.id.image_view_upload);
            imageView2= itemView.findViewById(R.id.image_view_upload2);
            imageView3= itemView.findViewById(R.id.image_view_upload3);
            card = itemView.findViewById(R.id.card);
            menu = itemView.findViewById(R.id.menu);
            delete = itemView.findViewById(R.id.delete);
            edit =itemView.findViewById(R.id.edit);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {


            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");
            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onWhatEverClick(int position);
        void onDeleteClick(int position);
    }

    private String getDateToday(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String today = dateFormat.format(date);
        return today;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }




        }









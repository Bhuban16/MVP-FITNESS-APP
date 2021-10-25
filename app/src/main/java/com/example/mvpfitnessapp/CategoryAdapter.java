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

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ImageViewHolder> {
    private Context mContext;
    private List<FirebaseCategory> mUploads;
    private CategoryAdapter.OnItemClickListener mListener;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;

    public CategoryAdapter(Context context, List<FirebaseCategory> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public CategoryAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.category_item, parent, false);
        return new CategoryAdapter.ImageViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder (@NonNull CategoryAdapter.ImageViewHolder holder, int position){
       FirebaseCategory uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getmTitle());
        Picasso.get()
                .load(uploadCurrent.getmBanner())
                .fit()

                .into(holder.imageView);


        ((CategoryAdapter.ImageViewHolder)holder).delete.setOnClickListener(new View.OnClickListener() {


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
        ((CategoryAdapter.ImageViewHolder)holder).card.setOnClickListener(new View.OnClickListener() {


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
        ((CategoryAdapter.ImageViewHolder)holder).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onWhatEverClick(position);
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

        public TextView textViewName;
        public ImageView imageView , delete, edit;
        public LinearLayout card;


        public ImageViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            card = itemView.findViewById(R.id.card);
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


    public void setOnItemClickListener(CategoryAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


}

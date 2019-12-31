package Adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.sc_147.healthmate.R;

import java.util.ArrayList;
import java.util.List;

import Interface.onItemClick;
import Model.GetHospitalList_ResModel;

/**
 * Created by sc-147 on 10-Mar-18.
 */

public class Adapter_HospitalList extends RecyclerView.Adapter<Adapter_HospitalList.ViewHolder> implements Filterable {
    private List<GetHospitalList_ResModel> mArrayList;
    private List<GetHospitalList_ResModel> mFilteredList;
    private onItemClick onItemClick;

    public Adapter_HospitalList(List<GetHospitalList_ResModel> arrayList, onItemClick onItemClick) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_layout_hospital, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        viewHolder.textViewHospitalName.setText(mFilteredList.get(i).getHospitalname());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.ItemClicked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredList = mArrayList;
                } else {
                    List<GetHospitalList_ResModel> filteredList = new ArrayList<>();
                    for (GetHospitalList_ResModel GetHospitalList_ResModel : mArrayList) {
                        if (GetHospitalList_ResModel.getHospitalname().toLowerCase().contains(charString)) {
                            filteredList.add(GetHospitalList_ResModel);
                        }
                    }
                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHospitalName;

        ViewHolder(View view) {
            super(view);
            textViewHospitalName = view.findViewById(R.id.textView_HospitalName);
        }
    }
}

package Adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sc_147.healthmate.R;

import java.util.List;

import Interface.onItemClick;
import Model.GetDoctorList_ResModel;

/**
 * Created by sc-147 on 10-Mar-18.
 */

public class Adapter_DoctorList extends RecyclerView.Adapter<Adapter_DoctorList.ViewHolder> {
    private List<GetDoctorList_ResModel> mArrayList;
    private Interface.onItemClick onItemClick;

    public Adapter_DoctorList(List<GetDoctorList_ResModel> arrayList, onItemClick onItemClick) {
        mArrayList = arrayList;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_layout_doctor, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        String doctorName = mArrayList.get(i).getFirstname() + " " + mArrayList.get(i).getLastname();
        String speciality = "Speciality - " + mArrayList.get(i).getSpeciality1();

        viewHolder.textViewDoctorName.setText(doctorName);
        viewHolder.textViewSpeciaLity.setText(speciality);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.ItemClicked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDoctorName;
        TextView textViewSpeciaLity;

        ViewHolder(View view) {
            super(view);
            textViewDoctorName = view.findViewById(R.id.textView_doctorName);
            textViewSpeciaLity = view.findViewById(R.id.textView_speciaLity);
        }
    }
}
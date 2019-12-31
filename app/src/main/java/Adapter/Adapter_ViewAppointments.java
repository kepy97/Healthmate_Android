package Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sc_147.healthmate.R;

import java.util.List;

import Model.GetViewAppointments_ResModel;

/**
 * Created by sc-147 on 2018-03-23.
 */

public class Adapter_ViewAppointments extends RecyclerView.Adapter<Adapter_ViewAppointments.ViewHolder> {
    private List<GetViewAppointments_ResModel> mysppointments;

    public Adapter_ViewAppointments(List<GetViewAppointments_ResModel> mysppointments) {
        this.mysppointments = mysppointments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardViewAppointmentnumber.setVisibility(View.VISIBLE);
        holder.cardViewPatient.setVisibility(View.VISIBLE);
        String appointno = String.valueOf(position + 1);
        holder.textViewAppointmentnumber.setText(appointno);
        String Patient = mysppointments.get(position).getLoginId().getUserdetailsCollection().get(0).getFirstname() + " " + mysppointments.get(position).getLoginId().getUserdetailsCollection().get(0).getLastname();
        holder.textViewPatient.setText(Patient);
        holder.textViewHospitalname.setText(mysppointments.get(position).getHospitalId().getHospitalname());
        String doctorname = mysppointments.get(position).getDoctorId().getFirstname() + " " + mysppointments.get(position).getDoctorId().getLastname();
        holder.textViewDoctorname.setText(doctorname);
        holder.textViewDate.setText(mysppointments.get(position).getDate());
        holder.textViewTime.setText(mysppointments.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mysppointments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHospitalname;
        TextView textViewDoctorname;
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewAppointmentnumber;
        TextView textViewPatient;
        CardView cardViewAppointmentnumber;
        CardView cardViewPatient;

        ViewHolder(View itemView) {
            super(itemView);
            textViewHospitalname = itemView.findViewById(R.id.textView_hospitalname);
            textViewDoctorname = itemView.findViewById(R.id.textView_doctorname);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewTime = itemView.findViewById(R.id.textView_time);
            textViewAppointmentnumber = itemView.findViewById(R.id.textView_appointmentnumber);
            textViewPatient = itemView.findViewById(R.id.textView_patient);
            cardViewAppointmentnumber = itemView.findViewById(R.id.cardView_appointmentNo);
            cardViewPatient = itemView.findViewById(R.id.cardView_patientNM);
        }
    }
}

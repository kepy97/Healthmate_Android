package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sc-147 on 2018-03-23.
 */

public class GetMyAppointments_ResModel {
    @SerializedName("doctorId")
    @Expose
    private DoctorId doctorId;
    @SerializedName("hospitalId")
    @Expose
    private HospitalId hospitalId;
    @SerializedName("loginId")
    @Expose
    private LoginId loginId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

    public DoctorId getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(DoctorId doctorId) {
        this.doctorId = doctorId;
    }

    public HospitalId getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(HospitalId hospitalId) {
        this.hospitalId = hospitalId;
    }

    public LoginId getLoginId() {
        return loginId;
    }

    public void setLoginId(LoginId loginId) {
        this.loginId = loginId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public class DoctorId {

        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("speciality1")
        @Expose
        private String speciality1;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getSpeciality1() {
            return speciality1;
        }

        public void setSpeciality1(String speciality1) {
            this.speciality1 = speciality1;
        }

    }

    public class HospitalId {

        @SerializedName("hospitalname")
        @Expose
        private String hospitalname;

        public String getHospitalname() {
            return hospitalname;
        }

        public void setHospitalname(String hospitalname) {
            this.hospitalname = hospitalname;
        }

    }

    public class LoginId {

        @SerializedName("emailid")
        @Expose
        private String emailid;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("userdetailsCollection")
        @Expose
        private List<UserdetailsCollection> userdetailsCollection = null;

        public String getEmailid() {
            return emailid;
        }

        public void setEmailid(String emailid) {
            this.emailid = emailid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public List<UserdetailsCollection> getUserdetailsCollection() {
            return userdetailsCollection;
        }

        public void setUserdetailsCollection(List<UserdetailsCollection> userdetailsCollection) {
            this.userdetailsCollection = userdetailsCollection;
        }

    }

    public class UserdetailsCollection {

        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

    }
}

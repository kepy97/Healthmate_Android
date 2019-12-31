package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sc-147 on 09-Mar-18.
 */

public class GetHospitalList_ResModel {
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

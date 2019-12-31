package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sc-147 on 2018-03-16.
 */

public class GetTimeSlot_ResModel {
    @SerializedName("time")
    @Expose
    private List<String> time = null;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }
}

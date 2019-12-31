package Interface;

import java.util.List;

import Model.BookAppointment_ResModel;
import Model.GetCityArea_ResModel;
import Model.GetDoctorList_ResModel;
import Model.GetHospitalList_ResModel;
import Model.GetMyAppointments_ResModel;
import Model.GetTimeSlot_ResModel;
import Model.GetViewAppointments_ResModel;
import Model.LoginSignup_ResModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sc-147 on 08-Mar-18.
 */

public interface APIManager {
    //URL end point
    String login = "user/login";
    String signup = "user/signup";
    String city = "location/city";
    String area = "location/area";
    String hospital_name = "hospital/name";
    String doctor_name = "hospital/doctor";
    String appointment_time = "appointment/time";
    String book_appointment = "appointment/bookappointment";
    String my_appointments = "appointment/myappointment";
    String view_appointments = "appointment/viewappointment";

    //Parameter
    String email_query = "emailid", password_query = "password";

    String fname_query = "fname", lname_query = "lname", mobile_query = "mobile";

    String state_query = "state", city_query = "city", area_query = "area", hospital_query = "hospital";

    String date_query = "date", doctorname_query = "doctorname";

    String doctor_query = "doctor", time_query = "time";

    @GET(login)
    Call<List<LoginSignup_ResModel>> getLoginResult(@Query(email_query) String _email, @Query(password_query) String _queryassword);

    @GET(signup)
    Call<List<LoginSignup_ResModel>> getSignupResult(@Query(email_query) String _email, @Query(password_query) String _queryassword, @Query(fname_query) String _fname, @Query(lname_query) String _lname, @Query(mobile_query) String _mobile);

    @GET(city)
    Call<List<GetCityArea_ResModel>> getCityListResult(@Query(state_query) String _state);

    @GET(area)
    Call<List<GetCityArea_ResModel>> getAreaListResult(@Query(city_query) String _city);

    @GET(hospital_name)
    Call<List<GetHospitalList_ResModel>> getHospitalListResult(@Query(area_query) String _area);

    @GET(doctor_name)
    Call<List<GetDoctorList_ResModel>> getDoctorListResult(@Query(hospital_query) String _hospital);

    @GET(appointment_time)
    Call<GetTimeSlot_ResModel> getAppointmentTimeResult(@Query(date_query) String _doctor, @Query(doctorname_query) String _doctorname);

    @GET(book_appointment)
    Call<List<BookAppointment_ResModel>> getBookAppointmentResult(@Query(email_query) String _emailid, @Query(date_query) String _date, @Query(doctor_query) String _doctor, @Query(time_query) String _time, @Query(hospital_query) String _hospital);

    @GET(my_appointments)
    Call<List<GetMyAppointments_ResModel>> getMyAppointmentResult(@Query(email_query) String _emailid);

    @GET(view_appointments)
    Call<List<GetViewAppointments_ResModel>> getViewAppointmentResult(@Query(email_query) String _emailid);
}

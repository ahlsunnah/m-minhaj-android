package raslan.learn.islam.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.apollographql.apollo.sample.GetCourseDataQuery;

import java.util.List;

public class DataHelper implements Parcelable {
    private List<GetCourseDataQuery.Edge> data;

    public DataHelper(List<GetCourseDataQuery.Edge> data){
        this.data = data;
    }

    protected DataHelper(Parcel in) {
    }

    public static final Creator<DataHelper> CREATOR = new Creator<DataHelper>() {
        @Override
        public DataHelper createFromParcel(Parcel in) {
            return new DataHelper(in);
        }

        @Override
        public DataHelper[] newArray(int size) {
            return new DataHelper[size];
        }
    };

    public List<GetCourseDataQuery.Edge> getData() {
        return data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}

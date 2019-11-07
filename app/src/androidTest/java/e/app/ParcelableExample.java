package e.app;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableExample implements Parcelable {

    String myString;

    public ParcelableExample(String myString) {
        this.myString = myString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.myString);
    }

    public static final Parcelable.Creator<ParcelableExample> CREATOR =
            new Parcelable.Creator<ParcelableExample>(){

                @Override
                public ParcelableExample createFromParcel(Parcel source) {
                    return new ParcelableExample(source);
                }

                @Override
                public ParcelableExample[] newArray(int size) {
                    return new ParcelableExample[size];
                }
            };

    private ParcelableExample(Parcel in) {
        String temp = in.readString();
        this.myString = temp;
    }

}

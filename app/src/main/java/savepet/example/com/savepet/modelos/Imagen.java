package savepet.example.com.savepet.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Imagen implements Parcelable {
    private Integer id,animal_id;
    private String path;

    public Imagen(Integer id, Integer animal_id, String path) {
        this.id = id;
        this.animal_id = animal_id;
        this.path = path;
    }

    public Imagen() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(Integer animal_id) {
        this.animal_id = animal_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.animal_id);
        dest.writeString(this.path);
    }

    protected Imagen(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.animal_id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.path = in.readString();
    }

    public static final Parcelable.Creator<Imagen> CREATOR = new Parcelable.Creator<Imagen>() {
        @Override
        public Imagen createFromParcel(Parcel source) {
            return new Imagen(source);
        }

        @Override
        public Imagen[] newArray(int size) {
            return new Imagen[size];
        }
    };
}

package ted.rental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import ted.rental.database.entities.PictureEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Picture {

    private Integer picture_id;

    @JsonIgnore
    private File thumbnail;

    @JsonIgnore
    private File medium;

    @JsonIgnore
    private File picture;


    public Picture() {

    }

    public Picture(Integer picture_id, File thumbnail, File medium, File picture) {
        this.picture_id = picture_id;
        this.thumbnail = thumbnail;
        this.medium = medium;
        this.picture = picture;
    }

    public Integer getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(Integer picture_id) {
        this.picture_id = picture_id;
    }

    public File getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(File thumbnail) {
        this.thumbnail = thumbnail;
    }

    public File getMedium() {
        return medium;
    }

    public void setMedium(File medium) {
        this.medium = medium;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "picture_id=" + picture_id +
                ", thumbnail=" + thumbnail +
                ", medium=" + medium +
                ", picture=" + picture +

                '}';
    }

    public PictureEntity toEntity(){
        return new PictureEntity(this.thumbnail.toEntity(), this.medium.toEntity(),
                this.picture.toEntity());
    }
}

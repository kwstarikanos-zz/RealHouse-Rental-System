package ted.rental.database.entities;

import org.eclipse.jetty.util.annotation.Name;
import ted.rental.model.Picture;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@NamedQueries({
        @NamedQuery(name = "pictures.findById", query = "SELECT p FROM PictureEntity p where p.picture_id =:id"),
        @NamedQuery(name = "pictures.deleteById", query = "DELETE FROM PictureEntity p where p.picture_id=:id")
})
public class PictureEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer picture_id;

    /*                                                                           */
      /*   Σχέση  Ένα-Προς-Ένα ((PictureEntity))-((ROOMENTITY_PICTUREENTITY))      */
     /*                                                                           */
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, mappedBy = "pictureEntity")
    private ROOMENTITY_PICTUREENTITY roomentity_pictureentity;

    public ROOMENTITY_PICTUREENTITY getRoomentity_pictureentity() {
        return roomentity_pictureentity;
    }

    public void setRoomentity_pictureentity(ROOMENTITY_PICTUREENTITY roomentity_pictureentity) {
        this.roomentity_pictureentity = roomentity_pictureentity;
    }


    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "thumbnail", referencedColumnName = "id")
    private FileEntity thumbnail;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "medium", referencedColumnName = "id")
    private FileEntity medium;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "picture", referencedColumnName = "id")
    private FileEntity picture;


    public PictureEntity() {

    }

    public PictureEntity(FileEntity thumbnail, FileEntity medium, FileEntity picture) {
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

    public FileEntity getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(FileEntity thumbnail) {
        this.thumbnail = thumbnail;
    }

    public FileEntity getMedium() {
        return medium;
    }

    public void setMedium(FileEntity medium) {
        this.medium = medium;
    }

    public FileEntity getPicture() {
        return picture;
    }

    public void setPicture(FileEntity picture) {
        this.picture = picture;
    }


    public Picture toModel() {
        return new Picture(this.picture_id, this.thumbnail == null ? null : this.thumbnail.toModel(), this.medium == null ? null : this.medium.toModel(),
                this.picture == null ? null : this.picture.toModel());
    }
}

package ted.rental.database.entities;

import ted.rental.model.File;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class FileEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Basic
    @Column(name = "filename", nullable = false, length = 255)
    private String filename;

    @Basic
    @Column(name = "filetype", nullable = false, length = 20)
    private String filetype;

    @Basic
    @Column(name = "filesize", nullable = false)
    private Integer filesize;

    @Basic
    @Lob
    @Column(name = "base64", nullable = false, length = 2000000)
    private byte[] base64;


    public FileEntity() {

    }

    public FileEntity(String filename, String filetype, Integer filesize, String base64) {
        this.filename = filename;
        this.filetype = filetype;
        this.filesize = filesize;
        this.base64 = base64.getBytes();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public byte[] getBase64() {
        return base64;
    }

    public void setBase64(byte[] base64) {
        this.base64 = base64;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", filetype='" + filetype + '\'' +
                ", filesize='" + filesize + "\'" +
                ", base64='" + Arrays.toString(base64).substring(0, 10) + "...\'" +
                '}';
    }

    public File toModel(){
        return new File(this.id, this.filename, this.filetype, this.filesize, this.base64);
    }

}

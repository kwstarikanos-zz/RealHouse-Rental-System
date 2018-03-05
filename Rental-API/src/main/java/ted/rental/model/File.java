package ted.rental.model;

import ted.rental.database.entities.FileEntity;

public class File {

    private Integer id;

    private String filename;

    private String filetype;

    private Integer filesize;

    private String base64;

    public File() {
    }

    public File(Integer id, String filename, String filetype, Integer filesize, byte[] base64) {
        this.id = id;
        this.filename = filename;
        this.filetype = filetype;
        this.filesize = filesize;
        this.base64 = new String(base64);
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

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", filetype='" + filetype + '\'' +
                ", filesize=" + filesize +
                ", base64='" + base64.substring(0, 20) + "...\'" +
                ", base64.getBytes().length=" + base64.getBytes().length +
                '}';
    }

    public FileEntity toEntity(){
        return new FileEntity(this.filename, this.filetype, this.filesize, this.base64);
    }
}

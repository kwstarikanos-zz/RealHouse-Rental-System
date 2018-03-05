package ted.rental.model.outputs;


import ted.rental.model.Picture;

public class ProfilePictureAndOwner {

    String owner;

    Picture picture;

    public ProfilePictureAndOwner() {
    }

    public ProfilePictureAndOwner(String owner, Picture picture) {
        this.owner = owner;
        this.picture = picture;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}

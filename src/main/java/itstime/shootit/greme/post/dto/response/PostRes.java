package itstime.shootit.greme.post.dto.response;

public interface PostRes {
    Long getId();
    String getImage();
    String getContent();
    String getHashtag();
    boolean getStatus();
}

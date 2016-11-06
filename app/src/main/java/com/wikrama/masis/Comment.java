package com.wikrama.masis;

import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by Naufal Prakoso on 11/5/2016.
 */
@IgnoreExtraProperties
public class Comment {

    public String uid;
    public String author;
    public String text;

    public Comment() {
    }

    public Comment(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }

}
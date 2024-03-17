package com.example.tuitionfinder;

import android.net.Uri;

import java.util.ArrayList;

public class TuitionImage {
    String ImageLink;

    public TuitionImage() {
    }

    public TuitionImage(String imageLink) {
        ImageLink = imageLink;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }
}

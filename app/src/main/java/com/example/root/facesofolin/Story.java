package com.example.root.facesofolin;

/**
 * Created by root on 11/10/14.
 */



public class Story {
    private String title;
    private String location;
    private String story_text;
    private String date;
    private String image_url;
    private String image_caption;

    public Story(String title, String location, String story_text, String date, String image_url, String image_caption) {
        this.title = title;
        this.location = location;
        this.story_text = story_text;
        this.date = date;
        this.image_url = image_url;
        this.image_caption = image_caption;
    }

    public String get_title() {
        return title;
    }

    public String get_location() {
        return location;
    }

    public String get_storytext() {
        return story_text;
    }

    public String get_date() {
        return date;
    }

    public String get_image_url() { return image_url; }


    public String get_image_caption() { return image_caption; }
}

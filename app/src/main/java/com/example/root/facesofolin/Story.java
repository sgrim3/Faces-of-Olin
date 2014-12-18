package com.example.root.facesofolin;

/**
 * Created by root on 11/10/14.
 */

public class Story {
    // Encodes all the information for a typical story
    private String time;
    private String location;
    private String story_text;
    private String story_title;
    private String image_url;
    private String image_caption;
    private String author;

    public Story(String time, String location, String story_text, String story_title, String image_url, String image_caption, String author) {
        this.time = time;
        this.location = location;
        this.story_text = story_text;
        this.story_title = story_title;
        this.image_url = image_url;
        this.image_caption = image_caption;
        this.author = author;
    }

    public String get_time() {
        return time;
    }

    public String get_location() {
        return location;
    }

    public String get_storytext() {
        return story_text;
    }

    public String get_storytitle() {
        return story_title;
    }

    public String get_image_url() { return image_url; }

    public String get_image_caption() { return image_caption; }

    public String get_author() { return author; }
}

package com.example.Observer.models;

import com.example.Observer.base.Subjects;

public class VideoData extends Subjects {
    private String title;
    private String description;
    private String fileName;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        videoDataChanged();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        videoDataChanged();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        videoDataChanged();
    }

    // Trigger notify when data changes
    private void videoDataChanged() {
        notifyObservers(this, null);
    }
}

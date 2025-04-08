package com.example.Observer.notifiers;

import com.example.Observer.base.Observer;
import com.example.Observer.base.Subjects;
import com.example.Observer.models.VideoData;

public class FacebookNotifiers extends Observer {
    public FacebookNotifiers(Subjects subjects) {
        this.subjects = subjects;
        this.subjects.attachObservers(this);
    }

    @Override
    public void notify(Subjects subjects, Object data) {
        if (subjects instanceof VideoData videoData) {// so sanh type object
            System.out.println("Notify all subscribers via FACEBOOK with new data: " + "\n\tName: "
                    + videoData.getTitle() + "\n\tDescription: "
                    + videoData.getDescription() + "\n\tFileName: "
                    + videoData.getFileName());
        }
    }
}

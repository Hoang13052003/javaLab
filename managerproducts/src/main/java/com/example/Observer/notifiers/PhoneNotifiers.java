package com.example.Observer.notifiers;

import com.example.Observer.base.Observer;
import com.example.Observer.base.Subjects;
import com.example.Observer.models.VideoData;

public class PhoneNotifiers extends Observer {
    public PhoneNotifiers(Subjects subjects) {
        this.subjects = subjects;
        this.subjects.attachObservers(this);
    }

    @Override
    public void notify(Subjects subjects, Object data) {
        if (subjects instanceof VideoData videoData) {// so sanh type object
            System.out.println("Notify all subscribers via PHONE NUMBER with new data: " + "\n\tName: "
                    + videoData.getTitle() + "\n\tDescription: "
                    + videoData.getDescription() + "\n\tFileName: "
                    + videoData.getFileName());
        }
    }
}

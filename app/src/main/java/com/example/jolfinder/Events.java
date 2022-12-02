package com.example.jolfinder;

import android.graphics.drawable.Drawable;

public class Events {

    private String eventN,totalDistance, image;


    public Events(String eventN,String totalDistance, String image){
        this.setEventN(eventN);
        this.setTotalDistance(totalDistance);
        this.setImage(image);

    }
    public String getImage(){return image;}

    public void setImage(String image){this.image = image;}

    public String getEventN() {
        return eventN;
    }

    public void setEventN(String eventN) {
        this.eventN = eventN;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }


}

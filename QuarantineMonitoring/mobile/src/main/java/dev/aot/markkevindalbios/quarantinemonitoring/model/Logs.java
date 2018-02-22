package dev.aot.markkevindalbios.quarantinemonitoring.model;

import java.util.Date;

/**
 * Created by mark.kevin.d.albios on 2/21/2018.
 */

public class Logs {
    private String timeStamp;
    private boolean status; // 0 - Not Around | 1 - Present

    public Logs(){}

    public Logs(String timeStamp, boolean status) {
        this.timeStamp = timeStamp;
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

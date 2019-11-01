package com.example.iiitdconnect;

import java.io.Serializable;
import java.util.Map;

public class interested implements Serializable {
    private Map<String, String> interested_ids;

    public Map<String, String> getInterested_ids() {
        return interested_ids;
    }

    public void setInterested_ids(Map<String, String> interested_ids) {
        this.interested_ids = interested_ids;

    }

    public interested(Map<String, String> interested_ids){
        this.interested_ids = interested_ids;
        this.interested_ids.put("temp_id", "temp_id");
    }
    public interested(){}

}

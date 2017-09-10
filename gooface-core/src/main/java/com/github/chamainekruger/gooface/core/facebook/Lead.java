package com.github.chamainekruger.gooface.core.facebook;

import com.restfb.Facebook;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

/**
 * POJO that represent a Facebook Lead
 * @author Charmaine Kruger(charmaine.kruger@gmail.com)
 */
@Log
@Data @AllArgsConstructor @NoArgsConstructor
public class Lead {
    @Facebook("created_time")
    private String createdTime;
    @Facebook("id")
    private Long id;
    @Facebook("field_data")
    private List<FieldData> fieldData;
    
    public Date getCreateDate(){
        if(this.createdTime!=null){
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            try {
                return sdf.parse(this.createdTime);
            } catch (ParseException ex) {
                log.severe(ex.getMessage());
                return null;
            }
        }
        return null;
    }
    
                                               //2017-09-10T16:52:05+0000
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
}

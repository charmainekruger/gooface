package com.github.chamainekruger.gooface.common;

import com.restfb.Facebook;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO that represent a facebook campaign
 * @author Charmaine Kruger(charmaine.kruger@gmail.com)
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class Campaign implements Serializable {
    
    @Facebook("id")
    private Long id;
    @Facebook("leadgen_export_csv_url")
    private String leadgenExportCsvUrl;
    @Facebook("locale")
    private String locale;
    @Facebook("name")
    private String name;
    @Facebook("status")
    private String status;
}

package com.github.chamainekruger.gooface.core.facebook;

import com.restfb.Facebook;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO that represent Field data
 * @author Charmaine Kruger(charmaine.kruger@gmail.com)
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class FieldData {
    @Facebook("name")
    private String name;
    @Facebook("values")
    private List<String> values;
}

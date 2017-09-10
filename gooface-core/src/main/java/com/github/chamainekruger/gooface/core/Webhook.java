package com.github.chamainekruger.gooface.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent a Webhook from Facebook
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class Webhook {
    private Long leadId;
    private Long formId;
}

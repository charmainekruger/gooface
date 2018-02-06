package com.github.chamainekruger.gooface.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent a event
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class CampaignLeadEvent implements Serializable {
    
    private String reference;
    private Campaign campaign;
    private Lead lead;
}

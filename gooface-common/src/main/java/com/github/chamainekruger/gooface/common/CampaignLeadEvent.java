package com.github.chamainekruger.gooface.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent a event
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class CampaignLeadEvent {
    private String reference;
    private Campaign campaign;
    private Lead lead;
}

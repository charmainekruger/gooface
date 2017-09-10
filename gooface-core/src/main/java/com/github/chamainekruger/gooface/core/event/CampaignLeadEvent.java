package com.github.chamainekruger.gooface.core.event;

import com.github.chamainekruger.gooface.core.facebook.Campaign;
import com.github.chamainekruger.gooface.core.facebook.Lead;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent a event
 * @author Charmaine Kruger (charmaine.kruger@gmail.com)
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class CampaignLeadEvent {
    private Campaign campaign;
    private Lead lead;
}

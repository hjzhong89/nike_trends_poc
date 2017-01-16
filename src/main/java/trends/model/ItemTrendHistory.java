package trends.model;

import java.util.Date;

/**
 * Model of an Item's Trend history
 *
 * @author Hok-Ming J. Zhong
 * @verson 0.0.1
 */
public class ItemTrendHistory {

    private NikeItem item;
    private Date firstAppearance;

    public NikeItem getItem() {
        return item;
    }

    public void setItem(NikeItem item) {
        this.item = item;
    }

    public Date getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(Date firstAppearance) {
        this.firstAppearance = firstAppearance;
    }
}

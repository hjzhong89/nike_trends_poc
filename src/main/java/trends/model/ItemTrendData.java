package trends.model;

import java.util.Date;

/**
 * Model of an items trend data.
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
public class ItemTrendData {

    private NikeItem item;
    private Date startDate;
    private Date endDate;
    private Integer mentions;
    private String status;

    public ItemTrendData() {

    }

    public NikeItem getItem() {
        return item;
    }

    public void setItem(NikeItem item) {
        this.item = item;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getMentions() {
        return mentions;
    }

    public void setMentions(Integer mentions) {
        this.mentions = mentions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package trends.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import trends.data.NikeItemDataAccessObject;
import trends.exception.DataException;
import trends.model.ItemTrendData;
import trends.model.ItemTrendHistory;
import trends.model.ItemType;
import trends.model.NikeItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Trend data aggregator from a JSON Array source
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
public class JsonDataAggregator implements DataAggregatorService {
    private SimpleDateFormat formatter;
    private JSONArray jsonTrendData;
    private List<NikeItemDataPoint> trendData;

    private JSONArray jsonTrendHistories;
    private Map<String, ItemTrendHistory> trendHistories;

    @Autowired
    private NikeItemDataAccessObject dao;

    public JsonDataAggregator() {
         formatter = new SimpleDateFormat("yyyy-MM-dd");
    }
    public JSONArray getJsonTrendData() {
        return jsonTrendData;
    }

    /**
     * Convert the JSONArray to a list of NikeItemDataPoints
     *
     * @param jsonTrendData
     * @throws DataException
     */
    public void setJsonTrendData(JSONArray jsonTrendData) throws DataException {
        this.jsonTrendData = jsonTrendData;
        Iterator<JSONObject> iterator = jsonTrendData.iterator();
        List<NikeItemDataPoint> trendData = new ArrayList<>();

        try {
            while (iterator.hasNext()) {
                JSONObject jsonItem = iterator.next();
                NikeItemDataPoint datapoint = new NikeItemDataPoint();
                NikeItem nikeItem = dao.getItemById(((Long) jsonItem.get("itemId")).intValue());
                Date date = formatter.parse((String) jsonItem.get("date"));
                datapoint.setNikeItem(nikeItem);
                datapoint.setDate(date);
                datapoint.setMentions(((Long) jsonItem.get("mentions")).intValue());

                trendData.add(datapoint);
            }

            this.trendData = trendData;
        } catch (ParseException ex) {
            throw new DataException(ex);
        }
    }

    public JSONArray getJsonTrendHistories() {
        return jsonTrendHistories;
    }

    /**
     * Convert the JSONArray to a list of ItemTrendHistory objects
     * @param jsonTrendHistories
     */
    public void setJsonTrendHistories(JSONArray jsonTrendHistories) throws DataException {
        this.jsonTrendHistories = jsonTrendHistories;
        Iterator<JSONObject> iterator = jsonTrendHistories.iterator();
        Map<String, ItemTrendHistory> trendHistories = new HashMap<>();

        try {
            while (iterator.hasNext()) {
                JSONObject jsonItem = iterator.next();
                NikeItem nikeItem = dao.getItemById(((Long) jsonItem.get("itemId")).intValue());
                Date firstAppearance = formatter.parse((String) jsonItem.get("firstAppearedDate"));
                ItemTrendHistory itemHistory = new ItemTrendHistory();
                itemHistory.setItem(nikeItem);
                itemHistory.setFirstAppearance(firstAppearance);
                trendHistories.put(nikeItem.getName(), itemHistory);
            }

            this.trendHistories = trendHistories;
        } catch (ParseException ex) {
            throw new DataException();
        }
    }

    public NikeItemDataAccessObject getDao() {
        return dao;
    }

    public void setDao(NikeItemDataAccessObject dao) {
        this.dao = dao;
    }

    /**
     * Aggregates all trend data from the start and end dates. Updates the Trend Item History
     * file.
     *
     * @param startDate Start date
     * @param endDate   End date
     * @return List of ItemTrendData
     */
    @Override
    public List<ItemTrendData> aggregateAllItems(Date startDate, Date endDate) {
        Map<NikeItem, Integer> itemMentionsMap = new HashMap<>();

        trendData.forEach(dataPoint -> {
            if (compareDates(dataPoint.getDate(), startDate) >= 0
                    && compareDates(dataPoint.getDate(), endDate) <= 0) {
                if (itemMentionsMap.containsKey(dataPoint.getNikeItem())) {
                    Integer mentions = itemMentionsMap.get(dataPoint.getNikeItem()) + dataPoint.getMentions();
                    itemMentionsMap.put(dataPoint.getNikeItem(), mentions);
                } else {
                    itemMentionsMap.put(dataPoint.getNikeItem(), dataPoint.getMentions());
                }
            }
        });

        List<ItemTrendData> trendData = new ArrayList<>();
        Date currentDate = new Date();
        Boolean checkStatus = compareDates(currentDate, startDate) >= 0 && compareDates(currentDate, endDate) <= 0;

        for (NikeItem item : itemMentionsMap.keySet()) {
            ItemTrendData itemTrendData = new ItemTrendData();
            itemTrendData.setItem(item);
            itemTrendData.setStartDate(startDate);
            itemTrendData.setEndDate(endDate);
            itemTrendData.setMentions(itemMentionsMap.get(item));

            if (checkStatus) {
                if (!trendHistories.containsKey(item.getName())) {
                    itemTrendData.setStatus("NEW");
                    ItemTrendHistory itemTrendHistory = new ItemTrendHistory();
                    itemTrendHistory.setItem(item);
                    itemTrendHistory.setFirstAppearance(startDate);
                    trendHistories.put(item.getName(), itemTrendHistory);
                } else {
                    ItemTrendHistory itemTrendHistory = trendHistories.get(item.getName());
                    Date firstAppearance = itemTrendHistory.getFirstAppearance();
                    if (compareDates(firstAppearance, startDate) >= 0) {
                        itemTrendData.setStatus("NEW");
                    }
                }
            }
            trendData.add(itemTrendData);
        }



        trendData.sort((item1, item2) -> item2.getMentions() - item1.getMentions());
        return trendData;
    }

    /**
     * Not yet implemented.
     *
     * @param itemName  Name of the Item
     * @param startDate Start Date
     * @param endDate   End Date
     * @return
     */
    @Override
    public ItemTrendData aggregateByItemName(String itemName, Date startDate, Date endDate) {
        return null;
    }

    /**
     * Note Yet implemented.
     *
     * @param type      ItemType
     * @param startDate Start date
     * @param endDate   End Date
     * @return
     */
    @Override
    public List<ItemTrendData> aggregateByItemType(ItemType type, Date startDate, Date endDate) {
        return null;
    }

    /**
     * Utility class to compare dates without time
     * @param d1
     * @param d2
     * @return 0 if equal, positive if d1 is after d2, else negative
     */
    private int compareDates(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) {
            return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        }

        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) {
            return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
        }

        return c1.get(Calendar.DATE) - c2.get(Calendar.DATE);
    }

    /**
     * Helper class for single data points of a trending item.
     */
    private class NikeItemDataPoint {
        private NikeItem nikeItem;
        private Date date;
        private Integer mentions;

        public NikeItem getNikeItem() {
            return nikeItem;
        }

        public void setNikeItem(NikeItem nikeItem) {
            this.nikeItem = nikeItem;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Integer getMentions() {
            return mentions;
        }

        public void setMentions(Integer mentions) {
            this.mentions = mentions;
        }
    }
}

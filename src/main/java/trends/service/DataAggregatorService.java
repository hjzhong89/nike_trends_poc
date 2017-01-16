package trends.service;

import trends.model.ItemTrendData;
import trends.model.ItemType;

import java.util.Date;
import java.util.List;

/**
 * Service Interface for aggregating data from different sources
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
public interface DataAggregatorService {

    /**
     * Aggregates all trend data for all items for a date range
     *
     * @param startDate Start date
     * @param endDate   End date
     * @return List of ItemTrendData
     */
    List<ItemTrendData> aggregateAllItems(Date startDate, Date endDate);

    /**
     * Aggregates trend data for a single item in a date range
     *
     * @param itemName  Name of the Item
     * @param startDate Start Date
     * @param endDate   End Date
     * @return ItemTrendData for the item
     */
    ItemTrendData aggregateByItemName(String itemName, Date startDate, Date endDate);

    /**
     * Aggregates trend data for all items of a single type
     *
     * @param type      ItemType
     * @param startDate Start date
     * @param endDate   End Date
     * @return List of ItemTrendData for all items of the given type
     */
    List<ItemTrendData> aggregateByItemType(ItemType type, Date startDate, Date endDate);
}

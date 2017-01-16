package trends.data;

import trends.exception.DataException;
import trends.model.ItemType;
import trends.model.NikeItem;

import java.util.List;

/**
 * Interface for retrieving Nike Trend items and data
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
public interface NikeItemDataAccessObject {

    /**
     * Retrieve a NikeItem object by ID
     *
     * @param id
     * @return NikeItem object
     */
    NikeItem getItemById(Integer id);

    /**
     * Retrieve all Nike Item
     * @return List of NikeItem
     */
    List<NikeItem> getAllItems() throws DataException;

    /**
     * Retrieve all Nike Item of an item type
     * @return List of NikeItem
     */
    List<NikeItem> getItemsByType(ItemType type) throws DataException;
}

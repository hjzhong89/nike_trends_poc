package trends.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import trends.exception.DataException;
import trends.model.ItemType;
import trends.model.NikeItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * DAO for JSON data
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
public class JsonDataAccessObject implements NikeItemDataAccessObject {

    private JSONArray jsonItemData;
    private List<NikeItem> nikeItems;

    public JsonDataAccessObject() {
        nikeItems = null;
        jsonItemData = null;
    }

    public JSONArray getJsonItemData() {
        return jsonItemData;
    }

    public void setJsonItemData(JSONArray jsonItemData) {
        this.jsonItemData = jsonItemData;
        Iterator<JSONObject> iterator = jsonItemData.iterator();
        List<NikeItem> items = new ArrayList<>();
        while (iterator.hasNext()) {
            JSONObject jsonItem = iterator.next();
            NikeItem nikeItem = new NikeItem();
            nikeItem.setId(((Long) jsonItem.get("_id")).intValue());
            nikeItem.setName((String) jsonItem.get("name"));
            nikeItem.setItemType((String) jsonItem.get("itemType"));
            items.add(nikeItem);
        }

        this.nikeItems = items;
    }

    @Override
    public NikeItem getItemById(Integer id) {
        if (nikeItems == null || nikeItems.isEmpty()) {
            return null;
        }

        for (NikeItem item : nikeItems) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }

    @Override
    public List<NikeItem> getAllItems() throws DataException {

        return null;
    }

    @Override
    public List<NikeItem> getItemsByType(ItemType type) throws DataException {
        return null;
    }
}

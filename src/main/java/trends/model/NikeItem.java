package trends.model;

/**
 * Basic model of an Item
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
public class NikeItem {

    private Integer id;
    private String name;
    private ItemType itemType;

    public NikeItem() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        switch (itemType) {
            case "SNEAKER":
                this.itemType = ItemType.SNEAKER;
                break;
            case "SHORTS":
                this.itemType = ItemType.SHORTS;
                break;
            case "TOP":
                this.itemType = ItemType.TOP;
                break;
            case "APPAREL":
                this.itemType = ItemType.APPAREL;
                break;
            default:
                this.itemType = ItemType.OTHER;
        }
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}

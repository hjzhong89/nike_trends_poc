package trends.model;

/**
 * Enum of item types.
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
public enum ItemType {

    SNEAKER("SNEAKER"),
    TOP("TOP"),
    SHORTS("SHORTS"),
    APPAREL("APPAREL"),
    OTHER("OTHER");

    private String type;

    ItemType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}

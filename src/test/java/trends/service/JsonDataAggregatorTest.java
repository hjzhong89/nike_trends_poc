package trends.service;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import trends.data.NikeItemDataAccessObject;
import trends.exception.DataException;
import trends.model.ItemTrendData;
import trends.model.NikeItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for JsonDataAggregator class
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
public class JsonDataAggregatorTest {

    private NikeItemDataAccessObject dao;
    private JsonDataAggregator uut;


    @Before
    public void setUp() throws ParseException, DataException {
        dao = Mockito.mock(NikeItemDataAccessObject.class);
        uut = new JsonDataAggregator();
        uut.setDao(dao);
    }

    /**
     * Test the aggregateAllItems function returns all expected NikeItems in descending order of mentions
     */
    @Test
    public void testAggregateAllItems() throws java.text.ParseException, ParseException, DataException {
        JSONParser parser = new JSONParser();
        String strTrendData = "[" +
                "{\"itemId\": 0, \"date\": \"2017-01-12\", \"mentions\": 1000 }," +
                "{\"itemId\": 1, \"date\": \"2017-01-12\", \"mentions\": 2000 }," +
                "{\"itemId\": 2, \"date\": \"2017-01-12\", \"mentions\": 3000 }" +
                "]";
        String strTrendHistories = "[]";

        JSONArray trendData = (JSONArray) parser.parse(strTrendData);
        JSONArray trendHistories = (JSONArray) parser.parse(strTrendHistories);

        NikeItem item0 = new NikeItem();
        NikeItem item1 = new NikeItem();
        NikeItem item2 = new NikeItem();

        item0.setId(0);
        item1.setId(1);
        item2.setId(2);

        Mockito.when(dao.getItemById(0)).thenReturn(item0);
        Mockito.when(dao.getItemById(1)).thenReturn(item1);
        Mockito.when(dao.getItemById(2)).thenReturn(item2);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse("2017-01-12");
        Date endDate = dateFormat.parse("2017-01-19");

        uut.setJsonTrendData(trendData);
        uut.setJsonTrendHistories(trendHistories);

        List<ItemTrendData> itemTrendData = uut.aggregateAllItems(startDate, endDate);

        assertEquals(3, itemTrendData.size());
        assertEquals(item2, itemTrendData.get(0).getItem());
    }

    /*
    Other unit tests would include expected exceptions (error parsing date, JSON file, or DataException),
    excluding items before the start date and after the end date, and empty lists
     */
}

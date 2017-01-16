package trends.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import trends.controller.data.DataController;
import trends.model.ItemTrendData;
import trends.model.ItemType;
import trends.model.NikeItem;
import trends.service.DataAggregatorService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests the end points for the DataController
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(classes = DataControllerTest.WebConfig.class))
public class DataControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    /**
     * Test getAllTrendDataByDate method URL, query parameters, media types, response type, and response content
     *
     * @throws Exception
     */
    @Test
    public void testGetAllTrendDataByDate() throws Exception {
        mockMvc.perform(get("/api/trends?startDate=2017-01-12&endDate=2017-01-19").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[1].item.name").value("Kyrie 3"));
    }

    /*
    * Other unit tests would include expected errors when missing a query parameter, improper query parameter format,
    * and no data returned.
     */

    @Configuration
    @EnableWebMvc
    static class WebConfig extends WebMvcConfigurerAdapter {

        @Bean
        public DataAggregatorService dataAggregatorService() throws Exception {
            DataAggregatorService dataService = Mockito.mock(DataAggregatorService.class);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            NikeItem item1 = new NikeItem();
            item1.setId(1);
            item1.setItemType(ItemType.SNEAKER);
            item1.setName("Kyrie 3");

            ItemTrendData itemTrend1 = new ItemTrendData();
            itemTrend1.setItem(item1);
            itemTrend1.setStartDate(formatter.parse("2017-01-12"));
            itemTrend1.setEndDate(formatter.parse("2017-01-19"));
            itemTrend1.setMentions(1000);

            NikeItem item2 = new NikeItem();
            item2.setId(2);
            item2.setItemType(ItemType.APPAREL);
            item2.setName("Nike Pro Combat Compression Shorts");

            ItemTrendData itemTrend2 = new ItemTrendData();
            itemTrend2.setItem(item2);
            itemTrend2.setStartDate(formatter.parse("2017-01-12"));
            itemTrend2.setEndDate(formatter.parse("2017-01-19"));
            itemTrend2.setMentions(2000);

            List<ItemTrendData> trendData = new ArrayList<>();
            trendData.add(itemTrend2);
            trendData.add(itemTrend1);

            Mockito.when(dataService.aggregateAllItems(Mockito.any(), Mockito.any())).thenReturn(trendData);

            return dataService;
        }

        @Bean
        public DataController dataController() {
            DataController dataController = new DataController();

            return dataController;
        }
    }
}

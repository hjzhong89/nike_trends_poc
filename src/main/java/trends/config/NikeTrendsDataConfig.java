package trends.config;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import trends.data.JsonDataAccessObject;
import trends.data.NikeItemDataAccessObject;
import trends.exception.DataException;
import trends.service.DataAggregatorService;
import trends.service.JsonDataAggregator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Configures and initializes the DAO beans
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
@Configuration
public class NikeTrendsDataConfig {

    @Bean
    public DataAggregatorService dataAggregatorService() throws IOException, ParseException, DataException {
        final String JSON_TREND_DATA_FILE = "trend_data.json";
        final String JSON_TREND_HISTORY_FILE = "trend_history.json";

        ClassLoader classLoader = getClass().getClassLoader();
        File trendDataFile = new File(classLoader.getResource(JSON_TREND_DATA_FILE).getFile());

        JSONParser jsonParser = new JSONParser();
        JSONArray trendData = (JSONArray) jsonParser.parse(new FileReader(trendDataFile));

        File trendHistoryFile = new File(classLoader.getResource(JSON_TREND_HISTORY_FILE).getFile());
        JSONArray trendHistory = (JSONArray) jsonParser.parse(new FileReader(trendHistoryFile));
        JsonDataAggregator dataService = new JsonDataAggregator();
        dataService.setDao(nikeItemDataAccessObject());
        dataService.setJsonTrendData(trendData);
        dataService.setJsonTrendHistories(trendHistory);


        return dataService;
    }

    @Bean
    public NikeItemDataAccessObject nikeItemDataAccessObject() throws IOException, ParseException {
        final String JSON_ITEM_FILE_NAME = "sneakers.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(JSON_ITEM_FILE_NAME).getFile());

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(file));
        JsonDataAccessObject dao = new JsonDataAccessObject();
        dao.setJsonItemData((JSONArray) obj);
        return dao;
    }
}

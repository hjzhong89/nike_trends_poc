package trends.controller.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trends.exception.BusinessLogicException;
import trends.model.ItemTrendData;
import trends.service.DataAggregatorService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * REST controller for data services
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
@RestController
public class DataController {

    @Autowired
    private DataAggregatorService dataService;

    public DataAggregatorService getDataService() {
        return dataService;
    }

    public void setDataService(DataAggregatorService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value ="/api/trends", method = RequestMethod.GET)
    public List<ItemTrendData> getAllTrendDataByDate(
            @RequestParam("startDate") String startDateParam,
            @RequestParam("endDate") String endDateParam
    ) throws BusinessLogicException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateParam);
            Date endDate = dateFormat.parse(endDateParam);

            return dataService.aggregateAllItems(startDate, endDate);
        } catch (ParseException ex) {
            throw new BusinessLogicException(ex);
        }
    }
}

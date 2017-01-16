package trends.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import trends.service.DataAggregatorService;

/**
 * Controller for home page.
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private DataAggregatorService dataService;

    public DataAggregatorService getDataService() {
        return dataService;
    }

    public void setDataService(DataAggregatorService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getIndex(ModelMap model) {
        return "home/home";
    }
}

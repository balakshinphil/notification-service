package notificationservice.controller;

import notificationservice.dto.NotificationStatisticsDTO;
import notificationservice.dto.StatisticsDTO;
import notificationservice.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }


    @GetMapping("/api/statistics")
    public List<StatisticsDTO> getGeneralStatistics() {
        return statisticsService.getGeneralStatistics();
    }


    @GetMapping("/api/statistics/{id}")
    public NotificationStatisticsDTO getStatisticsOfNotification(@PathVariable long id) {
        return statisticsService.getDetailStatisticsForNotification(id);
    }
}

package project.clothes_shop.service.statistic;

import project.clothes_shop.model.Statistic;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.List;

public interface IStatisticService {
    List<Statistic> getByDateRange(Date startDay, Date endDay);

    ByteArrayInputStream exportToExcelFile(Date startDay, Date toDay);
}

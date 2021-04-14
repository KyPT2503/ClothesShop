package project.clothes_shop.dto;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class DateSearchDTO {
    private String startDay;
    private String endDay;

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public Date convertDateSql(String date) {
        List<String> data = Arrays.asList(date.split("/"));
        return Date.valueOf(data.get(2) + "-" + data.get(0) + "-" + data.get(1));
    }
}

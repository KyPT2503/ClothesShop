package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.dto.DateSearchDTO;
import project.clothes_shop.model.Statistic;
import project.clothes_shop.service.statistic.IStatisticService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/statistic")
public class AdminStatisticController {
    @Autowired
    private IStatisticService statisticService;

    @GetMapping("")
    public ModelAndView showStatisticThisWeek() {
        Date toDay = Date.valueOf(LocalDate.now());
        toDay.setDate(toDay.getDate() + 1);
        Date fromDay = Date.valueOf(toDay.toString());
        fromDay.setDate(fromDay.getDate() - 7);
        return new ModelAndView("admin/statistic", "statistics", statisticService.getByDateRange(fromDay, toDay));
    }

    @PostMapping("/search")
    public List<Statistic> searchByDateRange(@RequestBody DateSearchDTO dateSearchDTO) {
        Date toDay = dateSearchDTO.convertDateSql(dateSearchDTO.getEndDay());
        toDay.setDate(toDay.getDate() + 1);
        Date fromDay = dateSearchDTO.convertDateSql(dateSearchDTO.getStartDay());
        return statisticService.getByDateRange(fromDay, toDay);
    }

    @GetMapping("/download")
    public ModelAndView downloadStatistic(@RequestParam("startDay") Date fromDay, @RequestParam("endDay") Date toDay, HttpServletResponse response) {
        String fileName = "SoLieuThongKe (" + fromDay.toString() + " den " + toDay.toString() + ").xlsx";
        toDay.setDate(toDay.getDate() + 1);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        InputStream inputStream = statisticService.exportToExcelFile(fromDay, toDay);
        try {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("admin/statistic", "statistics", new ArrayList<Statistic>());
    }
}

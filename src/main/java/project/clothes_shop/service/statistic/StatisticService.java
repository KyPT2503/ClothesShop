package project.clothes_shop.service.statistic;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.Order;
import project.clothes_shop.model.OrderDetail;
import project.clothes_shop.model.Statistic;
import project.clothes_shop.service.order.IOrderService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService implements IStatisticService {
    @Autowired
    private IOrderService orderService;

    @Override
    public List<Statistic> getByDateRange(Date startDay, Date endDay) {
        List<Statistic> statistics = new ArrayList<>();
        for (Date date = startDay; date.before(endDay); date.setDate(date.getDate() + 1)) {
            Statistic statistic = new Statistic();
            List<Order> orders = orderService.findAllByDate(date);
            statistic.setDay(Date.valueOf(date.toString()));
            statistic.setOrderAmount(orders.size());
            int productAmount = 0;
            int total = 0;
            for (Order order : orders) {
                for (OrderDetail orderDetail : order.getOrderDetails()) {
                    productAmount += orderDetail.getAmount();
                    total += orderDetail.getAmount() * orderDetail.getClothes().getClothesDetail().getPrice();
                }
            }
            statistic.setProductAmount(productAmount);
            statistic.setTotal(total);
            statistics.add(statistic);
        }
        return statistics;
    }

    @Override
    public ByteArrayInputStream exportToExcelFile(Date startDay, Date toDay) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Số liệu thống kê");
        int rowNumber = 0;
        Row head = sheet.createRow(rowNumber++);
        Cell firstCell0 = head.createCell(0);
        firstCell0.setCellValue("Ngày");
        Cell firstCell1 = head.createCell(1);
        firstCell1.setCellValue("Số đơn hàng bán được");
        Cell firstCell2 = head.createCell(2);
        firstCell2.setCellValue("Số lượng sản phẩm bán được");
        Cell firstCell3 = head.createCell(3);
        firstCell3.setCellValue("Tổng doanh thu");
        for (Statistic statistic : this.getByDateRange(startDay, toDay)) {
            Row row = sheet.createRow(rowNumber++);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(statistic.getDay().toString());
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(statistic.getOrderAmount());
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(statistic.getProductAmount());
            Cell cell3 = row.createCell(3);
            cell3.setCellValue(statistic.getTotal());
        }
        try {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}

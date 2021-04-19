package project.clothes_shop.service.room;

import org.springframework.http.HttpCookie;
import project.clothes_shop.model.Room;
import project.clothes_shop.service.IGeneralService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRoomService extends IGeneralService<Room> {
    Room getCurrentRoom(HttpServletRequest request, HttpServletResponse response);
}

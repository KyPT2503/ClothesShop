package project.clothes_shop.service.message;

import project.clothes_shop.model.Message;
import project.clothes_shop.model.Room;
import project.clothes_shop.service.IGeneralService;

import java.util.List;

public interface IMessageService extends IGeneralService<Message> {
    List<Message> findByRoom(Room room);
}

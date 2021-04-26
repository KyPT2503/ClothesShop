package project.clothes_shop.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Message;
import project.clothes_shop.model.Room;
import project.clothes_shop.repo.MessageRepo;

import java.util.List;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public Message add(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public boolean remove(Message message) {
        return false;
    }

    @Override
    public Message update(Message message) {
        return null;
    }

    @Override
    public Message findById(Long id) {
        return null;
    }

    @Override
    public List<Message> findByRoom(Room room) {
        List<Message> messages = messageRepo.getAllByRoom(room);
        for (Message message : messages) {
            if (message.getAppUser() == null) {
                AppUser appUser = new AppUser();
                appUser.setName("Ẩn danh");
                message.setAppUser(appUser);
            }
        }
        return messages;
    }
}

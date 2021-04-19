package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Message;
import project.clothes_shop.service.message.IMessageService;
import project.clothes_shop.service.room.IRoomService;
import project.clothes_shop.service.user.IAppUserService;

import java.security.Principal;

@Controller
public class MessagingController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IMessageService messageService;

    @MessageMapping("/chat/{roomId}")
    public void sendDirect(@DestinationVariable("roomId") String roomId, @Payload Message message, Principal principal) {
        // set for message (appUser, room)
        if (principal == null) {
            AppUser appUser = new AppUser();
            appUser.setName("Ẩn danh");
            message.setAppUser(appUser);
        } else {
            message.setAppUser(appUserService.getByEmail(principal.getName()));
        }
        simpMessagingTemplate.convertAndSend("/topic/" + roomId, message);
        message.setRoom(roomService.findById(Long.valueOf(roomId)));
        // ad message to database
        if (principal != null) {
            messageService.add(message);
        } else {
            message.setAppUser(null);
            messageService.add(message);
        }
    }
}

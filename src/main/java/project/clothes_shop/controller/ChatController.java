package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Cart;
import project.clothes_shop.model.Message;
import project.clothes_shop.model.Room;
import project.clothes_shop.service.cart.ICartService;
import project.clothes_shop.service.message.IMessageService;
import project.clothes_shop.service.room.IRoomService;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IMessageService messageService;

    @ModelAttribute("current_cart")
    public Cart getCurrentCart(HttpSession session) {
        return cartService.getCurrentCart(session);
    }

    @ModelAttribute("user")
    public AppUser getCurrentUser() {
        return appUserService.getCurrentUser();
    }

    @GetMapping("")
    public ModelAndView showChat() {
        return new ModelAndView("user/chat");
    }

    @GetMapping("/get-roomId")
    public Long getCurrentRoomId(HttpServletRequest request, HttpServletResponse response) {
        return roomService.getCurrentRoom(request, response).getId();
    }

    @GetMapping("/get-all-message/{roomId}")
    public List<Message> getAllMessage(@PathVariable("roomId") Room room) {
        return messageService.findByRoom(room);
    }
}

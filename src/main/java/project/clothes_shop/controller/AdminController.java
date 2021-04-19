package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.service.room.IRoomService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IRoomService roomService;

    @GetMapping("")
    public ModelAndView showIndexAdmin() {
        return new ModelAndView("admin/index","rooms",roomService.findAll());
    }
}

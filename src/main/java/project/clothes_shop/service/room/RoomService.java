package project.clothes_shop.service.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Room;
import project.clothes_shop.repo.RoomRepo;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private RoomRepo roomRepo;

    @Override
    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    @Override
    public Room add(Room room) {
        return roomRepo.save(room);
    }

    @Override
    public boolean remove(Room room) {
        return false;
    }

    @Override
    public Room update(Room room) {
        return null;
    }

    @Override
    public Room findById(Long id) {
        return roomRepo.getOne(id);
    }

    @Override
    public Room getCurrentRoom(HttpServletRequest request, HttpServletResponse response) {
        // check if is user: 1. if do not have room, create room else return room
        AppUser appUser = appUserService.getCurrentUser();
        if (appUser != null) {
            Room room = roomRepo.getFirstByAppUser(appUser);
            if (room == null) {
                Room newRoom = new Room();
                newRoom.setAppUser(appUser);
                room = roomRepo.save(newRoom);
            }
            return room;
        } else {
            // if is not user: 1. if has cookie: roomId, return room by roomId
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("roomId")) {
                    return roomRepo.getOne(Long.valueOf(cookie.getValue()));
                }
            }
            //                 2. if do not has cookie: roomId, create new room and add cookie roomId
            Room room = new Room();
            room = roomRepo.save(room);
            Cookie cookie = new Cookie("roomId", room.getId().toString());
            cookie.setMaxAge(1000000);
            response.addCookie(cookie);
            return room;
        }
    }
}

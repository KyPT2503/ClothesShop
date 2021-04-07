package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.model.Clothes;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.model.Color;
import project.clothes_shop.model.Size;
import project.clothes_shop.service.clothes.IClothesService;
import project.clothes_shop.service.color.IColorService;
import project.clothes_shop.service.size.ISizeService;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private IClothesService clothesService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private IColorService colorService;

    @ModelAttribute("colors")
    public List<Color> getAllColor() {
        return colorService.findAll();
    }

    @ModelAttribute("sizes")
    public List<Size> getAllSize() {
        return sizeService.findAll();
    }

    @GetMapping("")
    public ModelAndView showProducts() {
        return new ModelAndView("admin/products");
    }

    @GetMapping("/add")
    public ModelAndView showAddPage() {
        return new ModelAndView("admin/add", "clothes", new Clothes());
    }

    @GetMapping("/update/{id}")
    public ModelAndView showUpdatePage(@PathVariable("id") Clothes clothes) {
        return new ModelAndView("admin/add", "clothes", clothes);
    }

    @PostMapping("/add-clothes")
    public Clothes addOrSave(@ModelAttribute("clothes") Clothes clothes) {
        clothesService.add(clothes);
        return clothes;
    }
}

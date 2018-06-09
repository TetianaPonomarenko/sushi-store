package pack.controllers;

import pack.entities.Category;
import pack.entities.Client;
import pack.entities.Meal;
import pack.repository.CategoryRepository;
import pack.repository.MealsRepository;
import pack.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private ClientService clientService;

    private final MealsRepository mealRepository;
    private final CategoryRepository categoryRepository;

    public MenuController(MealsRepository mealsRepository, CategoryRepository categoryRepository) {
        this.mealRepository = mealsRepository;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(value = {"/categories"}, method = RequestMethod.GET)
    public ModelAndView category() {
        ModelAndView modelAndView = new ModelAndView();
        Iterable<Category> categoryList = categoryRepository.findAll();
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.setViewName("menu/categorymenu");
        return modelAndView;
    }

    @RequestMapping(value = {"/meals"}, method = RequestMethod.GET)
    public ModelAndView meal(@RequestParam(value = "id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        Iterable<Meal> optionalMealList = mealRepository.findAll();
        List<Meal> mealList = new ArrayList<>();
        for (Meal meal : optionalMealList) {
            if (meal.getCategory().getId().toString().equals(id)) {
                mealList.add(meal);
            }
        }

        modelAndView.addObject("mealList", mealList);
        modelAndView.setViewName("menu/mealmenu");
        return modelAndView;
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public ModelAndView getcart() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());
        modelAndView.addObject("userName", client.getClientFIO());
        modelAndView.setViewName("menu/cart");
        return modelAndView;
    }
}


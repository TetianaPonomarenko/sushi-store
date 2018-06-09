package pack.controllers;

import pack.entities.Category;
import pack.entities.Client;
import pack.entities.Meal;
import pack.repository.CategoryRepository;
import pack.repository.ClientRepository;
import pack.repository.MealsRepository;
import pack.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ClientService clientService;

    private final MealsRepository mealRepository;
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    public AdminController(MealsRepository mealsRepository, ClientRepository clientsRepository, CategoryRepository categoryRepository) {
        this.mealRepository = mealsRepository;
        this.clientRepository = clientsRepository;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());
        modelAndView.addObject("userName", "Welcome " + client.getClientFIO() + "!");
        modelAndView.setViewName("admin/home");
        return modelAndView;

    }

    @RequestMapping(value="/meals", method = RequestMethod.GET)
    public ModelAndView getMeals() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());
        Iterable<Meal> mealList = mealRepository.findAll();
        modelAndView.addObject("mealList", mealList);
        modelAndView.addObject("userName", "Welcome " + client.getClientFIO() + "!");
        modelAndView.setViewName("admin/meals");
        return modelAndView;

    }

    @GetMapping("/clients")
    public Iterable<Client> getClients() {
        return clientRepository.findAll();
    }

    @RequestMapping(value="/categories", method = RequestMethod.GET)
    public ModelAndView getCategories() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());

        Iterable<Category> categoryList = categoryRepository.findAll();
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.addObject("userName", "Welcome " + client.getClientFIO() + "!");
        modelAndView.setViewName("admin/categories");
        return modelAndView;

    }

    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public ModelAndView editMealByParameter(@RequestParam(value="ID_meal") String ID_meal) {  //(@PathVariable String ID_meal) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());

        Long id = new Long(ID_meal);
        Optional<Meal> optionalMeal = mealRepository.findById(id);
        Meal meal = new Meal();
        meal.setID_meal(optionalMeal.get().getID_meal());
        meal.setTitle(optionalMeal.get().getTitle());
        meal.setPrice(optionalMeal.get().getPrice());
        meal.setCategory(optionalMeal.get().getCategory());
        meal.setDiscountMeal(optionalMeal.get().getDiscountMeal());



        Iterable<Category> categories = categoryRepository.findAll();

        Meal newMeal = new Meal();
        modelAndView.addObject("newMeal", newMeal);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("editedmeal", meal);
        modelAndView.addObject("successMessage", "Редактирование информации.");

        modelAndView.addObject("userName", "Welcome " + client.getClientFIO() + "!");
        modelAndView.setViewName("admin/editmeal");

        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editMeal(@RequestParam(value="ID_meal") String ID_meal,
                                 @Valid Meal newMeal,
                                 BindingResult bindingResult,
                                 HttpServletRequest request,
                                 HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());

        Long idMeal = new Long(ID_meal);
        Optional<Meal> optionalMeal = mealRepository.findById(idMeal);

        newMeal.setID_meal(optionalMeal.get().getID_meal());

        Optional<Category> optionalCategory = categoryRepository.findById(newMeal.getCategory().getId());
        Category category = new Category();
        category.setId(optionalCategory.get().getId());
        category.setName(optionalCategory.get().getName());

        newMeal.setCategory(category);

        MultipartRequest multipartRequest = (MultipartRequest) request;
        Map map = multipartRequest.getFileMap();
        MultipartFile mfile = null;
        String fileName = "";
        for (Iterator iter = map.values().iterator(); iter.hasNext();) {
            mfile = (MultipartFile) iter.next();
            fileName = mfile.getOriginalFilename();
        }
        String file = "/images/"+fileName;
        newMeal.setImage(file);

        Meal savedMeal =  mealRepository.save(newMeal);

                Iterable<Category> categories = categoryRepository.findAll();
        Meal newestMeal = new Meal();
        modelAndView.addObject("newMeal", newestMeal);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("editedmeal", savedMeal);
        modelAndView.addObject("userName", "Welcome " + client.getClientFIO() + "!");
        modelAndView.addObject("successMessage", "Информация о блюде успешно обновлена!");

        modelAndView.setViewName("admin/editmeal");
        return modelAndView;
    }

    @RequestMapping(value="/edit/category", method=RequestMethod.GET)
    public ModelAndView editCategoryByParameter(@RequestParam(value="id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());

        Long idCategory = new Long(id);
        Optional<Category> optionalCategory = categoryRepository.findById(idCategory);
        Category category  = new Category();
        category.setId(optionalCategory.get().getId());
        category.setName(optionalCategory.get().getName());

        Category newCategory = new Category();
        modelAndView.addObject("newCategory", newCategory);
        modelAndView.addObject("editedcategory", category);
        modelAndView.addObject("successMessage", "Редактирование информации.");

        modelAndView.addObject("userName", "Welcome " + client.getClientFIO() + "!");
        modelAndView.setViewName("admin/editcategory");

        return modelAndView;
    }

    @RequestMapping(value = "/edit/category", method = RequestMethod.POST)
    public ModelAndView editCategory(@RequestParam(value="id") String id,
                                 @Valid Category newCategory,
                                 BindingResult bindingResult,
                                     HttpServletRequest request,
                                     HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());

        Long idCategory = new Long(id);
        newCategory.setId(idCategory);

        MultipartRequest multipartRequest = (MultipartRequest) request;
        Map map = multipartRequest.getFileMap();
        MultipartFile mfile = null;
        String fileName = "";
        for (Iterator iter = map.values().iterator(); iter.hasNext();) {
            mfile = (MultipartFile) iter.next();
            fileName = mfile.getOriginalFilename();
        }
        String file = "/images/"+fileName;
        newCategory.setImage(file);


        Category savedCategory =  categoryRepository.save(newCategory);


        Category newestCategory = new Category();
        modelAndView.addObject("newCategory", newestCategory);
        modelAndView.addObject("editedcategory", savedCategory);
        modelAndView.addObject("successMessage", "Информация о категории успешно обновлена!");

        modelAndView.addObject("userName", "Welcome " + client.getClientFIO() + "!");
        modelAndView.setViewName("admin/editcategory");

        return modelAndView;
    }

    @RequestMapping(value="/createcategory", method = RequestMethod.GET)
    public ModelAndView createCategory(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        Category category  = new Category();
        modelAndView.addObject("category", category);
        modelAndView.setViewName("admin/createcategory");
        return modelAndView;
    }

    @RequestMapping(value = "/createcategory", method = RequestMethod.POST)
    public ModelAndView createNewCategory(@Valid Category category, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());

              categoryRepository.save(category);
            modelAndView.addObject("successMessage", "Creating successful! ");
            modelAndView.addObject("category", new Category());
            modelAndView.setViewName("admin/createcategory");

        return modelAndView;
    }


    @RequestMapping(value="/createmeal", method = RequestMethod.GET)
    public ModelAndView createMeal(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByFIO(auth.getName());

        ModelAndView modelAndView = new ModelAndView();
        Meal meal  = new Meal();
        modelAndView.addObject("meal", meal);
        Iterable<Category> categoryList = categoryRepository.findAll();
        modelAndView.addObject("categories", categoryList);
        modelAndView.setViewName("admin/createmeal");
        return modelAndView;
    }

    @RequestMapping(value = "/createmeal", method = RequestMethod.POST)
    public ModelAndView createNewMeal(
            @ModelAttribute(value="meal") Meal meal,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Category> optionalCategory = categoryRepository.findById(meal.getCategory().getId());
        Category category = new Category();
        category.setId(optionalCategory.get().getId());
        category.setName(optionalCategory.get().getName());

        meal.setCategory(category);
        mealRepository.save(meal);
        Iterable<Category> categoryList = categoryRepository.findAll();
        modelAndView.addObject("categories", categoryList);
        modelAndView.addObject("successMessage", "Creating successful! ");
        modelAndView.addObject("meal", new Meal());
        modelAndView.setViewName("admin/createmeal");

        return modelAndView;
    }
}

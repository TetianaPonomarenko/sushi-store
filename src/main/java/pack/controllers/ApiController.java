package pack.controllers;

import pack.entities.Client;

import pack.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@RestController
public class ApiController {

    @Autowired
    private ClientService clientService;

    public ApiController() {
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Client client = new Client();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewClient(@Valid Client client, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Client userExists = clientService.findClientByFIO(client.getClientFIO());
        if (userExists != null) {
            bindingResult
                    .rejectValue("clientFIO", "error.client",
                            "Login has already used, please, input another.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            System.out.println(client.getPassword());
            clientService.saveClient(client);
            modelAndView.addObject("successMessage", "Registration successful! ");
            modelAndView.addObject("client", new Client());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
package ua.oleg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.oleg.utils.ConnectionProperties;

@Controller
@RequestMapping("/main")
public class MainWebController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView loginPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("connectionProperties",new ConnectionProperties());
        modelAndView.setViewName("loginPage");
        return modelAndView;
    }

//    @RequestMapping(value = "/repfirstmake",method = RequestMethod.POST)
//    public ModelAndView repfirstMake(@ModelAttribute("reportModel") Report report){

    @RequestMapping(value = "/connect",method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("connectionProperties") ConnectionProperties connectionProperties){
        ModelAndView modelAndView = new ModelAndView();


        //добавление ошибки
        modelAndView.addObject("error", "Ошибка ввода логина или пароля!");
        modelAndView.setViewName("loginPage");
        return modelAndView;
    }

   //     modelAndView.addObject("reportModel",new Report());
  //      modelAndView.setViewName("rep_first");

}

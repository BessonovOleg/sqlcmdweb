package ua.oleg.controllers;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.oleg.dao.PostgresConnection;
import ua.oleg.utils.ConnectionProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/main")
public class MainWebController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView loginPage(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        PostgresConnection postgresConnection = (PostgresConnection) session.getAttribute("connection");

        if (postgresConnection != null){
            modelAndView.setViewName("mainPage");
        }else {
            modelAndView.addObject("connectionProperties", new ConnectionProperties());
            modelAndView.setViewName("loginPage");
        }
        return modelAndView;
    }

//    @RequestMapping(value = "/repfirstmake",method = RequestMethod.POST)
//    public ModelAndView repfirstMake(@ModelAttribute("reportModel") Report report){

    @RequestMapping(value = "/connect",method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("connectionProperties") ConnectionProperties connectionProperties, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        PostgresConnection postgresConnection = (PostgresConnection) session.getAttribute("connection");

        if (postgresConnection == null){
            postgresConnection = new PostgresConnection(connectionProperties);

            try {
                postgresConnection.connect();
                modelAndView.setViewName("mainPage");
                session.setAttribute("connection",postgresConnection);
            }catch (Exception e){
                modelAndView.addObject("error", "Жопа " + e.getMessage());
                modelAndView.setViewName("loginPage");
            }
        } else {
            modelAndView.setViewName("mainPage");
        }

        return modelAndView;
    }

   //     modelAndView.addObject("reportModel",new Report());
  //      modelAndView.setViewName("rep_first");

}

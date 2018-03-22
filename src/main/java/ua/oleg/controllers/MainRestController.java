package ua.oleg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.oleg.dao.PostgresConnection;
import ua.oleg.dao.PostgresDatabaseManager;
import ua.oleg.model.DataTable;
import ua.oleg.service.MainService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/rest")
public class MainRestController {
    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTable getTableData(HttpSession session,HttpServletRequest request){

        DataTable result = new DataTable();
        PostgresConnection postgresConnection = (PostgresConnection) session.getAttribute("connection");

        if(postgresConnection != null) {
            String tableName = request.getParameter("tblname").trim();

            if (tableName != null && !"".equals(tableName)) {
                try {
                    mainService.setConnection(postgresConnection);
                    result = mainService.getTableData(tableName);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }


    @RequestMapping(value = "/tables",method = RequestMethod.GET)
    public List<String> getTables(HttpSession session){
        List<String> result = new ArrayList<String>();
        PostgresConnection postgresConnection = (PostgresConnection) session.getAttribute("connection");
        if(postgresConnection != null){
            mainService.setConnection(postgresConnection);
            result = mainService.getTables();
        }
        return result;
    }


    @RequestMapping(value = "/deletetable",method = RequestMethod.POST)
    public String deleteTable(HttpSession session, HttpServletRequest request){
        String result = "";
        PostgresConnection postgresConnection = (PostgresConnection) session.getAttribute("connection");

        if(postgresConnection != null) {
            String tableName = request.getParameter("tblname").trim();

            if(tableName == null || "".equals(tableName)){
                result = "Error! Table name cannot be empty";
            }else {
                try {
                    mainService.setConnection(postgresConnection);
                    mainService.deleteTable(tableName);
                    result = "Table '"+tableName+"' was deleted from database!";
                }catch (Exception e){
                    result = e.getMessage();
                }
            }
        }else {
            result = "Cannot connect ot database!";
        }
        return result;
    }

}

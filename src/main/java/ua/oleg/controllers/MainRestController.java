package ua.oleg.controllers;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.oleg.dao.PostgresConnection;
import ua.oleg.model.DataTable;
import ua.oleg.service.MainService;
import ua.oleg.utils.ColumnProperties;
import ua.oleg.utils.RowContentProperties;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;



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

    @RequestMapping(value = "/deleterow",method = RequestMethod.POST)
    public String deleteRow(HttpSession session, HttpServletRequest request){
        String result = "";
        PostgresConnection postgresConnection = (PostgresConnection) session.getAttribute("connection");

        if(postgresConnection != null) {
            String tableName = request.getParameter("tblname").trim();
            int rowID = Integer.valueOf(request.getParameter("rowid"));

            if(tableName == null || "".equals(tableName) || rowID == 0){
                result = "Error! Invalid parameters";
            }else {
                try {
                    mainService.setConnection(postgresConnection);
                    mainService.deleteRowByID(tableName,rowID);
                    result = "Row was deleted from database!";
                }catch (Exception e){
                    result = e.getMessage();
                }
            }
        }else {
            result = "Cannot connect ot database!";
        }
        return result;
    }


    @RequestMapping(value = "updatetablecontents",method = RequestMethod.POST)
    public String updatetablecontents(HttpSession session, HttpServletRequest request){
        String result = "";
        PostgresConnection postgresConnection = (PostgresConnection) session.getAttribute("connection");

        if(postgresConnection != null) {
            RowContentProperties rowContentProperties = null;
            try {
                BufferedReader reader = request.getReader();
                Gson gson = new Gson();
                rowContentProperties = gson.fromJson(reader, RowContentProperties.class);
            }catch (Exception e){
                result = e.getMessage();
            }

            if(rowContentProperties != null){
                try {
                    mainService.setConnection(postgresConnection);
                    mainService.updateTableContents(rowContentProperties);
                    result = "Ok";
                } catch (Exception e) {
                    result = e.getMessage();
                }
            }
        }else {
            result = "Cannot connect ot database!";
        }
        return result;
    }




    @RequestMapping(value = "/createtable",method = RequestMethod.POST)
    public String createTable(HttpSession session, HttpServletRequest request){
        String result = "";
        PostgresConnection postgresConnection = (PostgresConnection) session.getAttribute("connection");

        if(postgresConnection != null) {
            ColumnProperties columnProperties = null;

            try {
                BufferedReader reader = request.getReader();
                Gson gson = new Gson();
                columnProperties = gson.fromJson(reader, ColumnProperties.class);
            }catch (Exception e){
                result = e.getMessage();
            }

            if(columnProperties != null) {
                try {
                    mainService.setConnection(postgresConnection);
                    mainService.createTable(columnProperties);
                    result = "Table '" + columnProperties.getTblname() + "' was created in database!";
                } catch (Exception e) {
                    result = e.getMessage();
                }
            }
        }else {
            result = "Cannot connect ot database!";
        }
         return result;
    }
}

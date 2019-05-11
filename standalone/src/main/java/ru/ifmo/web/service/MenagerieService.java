package ru.ifmo.web.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.web.database.dao.MenagerieDAO;
import ru.ifmo.web.database.entity.Menagerie;
import ru.ifmo.web.standalone.App;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Data
@Slf4j
@Path("/menagerie")
@Produces({MediaType.APPLICATION_JSON})
public class MenagerieService {
    private MenagerieDAO menagerieDAO;

    public MenagerieService() throws IOException {
        log.info("Creating service");
        InputStream dsPropsStream = App.class.getClassLoader().getResourceAsStream("datasource.properties");
        Properties dsProps = new Properties();
        dsProps.load(dsPropsStream);
        HikariConfig hikariConfig = new HikariConfig(dsProps);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        this.menagerieDAO = new MenagerieDAO(dataSource);
    }

    @GET
    public List<Menagerie> findAll() throws SQLException {
        return menagerieDAO.findAll();
    }

    @GET
    @Path("/filter")
    public List<Menagerie> findWithFilters(@QueryParam("id") Long id, @QueryParam("animal") String animal,
                                          @QueryParam("name") String name, @QueryParam("breed") String breed,
                                          @QueryParam("health") String health, @QueryParam("arrival") String arrival) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(arrival);
        } catch (ParseException e) {
            date = null;
        }
        return menagerieDAO.findWithFilters(id, animal, name, breed, health, date);
    }

    @PUT
    @Path("{id}")
    public String update(@PathParam("id") Long id, @QueryParam("animal") String animal,
                         @QueryParam("name") String name, @QueryParam("breed") String breed,
                         @QueryParam("health") String health, @QueryParam("arrival") String arrival) throws SQLException, ParseException {
        if (animal == null) {
            throw new InvalidParameterException("animal");
        }
        if (arrival == null) {
            throw new InvalidParameterException("arrival");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(arrival);
        } catch (ParseException e) {
            date = null;
        }
        int updRes = menagerieDAO.update(id, animal, name, breed, health, date);
        if (updRes == 0){
            throw new NotFoundException(id);
        } else {
            return updRes + "";
        }
    }

    @DELETE
    @Path("{id}")
    public String delete(@PathParam("id") Long id) throws SQLException {
        int delRes = menagerieDAO.delete(id);
        if (delRes == 0){
            throw new NotFoundException(id);
        } else {
            return delRes + "";
        }
    }

    @POST
    public String create(@QueryParam("animal") String animal,
                         @QueryParam("name") String name, @QueryParam("breed") String breed,
                         @QueryParam("health") String health, @QueryParam("arrival") String arrival) throws
            SQLException, ParseException {
        if (animal == null) {
            throw new InvalidParameterException("animal");
        }
        if (arrival == null) {
            throw new InvalidParameterException("arrival");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(arrival);
        } catch (ParseException e) {
            date = null;
        }
        return menagerieDAO.create(animal, name, breed, health, date) + "";
    }
}

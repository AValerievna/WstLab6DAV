package ru.ifmo.web.client;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.web.database.entity.Menagerie;

import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.MediaType;

@Slf4j
public class MenagerieResourceIntegration {
    private final String findAllUrl = "http://localhost:8080/menagerie/all";
    private final String filterUrl = "http://localhost:8080/menagerie/filter";
    private final String updateUrl = "http://localhost:8080/menagerie/update";
    private final String deleteUrl = "http://localhost:8080/menagerie/delete";
    private final String createUrl = "http://localhost:8080/menagerie/create";
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public List<Menagerie> findAll() {
        Client client = Client.create();
        WebResource webResource = client.resource(findAllUrl);
        ClientResponse response =
                webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<Menagerie>> type = new GenericType<List<Menagerie>>() {
        };
        return response.getEntity(type);
    }

    public List<Menagerie> findWithFilters(Long id, String animal, String name, String breed, String health, Date arrival) {
        Client client = Client.create();
        WebResource webResource = client.resource(filterUrl);
        if (id != null) {
            webResource = webResource.queryParam("id", id + "");
        }
        if (animal != null) {
            webResource = webResource.queryParam("animal", animal);
        }
        if (name != null) {
            webResource = webResource.queryParam("name", name);
        }
        if (breed != null) {
            webResource = webResource.queryParam("breed", breed);
        }
        if (health != null) {
            webResource = webResource.queryParam("health", health);
        }
        if (arrival != null) {
            webResource = webResource.queryParam("arrival", sdf.format(arrival));
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<Menagerie>> type = new GenericType<List<Menagerie>>() {
        };
        return response.getEntity(type);
    }

    public Long create(String animal, String name, String breed, String health, Date arrival) {
        Client client = Client.create();
        WebResource webResource = client.resource(createUrl);
        if (animal != null) {
            webResource = webResource.queryParam("animal", animal);
        }
        if (name != null) {
            webResource = webResource.queryParam("name", name);
        }
        if (breed != null) {
            webResource = webResource.queryParam("breed", breed);
        }
        if (health != null) {
            webResource = webResource.queryParam("health", health);
        }
        if (arrival != null) {
            webResource = webResource.queryParam("arrival", sdf.format(arrival));
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return Long.parseLong(response.getEntity(type));
    }

    public int update(Long id, String animal, String name, String breed, String health, Date arrival) {
        Client client = Client.create();
        WebResource webResource = client.resource(updateUrl);
        if (id != null) {
            webResource = webResource.queryParam("id", id + "");
        }
        if (animal != null) {
            webResource = webResource.queryParam("animal", animal);
        }
        if (name != null) {
            webResource = webResource.queryParam("name", name);
        }
        if (breed != null) {
            webResource = webResource.queryParam("breed", breed);
        }
        if (health != null) {
            webResource = webResource.queryParam("health", health);
        }
        if (arrival != null) {
            webResource = webResource.queryParam("arrival", sdf.format(arrival));
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return Integer.parseInt(response.getEntity(type));
    }

    public int delete(Long id) {
        Client client = Client.create();
        WebResource webResource = client.resource(deleteUrl);
        if (id != null) {
            webResource = webResource.queryParam("id", id + "");
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return Integer.parseInt(response.getEntity(type));
    }
}

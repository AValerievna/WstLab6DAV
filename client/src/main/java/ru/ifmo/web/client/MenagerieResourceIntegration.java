package ru.ifmo.web.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.web.database.entity.Menagerie;

import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class MenagerieResourceIntegration {
    private final String baseUrl = "http://localhost:8080/menagerie";
    private final String filterUrl = "http://localhost:8080/menagerie/filter";
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public  RequestResult<List<Menagerie>> findAll() {
        Client client = Client.create();
        WebResource webResource = client.resource(baseUrl);
        ClientResponse response =
                webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            GenericType<String> type = new GenericType<String>() {};
            return new RequestResult<>(true, response.getEntity(type), null);
        }
        GenericType<List<Menagerie>> type = new GenericType<List<Menagerie>>() {
        };
        return new RequestResult<>(false, null, response.getEntity(type));
    }

    public  RequestResult<List<Menagerie>> findWithFilters(Long id, String animal, String name, String breed, String health, Date arrival) {
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
            GenericType<String> type = new GenericType<String>() {};
            return new RequestResult<>(true, response.getEntity(type), null);
        }
        GenericType<List<Menagerie>> type = new GenericType<List<Menagerie>>() {
        };
        return new RequestResult<>(false, null, response.getEntity(type));
    }

    public RequestResult<Long> create(String animal, String name, String breed, String health, Date arrival) {
        Client client = Client.create();
        WebResource webResource = client.resource(baseUrl);
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
            GenericType<String> type = new GenericType<String>() {};
            String entity = response.getEntity(type);
            return new RequestResult<>(true, entity, null);
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return new RequestResult<>(false, null, Long.parseLong(response.getEntity(type)));
    }

    public  RequestResult<Integer> update(Long id, String animal, String name, String breed, String health, Date arrival) {
        Client client = Client.create();
        WebResource webResource = client.resource(baseUrl);
        if (id != null) {
            webResource = webResource.path(id + "");
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
            GenericType<String> type = new GenericType<String>() {};
            return new RequestResult<>(true, response.getEntity(type), null);
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return new RequestResult<>(false, null, Integer.parseInt(response.getEntity(type)));
    }

    public  RequestResult<Integer> delete(Long id) {
        Client client = Client.create();
        WebResource webResource = client.resource(baseUrl);
        if (id != null) {
            webResource = webResource.path(id + "");
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            GenericType<String> type = new GenericType<String>() {};
            return new RequestResult<>(true, response.getEntity(type), null);
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return new RequestResult<>(false, null, Integer.parseInt(response.getEntity(type)));
    }
}

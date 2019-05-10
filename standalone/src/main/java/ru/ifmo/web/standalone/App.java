package ru.ifmo.web.standalone;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import ru.ifmo.web.service.InvalidParameterExceptionMapper;
import ru.ifmo.web.service.MenagerieService;
import ru.ifmo.web.service.NotFoundExceptionMapper;

import java.io.IOException;

@Slf4j
public class App {
    public static void main(String... args) throws IOException {
        String url = "http://0.0.0.0:8080";

        log.info("Creating configs");
        ClassNamesResourceConfig config = new ClassNamesResourceConfig(MenagerieService.class,
                InvalidParameterExceptionMapper.class, NotFoundExceptionMapper.class);
        log.info("Creating server");
        HttpServer server = GrizzlyServerFactory.createHttpServer(url, config);
        log.info("Starting server");
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        log.info("Application started");
        System.in.read();
    }

}

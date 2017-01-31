package thesis.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import thesis.service.FooDatabaseService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class App extends AbstractVerticle
{
    MongoClient mongo;
    FooDatabaseService service;

    @Override
    public void start() throws Exception {
        super.start();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().handler(CorsHandler.create("*")
                .allowedHeaders(getAllowedHeaders())
                .allowedMethods(getAllowedMethods()));

        router.get("/collections").handler(this::getCollections);
        router.get("/collections-eb").handler(this::getCollectionsEB);

        JsonObject mongoCfg = new JsonObject();
        mongoCfg.put("connection_string", "mongodb://127.0.0.1:27017");
        mongoCfg.put("db_name", "test_db");

        mongo = MongoClient.createShared(this.vertx, mongoCfg);
        service = FooDatabaseService.createProxy(vertx, FooDatabaseService.SERVICE_ADDRESS);

        vertx.createHttpServer().requestHandler(router::accept).listen(8585, ar -> {
            if(ar.succeeded())
                System.out.println("Running");
            else
                System.out.println(ar.cause());
        });

    }

    private <T> Handler<AsyncResult<T>> resultHandler(RoutingContext routingContext, Consumer<T> consumer){
        return res -> {
            if(res.succeeded())
                consumer.accept(res.result());
            else
                serviceUnavailable(routingContext);
        };
    }

    private void getCollections(RoutingContext routingContext){
        mongo.getCollections(ar -> {
            if(ar.succeeded()){
                List<JsonObject> l = ar.result().stream().map(e -> new JsonObject().put("name",e)).collect(Collectors.toList());
                routingContext.request().response()
                        .putHeader("content-type", "application/json")
                        .end(l.toString());
            }
            else
                ;
        });
    }

    private void getCollectionsEB(RoutingContext routingContext){
        service.getCollections(ar -> {
            if(ar.succeeded()){
                List<JsonObject> l = ar.result().stream().map(e -> new JsonObject().put("name",e)).collect(Collectors.toList());
                sendJsonResponse(routingContext, l.toString());
            }
            else{
                System.err.println(ar.cause());
                sendJsonResponse(routingContext, ar.cause().toString());
            }
        });
    }

    private void sendJsonResponse(RoutingContext routingContext, String response){
        routingContext.request().response()
                .putHeader("content-type", "application/json")
                .end(response);
    }

    private Set<String> getAllowedHeaders(){
        Set<String> allowHeaders = new HashSet<>();
        allowHeaders.add("x-requested-with");
        allowHeaders.add("Access-Control-Allow-Origin");
        allowHeaders.add("origin");
        allowHeaders.add("Content-Type");
        allowHeaders.add("accept");
        return allowHeaders;
    }

    private Set<HttpMethod> getAllowedMethods(){
        Set<HttpMethod> allowMethods = new HashSet<>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.POST);
        allowMethods.add(HttpMethod.DELETE);
        return allowMethods;
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }

    private void badRequest(RoutingContext context) {
        context.response().setStatusCode(400).end();
    }

    private void notFound(RoutingContext context) {
        context.response().setStatusCode(404).end();
    }

    private void serviceUnavailable(RoutingContext context) {
        context.response().setStatusCode(503).end();
    }

}

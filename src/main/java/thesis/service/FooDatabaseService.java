package thesis.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

@ProxyGen
public interface FooDatabaseService {

    static final String SERVICE_ADDRESS = "mongo-foo-db-service";

    static FooDatabaseService create(Vertx vertx, JsonObject config){
        return new FooDatabaseServiceImpl(vertx, config);
    }

    static FooDatabaseService createProxy(Vertx vertx, String address){
        return new FooDatabaseServiceVertxEBProxy(vertx, address);
    }

    void getCollections(Handler<AsyncResult<List<String>>> handler);

    @ProxyIgnore
    void close();

}

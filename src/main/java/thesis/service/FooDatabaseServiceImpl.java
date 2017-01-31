package thesis.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

public class FooDatabaseServiceImpl implements FooDatabaseService{

    private MongoClient mongo;
    private Vertx vertx;
    private JsonObject config;

    public FooDatabaseServiceImpl(Vertx vertx, JsonObject config){
        this.vertx = vertx;
        this.config = config;
        mongo = MongoClient.createNonShared(vertx,config);
    }

    @Override
    public void getCollections(Handler<AsyncResult<List<String>>> handler) {
        mongo.getCollections(ar -> {
            if(ar.succeeded())
                handler.handle(Future.succeededFuture(ar.result()));
            else
                handler.handle(Future.failedFuture(ar.cause()));
        });
    }

    @Override
    public void close() {
        mongo.close();
    }
}

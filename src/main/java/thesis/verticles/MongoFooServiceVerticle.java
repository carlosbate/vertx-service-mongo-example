package thesis.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;
import thesis.service.FooDatabaseService;

public class MongoFooServiceVerticle extends AbstractVerticle{

    FooDatabaseService service;

    @Override
    public void start() throws Exception {
        super.start();

        JsonObject config = new JsonObject();
        config.put("address", "");
        config.put("connection_string", "mongodb://127.0.0.1:27017");
        config.put("db_name", "test_db");

        service = FooDatabaseService.create(this.vertx, config);

        ProxyHelper.registerService(FooDatabaseService.class, vertx, service, FooDatabaseService.SERVICE_ADDRESS);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}

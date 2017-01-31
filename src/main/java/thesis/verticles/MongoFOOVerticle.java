package thesis.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoServiceVerticle;

public class MongoFOOVerticle extends AbstractVerticle {

    final String SERVICE_ADDRESS = "mongo-db-service";


    @Override
    public void start() throws Exception {
        super.start();

        JsonObject mongoCfg = new JsonObject();
        mongoCfg.put("address", SERVICE_ADDRESS);
        mongoCfg.put("db_name", "test_db");
        mongoCfg.put("useObjectId", true);
        mongoCfg.put("connection_string", "mongodb://127.0.0.1:27017");

        DeploymentOptions options = new DeploymentOptions().setConfig(mongoCfg);

        Verticle mongoVerticleService = new MongoServiceVerticle();

        vertx.deployVerticle(mongoVerticleService, options, res -> {
            if(res.succeeded()){
                System.out.println("Mongo service Deployed");
            }else {
                System.out.println("Mongo service Failed.");
            }
        });

    }
}

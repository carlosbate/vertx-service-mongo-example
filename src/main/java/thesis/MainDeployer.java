package thesis;

import io.vertx.core.AbstractVerticle;
import thesis.verticles.App;
import thesis.verticles.MongoFooServiceVerticle;

public class MainDeployer extends AbstractVerticle{

    @Override
    public void start() throws Exception {
        super.start();

        vertx.deployVerticle(new MongoFooServiceVerticle(), r -> {
            if(r.succeeded())
                System.out.println("Deployed MongoFoo Verticle");
            else
                System.err.println(r.cause());
        });

        vertx.deployVerticle(new App(), r -> {
            if(r.succeeded())
                System.out.println("Deployed App Verticle");
            else
                System.err.println(r.cause());
        });
    }
}

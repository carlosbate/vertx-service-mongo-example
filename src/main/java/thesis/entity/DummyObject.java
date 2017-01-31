package thesis.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class DummyObject {

    private String id;
    private String name;
    private int hp;
    private int stamina;
    private int x, y;

    public DummyObject(String id, String name, int hp, int stamina, int x, int y) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.stamina = stamina;
        this.x = x;
        this.y = y;
    }

    public DummyObject(JsonObject o){
        DummyObjectConverter.fromJson(o, this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public JsonObject toJson(){
        JsonObject o = new JsonObject();
        DummyObjectConverter.toJson(this, o);
        return o;
    }
}

/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package thesis.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link thesis.entity.DummyObject}.
 *
 * NOTE: This class has been automatically generated from the {@link thesis.entity.DummyObject} original class using Vert.x codegen.
 */
public class DummyObjectConverter {

  public static void fromJson(JsonObject json, DummyObject obj) {
    if (json.getValue("hp") instanceof Number) {
      obj.setHp(((Number)json.getValue("hp")).intValue());
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("stamina") instanceof Number) {
      obj.setStamina(((Number)json.getValue("stamina")).intValue());
    }
    if (json.getValue("x") instanceof Number) {
      obj.setX(((Number)json.getValue("x")).intValue());
    }
    if (json.getValue("y") instanceof Number) {
      obj.setY(((Number)json.getValue("y")).intValue());
    }
  }

  public static void toJson(DummyObject obj, JsonObject json) {
    json.put("hp", obj.getHp());
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    json.put("stamina", obj.getStamina());
    json.put("x", obj.getX());
    json.put("y", obj.getY());
  }
}
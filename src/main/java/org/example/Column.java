package org.example;

import org.apache.avro.Schema;

public class Column {
    public String name;
    public Schema type;

    public Column(String name, String type) {
        this.name = name;
        this.type = avroTypeFromString(type);
    }

    public static Schema avroTypeFromString(String type) {
        return switch (type.toLowerCase()) {
            case "string" -> Schema.create(Schema.Type.STRING);
            case "int", "integer" -> Schema.create(Schema.Type.INT);
            case "long" -> Schema.create(Schema.Type.LONG);
            case "boolean", "bool" -> Schema.create(Schema.Type.BOOLEAN);
            case "float" -> Schema.create(Schema.Type.FLOAT);
            case "double" -> Schema.create(Schema.Type.DOUBLE);
            case "bytes" -> Schema.create(Schema.Type.BYTES);

            default -> throw new IllegalArgumentException("Unsupported Avro type: " + type);
        };
    }
}

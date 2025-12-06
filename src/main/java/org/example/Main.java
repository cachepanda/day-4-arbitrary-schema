package org.example;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Column column1 = new Column("id", "INT");
        Column column2 = new Column("name", "STRING");
        List<Column> columns = List.of(column1, column2);

        // create avro schema
        Schema schema = buildSchema("person", columns);
        System.out.println(schema.toString(true));

        //read and write
        String filename = "users.avro";
        writeToAvro(schema, filename);
        readFromAvro(filename);
    }

    private static void readFromAvro(String filename) {
        File file = new File(filename);

        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();


        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            System.out.println("Reading records from users.avro:");
            while (dataFileReader.hasNext()) {
                GenericRecord userRecord = dataFileReader.next();

                System.out.println("User: " + userRecord.get("name"));
                System.out.println("ID: " + userRecord.get("id"));
                System.out.println("------------------------------------");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeToAvro(Schema schema, String filename) {
        GenericRecord user1 = new GenericData.Record(schema);
        user1.put("name", "Peter");
        user1.put("id", 7);

        File file = new File(filename);

        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(schema, file);
            dataFileWriter.append(user1);
            System.out.println("Avro file " + filename + " created successfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Schema buildSchema(String table, List<Column> columns) {
        SchemaBuilder.FieldAssembler<Schema> assembler = SchemaBuilder.record(table).fields();
        for (Column column : columns) {
            assembler = assembler.name(column.name).type(column.type).noDefault();
        }
        return assembler.endRecord();
    }
}
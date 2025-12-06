# Avro Schema Creation, Write, and Read

This project is a simple example to:

- **Create an Avro schema programmatically** using a list of columns  
- **Write a record** to an `.avro` file using `GenericRecord`
- **Read the record back** from the file

### How it works

1. Define columns (name + type)
2. Build an Avro schema at runtime
3. Create a `GenericRecord` and write it to `users.avro`
4. Read the record back and print the values

Avro automatically stores the schema inside the `.avro` file, so reading does not require passing a schema.

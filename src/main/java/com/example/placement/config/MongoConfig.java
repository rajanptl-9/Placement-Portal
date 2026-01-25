package com.example.placement.config;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.core.convert.converter.Converter;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "placementdb"; // same as in application.yml
    }

    // Spring Data MongoDB already creates a MongoClient based on the URI in
    // application.yml.
    // Here we only add custom converters for ObjectId <-> String handling.
    @Override
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ObjectIdToStringConverter());
        // converters.add(new StringToObjectIdConverter());
        return new MongoCustomConversions(converters);
    }

    // Convert ObjectId to its hex string representation
    static class ObjectIdToStringConverter implements Converter<ObjectId, String> {
        @Override
        public String convert(ObjectId source) {
            return source != null ? source.toHexString() : null;
        }
    }

    // Convert a hex string back to ObjectId
    // static class StringToObjectIdConverter implements Converter<String, ObjectId>
    // {
    // @Override
    // public ObjectId convert(String source) {
    // return (source != null && !source.isEmpty()) ? new ObjectId(source) : null;
    // }
    // }
}

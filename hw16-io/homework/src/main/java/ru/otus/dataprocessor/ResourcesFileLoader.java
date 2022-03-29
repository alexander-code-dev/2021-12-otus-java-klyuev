package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        List<Measurement> measurements = new ArrayList<>();
        // Deserializer JSON
        try (JsonParser parser = new JsonFactory()
                .createParser(this.getClass().getClassLoader().getResourceAsStream(fileName))) {
            while (!parser.isClosed()) {
                if (JsonToken.START_OBJECT.equals(parser.nextToken())) {
                    String name = null;
                    double value = 0D;
                    while (JsonToken.FIELD_NAME.equals(parser.nextToken())) {
                        parser.nextToken();
                        if("name".equals(parser.getCurrentName())) {
                            name = parser.getValueAsString();
                        }
                        else if ("value".equals(parser.getCurrentName())) {
                            value = parser.getValueAsDouble();
                        }
                    }
                    measurements.add(new Measurement(name, value));
                }
            }
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
        return measurements;
    }
}

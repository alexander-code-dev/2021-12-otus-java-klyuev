package ru.otus.web.services;

import java.io.IOException;
import java.util.Map;

public interface TemplateProcessor {
    String getPage(String filename, Map<String, Object> data) throws IOException;
    String getPage(String filename) throws IOException;
}

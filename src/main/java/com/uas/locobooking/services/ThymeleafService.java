package com.uas.locobooking.services;

import java.util.Map;

public interface ThymeleafService {

    String createContext(String template, Map<String, Object> variables);
}

package com.example.interfaces;

import java.util.List;

public interface DatabaseHandler {

    List<Object> getData();

    void insert(Object data);

    void update(Object data);

    void delete(Object data);
}

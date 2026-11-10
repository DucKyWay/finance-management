package com.suphawinee.financeManagement.services;

public interface Datasource<T> {
    T readData();
    void writeData(T data);
}

package com.mjc.school.repository.model.modelinterface;

public interface ModelInterface{
    Long getId();
    void setId(Long id);
    void getIdSequentialGenerator(Long id);
    void setIdSequentialGenerator(Long id);
    void generateId();
}

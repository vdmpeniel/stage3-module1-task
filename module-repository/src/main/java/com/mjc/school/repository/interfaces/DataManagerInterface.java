package com.mjc.school.repository.interfaces;

import java.util.List;

public interface DataManagerInterface {
    List<ModelInterface> load(Class<? extends ModelInterface> clazz) throws Exception;

    void persist(Class<? extends ModelInterface> clazz, List<ModelInterface> modelTable) throws Exception;
}

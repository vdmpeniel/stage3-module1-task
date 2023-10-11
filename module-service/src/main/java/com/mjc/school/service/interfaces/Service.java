package com.mjc.school.service.interfaces;

import java.util.List;

public interface Service<T1, T2> {
  List<T2> readAll();

  T2 readById(Long newsId);

  T2 create(T1 dtoRequest);

  T2 update(T1 dtoRequest);

  Boolean deleteById(Long newsId);
}

package com.mjc.school.repository.interfaces;

import java.util.List;

public interface Repository<T> {
  List<T> readAll();

  T readById(Long newsId);

  T create(T model);

  T update(T model);

  Boolean deleteById(Long newsId);

  Boolean isExistById(Long newsId);
}

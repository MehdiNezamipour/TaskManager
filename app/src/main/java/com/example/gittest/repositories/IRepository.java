package com.example.gittest.repositories;

import java.util.List;
import java.util.UUID;

public interface IRepository<E> {

    List<E> getList();

    E get(UUID id);

    void add(E e);

    void remove(E e);

    void update(E e);

}

package com.uploader.services;

import java.util.List;

public interface GenericService<T, K> {

    public T save(T model);

    public T get(K id);

    public void remove(K id);

    public List<T> getAll();
}

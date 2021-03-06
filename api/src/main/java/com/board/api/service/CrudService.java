package com.board.api.service;

import java.util.Optional;

public interface CrudService<T, ID> {

    Optional<T> getItemByUid(ID uid);

    T createItem(T item);

    T updateItem(T item);

    void deleteItem(ID uid);
}

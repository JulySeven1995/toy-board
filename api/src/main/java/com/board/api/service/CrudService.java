package com.board.api.service;

import java.util.Optional;

public interface CrudService<T> {

    Optional<T> getItemByUid(Long uid);

    T createItem(T user);

    T updateItem(T user);

    void deleteItem(Long uid);
}

package com.board.api.service;

import java.util.Optional;

public interface CrudService<T, ID> {

    Optional<T> getItemByUid(ID uid);

    T createItem(T user);

    T updateItem(T user);

    void deleteItem(ID uid);
}

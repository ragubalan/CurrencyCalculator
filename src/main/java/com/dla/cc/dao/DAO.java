package com.dla.cc.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    /**
     * Gets the <T> for the passed in code.
     *
     * @param code Passed in code.
     * @return Matching <T> if present; empty otherwise.
     */
    Optional<T> get(String code);

    /**
     * Gets all the available <T>.
     *
     * @return all available <T>.
     */
    List<T> getAll();
}

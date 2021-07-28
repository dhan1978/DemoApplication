package com.example.SpringMVCExample.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Author Rajan Chauhan
 * @Cteated On  20-05-2019
 * @Time 21:37
 */

/**
 * @class Generic class to reference invalid entity
 * @param <T>
 */
public class InvalidEntity<T> implements Serializable {

    private static final long serialVersionUID = 8096891435674431506L;

    private T invalidEntity;

    private HashMap<String,String> validationError;

    public InvalidEntity() {
    }

    public InvalidEntity(T invalidEntity) {
        this.invalidEntity = invalidEntity;
    }

    public T getInvalidEntity() {
        return invalidEntity;
    }

    public void setInvalidEntity(T invalidEntity) {
        this.invalidEntity = invalidEntity;
    }

    public HashMap<String, String> getValidationError() {
        return validationError;
    }

    public void setValidationError(HashMap<String, String> validationError) {
        this.validationError = validationError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof InvalidEntity)) return false;

        InvalidEntity<?> that = (InvalidEntity<?>) o;

        return new EqualsBuilder()
                .append(invalidEntity, that.invalidEntity)
                .append(validationError, that.validationError)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(invalidEntity)
                .append(validationError)
                .toHashCode();
    }
}

package io.github.artemy.osipov.shop.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final Class<?> targetClass;
    private final Object id;

    public EntityNotFoundException(Class<?> targetClass, Object id) {
        super(generateMessage(targetClass, id));
        this.targetClass = targetClass;
        this.id = id;
    }

    private static String generateMessage(Class<?> targetClass, Object id) {
        return String.format(
                "Entity '%s' with id '%s' not exists",
                targetClass.getSimpleName(), id
        );
    }
}

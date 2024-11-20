package com.example.login.domain.model.user;

import java.util.Objects;
import java.util.UUID;

public class UserId {
    private final UUID id;

    public UserId() {
        this.id = UUID.randomUUID();
    }

    public UserId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    // 同一性の比較
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserId userId = (UserId) o;
        return Objects.equals(id, userId);
    }
}

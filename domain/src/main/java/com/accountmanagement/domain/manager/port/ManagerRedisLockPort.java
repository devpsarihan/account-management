package com.accountmanagement.domain.manager.port;

import java.util.UUID;

public interface ManagerRedisLockPort {

    void lock(UUID managerId);

    void unlock(UUID managerId);
}

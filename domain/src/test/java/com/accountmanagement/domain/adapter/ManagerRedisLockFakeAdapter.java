package com.accountmanagement.domain.adapter;

import com.accountmanagement.domain.manager.port.ManagerRedisLockPort;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

public class ManagerRedisLockFakeAdapter implements ManagerRedisLockPort {

    @Override
    public void lock(UUID managerId) {
    }

    @Override
    public void unlock(UUID managerId) {
    }
}

package com.accountmanagement.infra.adapter.manager.lock;

import com.accountmanagement.domain.common.exception.AccountManagementException;
import com.accountmanagement.domain.common.exception.ErrorCode;
import com.accountmanagement.domain.manager.port.ManagerRedisLockPort;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagerRedisLockAdapter implements ManagerRedisLockPort {

    @Qualifier("managerLockRegistry")
    private final RedisLockRegistry managerLockRegistry;

    @Override
    public void lock(UUID managerId) {
        Lock lock = managerLockRegistry.obtain(managerId.toString());

        if (!lock.tryLock()) {
            throw new AccountManagementException(ErrorCode.MANAGER_LOCK_ERROR);
        }
        log.info("managerId:{} locked", managerId);
    }

    @Override
    public void unlock(UUID managerId) {
        try {
            managerLockRegistry.obtain(managerId.toString()).unlock();
            log.info("managerId:{} unlocked", managerId);
        } catch (RuntimeException e) {
            log.error("managerId:{} unlocked error", managerId, e);
        }
    }

}

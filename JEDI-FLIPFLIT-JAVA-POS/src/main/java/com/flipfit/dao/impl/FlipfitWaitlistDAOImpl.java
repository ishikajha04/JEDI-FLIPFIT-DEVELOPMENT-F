package com.flipfit.dao.impl;

import com.flipfit.bean.FlipfitWaitlist;
import com.flipfit.dao.FlipfitWaitlistDAO;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FlipfitWaitlistDAOImpl implements FlipfitWaitlistDAO {
    private static ConcurrentHashMap<Integer, FlipfitWaitlist> waitlistMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(1);

    @Override
    public FlipfitWaitlist getWaitlistBySlotId(int slotId) {
        return waitlistMap.values().stream()
                .filter(waitlist -> waitlist.getSlotId() == slotId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean addWaitlist(FlipfitWaitlist waitlist) {
        if (waitlist == null) {
            return false;
        }
        waitlist.setWaitlistId(getNextWaitlistId());
        waitlistMap.put(waitlist.getWaitlistId(), waitlist);
        return true;
    }

    @Override
    public boolean updateWaitlist(FlipfitWaitlist waitlist) {
        if (waitlist == null || !waitlistMap.containsKey(waitlist.getWaitlistId())) {
            return false;
        }
        waitlistMap.put(waitlist.getWaitlistId(), waitlist);
        return true;
    }

    @Override
    public boolean deleteWaitlist(int waitlistId) {
        return waitlistMap.remove(waitlistId) != null;
    }

    @Override
    public int getNextWaitlistId() {
        return idCounter.getAndIncrement();
    }
}
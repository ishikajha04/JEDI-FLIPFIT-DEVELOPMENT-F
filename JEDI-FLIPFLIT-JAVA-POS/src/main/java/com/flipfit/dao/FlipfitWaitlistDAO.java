package com.flipfit.dao;

import com.flipfit.bean.FlipfitWaitlist;
import java.util.List;

public interface FlipfitWaitlistDAO {
    FlipfitWaitlist getWaitlistBySlotId(int slotId);
    boolean addWaitlist(FlipfitWaitlist waitlist);
    boolean updateWaitlist(FlipfitWaitlist waitlist);
    boolean deleteWaitlist(int waitlistId);
    int getNextWaitlistId();
}
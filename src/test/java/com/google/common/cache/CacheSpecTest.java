package com.google.common.cache;

import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by blaffin on 3/30/2016.
 */
public class CacheSpecTest {

    // EXPECTED, ACTUAL

    @Test
    public void testFour(){
        CacheBuilder four = CacheSpec.getCacheBuilder("4");
        assertNotNull(four);
        assertEquals(4, four.maximumSize);
    }

    @Test
    public void testFourDaysFourHoursFourMinutesAccess(){
        CacheBuilder testFourDaysFourHoursFourMinutesAccess = CacheSpec.getCacheBuilder("4d4h4ma");
        assertNotNull(testFourDaysFourHoursFourMinutesAccess);
        assertEquals(TimeUnit.DAYS.toNanos(4) + TimeUnit.HOURS.toNanos(4) + TimeUnit.MINUTES.toNanos(4), testFourDaysFourHoursFourMinutesAccess.getExpireAfterAccessNanos());
    }

    @Test
    public void testFourDaysFourHoursFourMinutes(){
        CacheBuilder testFourDaysFourHoursFourMinutes = CacheSpec.getCacheBuilder("4d4h4m");
        assertNotNull(testFourDaysFourHoursFourMinutes);
        assertEquals(TimeUnit.DAYS.toNanos(4) + TimeUnit.HOURS.toNanos(4) + TimeUnit.MINUTES.toNanos(4), testFourDaysFourHoursFourMinutes.getExpireAfterWriteNanos());
    }

    @Test
    public void testFourDays(){
        CacheBuilder testFourDays = CacheSpec.getCacheBuilder("4d");
        assertNotNull(testFourDays);
        assertEquals(TimeUnit.DAYS.toNanos(4), testFourDays.getExpireAfterWriteNanos());
    }

    @Test
    public void testFourHours(){
        CacheBuilder testFourHours = CacheSpec.getCacheBuilder("4h");
        assertNotNull(testFourHours);
        assertEquals(TimeUnit.HOURS.toNanos(4), testFourHours.getExpireAfterWriteNanos());
    }

    @Test
    public void testFourMinutes(){
        CacheBuilder testFourMinutes = CacheSpec.getCacheBuilder("4m");
        assertNotNull(testFourMinutes);
        assertEquals(TimeUnit.MINUTES.toNanos(4), testFourMinutes.getExpireAfterWriteNanos());
    }

    @Test
    public void testNullSpec(){
        assertNull(CacheSpec.getCache(null));
        assertNull(CacheSpec.getCacheBuilder(null));
    }

    @Test
    public void testEmptySpec(){
        assertNull(CacheSpec.getCache(""));
        assertNull(CacheSpec.getCacheBuilder(""));
    }
}

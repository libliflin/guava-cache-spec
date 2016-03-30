package com.google.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by blaffin on 3/30/2016.
 *
 * sorry guava for using your package.
 * don't make methods I need to use to test package local if you don't want me using your package O_o
 *
 * related work:
 * http://camel.apache.org/cache.html
 *
 */
public class CacheSpec {

    static final Pattern DAY_PATTERN    = Pattern.compile("\\A([0-9]+d).*");
    static final Pattern HOUR_PATTERN   = Pattern.compile("\\A([0-9]+h).*");
    static final Pattern MINUTE_PATTERN = Pattern.compile("\\A([0-9]+m).*");
    static final Pattern ACCESS_PATTERN = Pattern.compile("\\Aa");
    static final Pattern SIZE_SPEC      = Pattern.compile("\\A([0-9]+)\\z");

    public static <K, V> Cache<K, V> getCache(String cacheSpec) {
        if (cacheSpec == null || cacheSpec.length() == 0) {
            return null;
        }
        return getCacheBuilder(cacheSpec).build();
    }

    /**
     * <pre>
     *
     * time-spec|size-spec;
     * time-spec:  \d+d\d+h\d+m days hours minutes; optionals...
     * size-spec:  \d+ ;
     *
     * examples:
     *  4h          expires 4h after write.
     *  4hw         expires 4h after write.
     *  4ha         expires 4h after access.
     *  100        set eviction based on 100 elements.
     * </pre>
     *
     * To use memory ba
     *
     * @param cacheSpec
     * @return
     */
    public static CacheBuilder getCacheBuilder(String cacheSpec) {
        if (cacheSpec == null || cacheSpec.length() == 0) {
            return null;
        }

        CacheBuilder cb = new CacheBuilder();

        // SPEC STARTS WITH TIME SPEC
        long specNanos = 0;
        Matcher matcher = DAY_PATTERN.matcher(cacheSpec);
        if (matcher.matches()) {
            String daySpec = matcher.group(1);
            daySpec = daySpec.substring(0, daySpec.length() - 1);
            specNanos = specNanos + TimeUnit.DAYS.toNanos(Long.parseLong(daySpec));
            cacheSpec = cacheSpec.substring(daySpec.length() + 1);
        }
        matcher = HOUR_PATTERN.matcher(cacheSpec);
        if (matcher.matches()) {
            String hourSpec = matcher.group(1);
            hourSpec = hourSpec.substring(0, hourSpec.length() - 1);
            specNanos = specNanos + TimeUnit.HOURS.toNanos(Long.parseLong(hourSpec));
            cacheSpec = cacheSpec.substring(hourSpec.length() + 1);
        }
        matcher = MINUTE_PATTERN.matcher(cacheSpec);
        if (matcher.matches()) {
            String minuteSpec = matcher.group(1);
            minuteSpec = minuteSpec.substring(0, minuteSpec.length() - 1);
            specNanos = specNanos + TimeUnit.MINUTES.toNanos(Long.parseLong(minuteSpec));
            cacheSpec = cacheSpec.substring(minuteSpec.length() + 1);
        }
        if(specNanos != 0){
            matcher = ACCESS_PATTERN.matcher(cacheSpec);
            if(matcher.matches()){
                cb.expireAfterAccess(specNanos, TimeUnit.NANOSECONDS);
            } else {
                cb.expireAfterWrite(specNanos, TimeUnit.NANOSECONDS);
            }
            return cb;
        }
        matcher = SIZE_SPEC.matcher(cacheSpec);
        if (matcher.matches()) {
            cb.maximumSize(Long.parseLong(matcher.group()));
        }

        return cb;
    }


}

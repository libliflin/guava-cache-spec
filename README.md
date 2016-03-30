# guava-cache-spec
Builders are verbose sometimes.

    time-spec|size-spec;
    time-spec:  \d+d\d+h\d+m days hours minutes; optionals...
    size-spec:  \d+ ;
    
### examples:

    4h          expires 4h after write.
    4hw         expires 4h after write.
    4ha         expires 4h after access.
    4           set eviction based on 4 elements.

### docs for underlying cache.
* http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/cache/CacheBuilder.html
* https://github.com/google/guava/wiki/CachesExplained#eviction

### future work:
add a mb using https://github.com/DimitrisAndreou/memory-measurer and weights.

### related work:
http://camel.apache.org/cache.html

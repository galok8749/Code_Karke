package leetcode.lrucache;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LRUCacheTest {

    LRUCache lRUCache;
    @BeforeAll
    void init() {
        lRUCache = new LRUCache(2);
    }
    @Test
    public void testLRUCache() {
        lRUCache.put(1, 1);
        assertEquals(1, lRUCache.getNodeHashMap().size());

        lRUCache.put(2, 2); // cache is {1=1, 2=2}
        assertEquals(2, lRUCache.getNodeHashMap().size());

        lRUCache.put(2,22);
        assertEquals(22, lRUCache.get(2));

        assertEquals(1, lRUCache.get(1));

        lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        assertEquals(2, lRUCache.getNodeHashMap().size());

        assertEquals(-1, lRUCache.get(2));
        lRUCache.put(4,4);
        assertEquals(2, lRUCache.getNodeHashMap().size());

        assertEquals(-1, lRUCache.get(1));
        assertEquals(3, lRUCache.get(3));
        assertEquals(4, lRUCache.get(4));
    }
}
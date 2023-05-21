package systemdesign;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RateLimiterServiceTest {
    public RateLimiterService rateLimiterService;

    @Test
    void check_success_logic() {
        rateLimiterService = new RateLimiterService(2, 3);
        Assertions.assertTrue(rateLimiterService.rateLimit(1));
        Assertions.assertTrue(rateLimiterService.rateLimit(1));
        Assertions.assertTrue(rateLimiterService.rateLimit(1));
        Assertions.assertFalse(rateLimiterService.rateLimit(1));

        Assertions.assertTrue(rateLimiterService.rateLimit(2));
        Assertions.assertTrue(rateLimiterService.rateLimit(2));
        Assertions.assertTrue(rateLimiterService.rateLimit(2));
        Assertions.assertFalse(rateLimiterService.rateLimit(2));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(rateLimiterService.rateLimit(1));
        Assertions.assertTrue(rateLimiterService.rateLimit(1));
        Assertions.assertTrue(rateLimiterService.rateLimit(1));
        Assertions.assertFalse(rateLimiterService.rateLimit(1));

        Assertions.assertTrue(rateLimiterService.rateLimit(2));
        Assertions.assertTrue(rateLimiterService.rateLimit(2));
        Assertions.assertTrue(rateLimiterService.rateLimit(2));
        Assertions.assertFalse(rateLimiterService.rateLimit(2));
    }
}
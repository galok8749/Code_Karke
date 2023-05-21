package systemdesign;

/*
Imagine we are building an application that is used by many different customers.
We want to avoid one customer being able to overload the system by sending too many requests,
so we enforce a per-customer rate limit. The rate limit is defined as:

"Each customer can make X requests per Y seconds"

// Perform rate limiting logic for provided customer ID. Return true if the
// request is allowed, and false if it is not.
boolean rateLimit(int customerId)

public class RateLimiterService {
  //The code should be thread-safe
  public boolean rateLimit(int customerId){
  //will return true if request will be served || false if rate limiting has to be done.
  }
}
O(1)
*/

import java.util.HashMap;
import java.util.Map;

/*
* Dry run =>
* customer => Netflix, Amazon, Google, Atlassian, Apple
* rule of rate-limit => X requests per Y sec => 3 req per 2 secs => X = 3, Y = 2
*
* t => 1, c1 => true  requestCount = 3, startTimeStamp = 1
* t => 1, c1 => true
* t => 1, c1 => true
* t => 1, c1 => false => 4 > X => false
*
* t = 1 => c2 => true
* t = 2 => c1 => false
* t = 3 => c1 => true because window has moved. => StartTimeStamp => 3
*
* how to model the window => left ====== right => startTimeStamp of window => right = startTimeStamp + Y
*
* */
public class RateLimiterService {
    private int windowSizeInSeconds;
    private int windowRequestsAllowed;
    private Map<Integer, CustomerData> customerDataMap;
    public RateLimiterService(int windowSizeInSeconds, int windowRequestsAllowed) {
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.windowRequestsAllowed = windowRequestsAllowed;
        customerDataMap = new HashMap<>();
    }

    public boolean rateLimit(int customerId) {
        final long currentTimeInSecs = System.currentTimeMillis() / 1000;

        final CustomerData currentCustomerData = customerDataMap.computeIfAbsent(customerId, k->new CustomerData(currentTimeInSecs, 0));

        final long windowStartTimeStampInSecs = currentCustomerData.getWindowStartTimeStampInSecs();

        if (currentTimeInSecs <= windowStartTimeStampInSecs + windowSizeInSeconds - 1) {
            if (currentCustomerData.getRequestCount() + 1 <= windowRequestsAllowed) {
                currentCustomerData.incrementRequestCount(1);
                return true;
            } else {
                return false;
            }
        } else {
            customerDataMap.put(customerId, new CustomerData(currentTimeInSecs, 1));
            return true;
        }
    }

    private static class CustomerData {
        private long windowStartTimeStampInSecs;
        private int requestCount; // I assume that the count for request of any customer within any window would be less than Integer.MAX_VALUE

        public CustomerData(long windowStartTimeStampInSecs, int requestCount) {
            this.windowStartTimeStampInSecs = windowStartTimeStampInSecs;
            this.requestCount = requestCount;
        }

        public long getWindowStartTimeStampInSecs() {
            return windowStartTimeStampInSecs;
        }

        public int getRequestCount() {
            return requestCount;
        }

        public void incrementRequestCount(int incrementCount) {
            this.requestCount +=incrementCount;
        }
    }

}

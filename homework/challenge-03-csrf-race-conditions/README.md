# Challenge 3: CSRF & Race Conditions

**Difficulty:** 5/5  
**Estimated Time:** 2-3 hours  
**Points:** 20 (+ 5 bonus)

---

## Scenario

A Spring Boot payment gateway allows customers to transfer funds between accounts. The current implementation lacks CSRF protection and performs balance updates in a non-atomic fashion, enabling race-condition exploits and double-spend attacks.

**Business Requirements:**
- Users can submit transfers via browser and mobile apps
- Transfers must be idempotent and auditable
- Concurrency should support burst traffic during peak hours
- Failed or suspicious transactions must be logged for review

**Security Requirements:**
- Enforce CSRF protection for browser-based clients
- Prevent simultaneous race conditions on account balances
- Provide idempotency keys for retries
- Ensure audit logging for success/failure scenarios

---

## Vulnerable Code

### `TransferController.java`

```java
@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private final AccountRepository accounts;

    @PostMapping
    public ResponseEntity<Map<String, Object>> transfer(@RequestBody TransferRequest request) {
        Account from = accounts.findByNumber(request.from()).orElseThrow();
        Account to = accounts.findByNumber(request.to()).orElseThrow();

        if (from.getBalance().compareTo(request.amount()) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "FAILURE", "reason", "Insufficient funds"));
        }

        // VULNERABILITY: No synchronization or transactional boundary
        from.setBalance(from.getBalance().subtract(request.amount()));
        to.setBalance(to.getBalance().add(request.amount()));
        accounts.save(from);
        accounts.save(to);

        return ResponseEntity.ok(Map.of("status", "SUCCESS", "reference", UUID.randomUUID().toString()));
    }
}
```

### `transfer.html`

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Transfer Funds</title>
</head>
<body>
<form method="post" action="/api/transfer">
    <label>From Account <input name="from"></label>
    <label>To Account <input name="to"></label>
    <label>Amount <input name="amount" type="number" step="0.01"></label>
    <!-- VULNERABILITY: Missing CSRF token -->
    <button type="submit">Send</button>
</form>
</body>
</html>
```

---

## Attack Scenarios
- CSRF attack from malicious site submitting hidden form
- Race condition via concurrent requests exploiting lack of locking
- Replay attack due to missing idempotency identifier
- Time-of-check/time-of-use gap after balance check but before debit
- Brute-forcing transfer references (UUIDs logged without randomness checks)

---

## Tasks
1. Add CSRF protection (Spring Security tokens or double-submit cookie pattern) for browser flows.
2. Make transfers atomic using transactions and row-level locks/optimistic locking.
3. Introduce idempotency keys enforced per account pair + amount.
4. Provide audit logging capturing attempts, failures, and anomalies.
5. Write tests covering CSRF rejection, race-condition mitigation, and idempotent retries.

---

## Success Criteria
- [ ] CSRF tokens validated for HTML form submissions
- [ ] Transfer operations wrapped in transactions with locking
- [ ] Idempotency keys prevent duplicate transfers
- [ ] Tests simulate concurrent requests without inconsistent balances
- [ ] Governance documentation updated with mitigation notes

---

## Bonus (Up to +5 pts)
- Detect and alert on anomalous transaction frequency
- Provide administrative dashboard summarizing blocked CSRF attempts
- Implement async eventing that preserves transactional safety

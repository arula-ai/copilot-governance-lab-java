# Challenge 5: Performance vs Security Tradeoffs

**Difficulty:** 5/5  
**Estimated Time:** 2-3 hours  
**Points:** 20 (+ 5 bonus)

---

## Scenario

A high-traffic API endpoint in the governance lab processes analytics exports. The legacy implementation focuses on performance but neglects security. Your task is to re-balance throughput, latency, and security controls while providing governance evidence for tradeoffs.

**Business Requirements:**
- Stream large CSV exports to authenticated clients
- Maintain sub-second response time for standard reports
- Support burst traffic during reporting windows
- Avoid breaking existing integrations

**Security Requirements:**
- Enforce rate limiting and anomaly detection
- Prevent data leakage across tenants
- Maintain encryption and integrity checks
- Provide auditable logs for suspicious usage

---

## Vulnerable Code

### `ReportController.java`

```java
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reports;

    @GetMapping("/export")
    public ResponseEntity<StreamingResponseBody> export(@RequestParam String customerId,
                                                         @RequestParam(defaultValue = "30") int days) {
        // VULNERABILITY: No authentication/authorization check
        StreamingResponseBody body = outputStream -> reports.streamCsv(customerId, days, outputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("Content-Disposition", "attachment; filename=export.csv");
        headers.setCacheControl("public, max-age=3600"); // VULNERABILITY: caches sensitive data

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
```

### `ReportService.java`

```java
@Service
public class ReportService {

    public void streamCsv(String customerId, int days, OutputStream outputStream) throws IOException {
        // VULNERABILITY: No tenant isolation check
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            writer.write("customer_id,metric,value\n");
            for (int i = 0; i < 100_000; i++) {
                writer.write(customerId + ",views," + ThreadLocalRandom.current().nextInt(1000) + "\n");
            }
        }
    }
}
```

---

## Attack Scenarios
- Unauthenticated user downloads another tenant’s data
- Attacker triggers massive exports causing resource exhaustion
- Shared cache exposes sensitive data to other clients
- Large exports allow timing side-channel on tenant IDs

---

## Tasks
1. Add authentication/authorization and tenant isolation checks.
2. Implement rate limiting and request throttling with metrics.
3. Stream data securely (chunked responses, encryption where appropriate).
4. Adjust caching headers to prevent sensitive data leakage.
5. Provide performance benchmarks before/after changes with commentary.

---

## Success Criteria
- [ ] Security controls documented with measured impact (latency/throughput)
- [ ] Rate limiting tests demonstrate blocked abusive patterns
- [ ] Authorization enforced with tenant validation
- [ ] Logs capture anomalous usage with context
- [ ] Governance documentation updated with tradeoff analysis

---

## Bonus (Up to +5 pts)
- Integrate adaptive throttling based on customer tier
- Add circuit breaker fallback for downstream dependencies
- Provide Grafana/Prometheus dashboards highlighting improvements

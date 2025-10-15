# Advanced Security Homework Challenges

## Overview

These advanced challenges stretch your knowledge of Spring Boot application security. Each scenario builds on the core workflow but introduces sophisticated attack vectors, governance requirements, and performance considerations.

**Estimated Time:** 8-12 hours total  
**Difficulty:** Advanced  
**Prerequisites:** Completed all workflow stages (0-5)

---

## Challenge Structure

Each challenge includes:
- **Vulnerable Code**: Subtle issues in controllers, services, or templates
- **Attack Scenarios**: Real-world exploitation examples
- **Security Requirements**: Specific controls to implement
- **Success Criteria**: Measurable outcomes and governance artifacts
- **Bonus Points**: Advanced defensive or monitoring techniques

---

## Grading Rubric

| Challenge | Points | Focus Area | Difficulty |
|-----------|--------|------------|------------|
| Challenge 1 | 20 pts | Advanced XSS & CSP | 4/5 |
| Challenge 2 | 20 pts | JWT Security & Session Management | 5/5 |
| Challenge 3 | 20 pts | CSRF & Race Conditions | 5/5 |
| Challenge 4 | 20 pts | Supply Chain Security | 4/5 |
| Challenge 5 | 20 pts | Performance vs Security | 5/5 |
| **Total** | **100 pts** | | |
| **Bonus** | Up to 25 pts | Advanced techniques | 5/5 |

### Grading Breakdown Per Challenge:
- **Vulnerability Identification** (4 pts): Find all security issues
- **Fix Implementation** (8 pts): Correct and auditable remediation
- **Security Tests** (4 pts): Comprehensive JUnit/MockMvc coverage
- **Documentation** (2 pts): Clear explanation of vulnerabilities and fixes
- **Code Quality** (2 pts): Clean, maintainable Spring code
- **Bonus** (up to 5 pts): Advanced defensive or monitoring techniques

---

## Challenges Overview

### Challenge 1: Advanced XSS & Content Security Policy
**Difficulty:** 4/5  
**Time:** 2-3 hours

Secure a rich-text publishing feature that must:
- Render user-provided HTML safely in Thymeleaf templates
- Apply strict Content Security Policy headers
- Sanitize SVG, CSS, and attribute-based injection vectors
- Support trusted fragments for whitelisted markup
- Document residual risks in governance artifacts

**Key Vulnerabilities:**
- DOM injection through `th:utext` or inline scripts
- Mutation XSS when serializing rich content
- CSS-based data exfiltration
- SVG JavaScript payloads
- `javascript:` URL schemes in anchor tags

---

### Challenge 2: JWT Security & Session Management
**Difficulty:** 5/5  
**Time:** 3-4 hours

Build a hardened authentication stack with:
- Signed JWT access tokens and rotating refresh tokens
- HttpOnly/SameSite/Secure cookies for session data
- Token reuse detection and revocation lists
- Robust expiration, clock skew, and audience validation
- Structured audit logging for authentication events

**Key Vulnerabilities:**
- Algorithm confusion attacks
- Token replay across tenants
- Insecure token persistence
- Missing expiration/issuer validation
- Session fixation during refresh flows

---

### Challenge 3: CSRF & Race Conditions
**Difficulty:** 5/5  
**Time:** 2-3 hours

Secure a financial transfer workflow against:
- CSRF on state-changing endpoints
- Race conditions and TOCTOU in balance checks
- Double-spend attempts via concurrent requests
- Idempotency violations
- Governance logging of rejected attempts

**Key Vulnerabilities:**
- Missing/invalid CSRF tokens for browser clients
- Non-atomic balance updates
- Lack of optimistic/pessimistic locking
- Insufficient idempotency keys for retry flows
- Verbose error messages leaking balances

---

### Challenge 4: Supply Chain Security
**Difficulty:** 4/5  
**Time:** 1-2 hours

Audit and secure build dependencies:
- Identify vulnerable Maven coordinates (direct + transitive)
- Integrate dependency scanning into GitHub Actions
- Establish version pinning and update cadence
- Prevent dependency confusion and repository spoofing
- Document SLSA/SBOM strategy for governance

**Key Vulnerabilities:**
- Outdated artifacts with active CVEs
- Transitive dependencies introducing risky classes
- Missing checksum verification for downloads
- Typosquatting artifacts in private repositories
- Build scripts executing untrusted code

---

### Challenge 5: Performance vs Security Tradeoffs
**Difficulty:** 5/5  
**Time:** 2-3 hours

Optimize a secure API without degrading defenses:
- Implement rate limiting with graceful degradation
- Cache responses without leaking tenant data
- Profile crypto-intensive operations safely
- Balance security headers with performance targets
- Provide governance evidence for chosen tradeoffs

**Key Challenges:**
- Timing attacks on login endpoints
- Cache poisoning and cache key isolation
- Side-channel risks from compression or response size
- DoS protections vs user experience
- JVM tuning vs security-sensitive defaults

---

## Learning Objectives

By completing these challenges, you will:

1. **Master Advanced XSS Prevention**
   - Apply CSP directives, nonces, and hash-based whitelists
   - Integrate sanitizer libraries with Thymeleaf
   - Recognize mutation XSS patterns in serialized markup

2. **Implement Secure Authentication**
   - Design resilient JWT workflows with rotation
   - Harden refresh token storage and invalidation
   - Instrument audit logging for forensic analysis

3. **Mitigate CSRF and Concurrency Risks**
   - Enforce CSRF protection aligned with Spring Security
   - Use locking/idempotency to stop double-spend attacks
   - Document transactional invariants in governance artifacts

4. **Strengthen Supply Chain Governance**
   - Automate dependency scanning and SBOM generation
   - Evaluate library risk and plan response cadences
   - Prevent dependency confusion in multi-repo setups

5. **Balance Performance and Security**
   - Profile JVM apps while respecting security controls
   - Implement secure caching, throttling, and rate limits
   - Communicate tradeoffs with evidence for reviewers

---

## Deliverables

For each challenge, submit:
- Updated Java/Spring code and templates
- JUnit/MockMvc tests with coverage evidence
- Updated documentation (`VULNERABILITIES.md`, `FIXES.md`, `docs/test-coverage.md`)
- Governance notes in `docs/workflow-tracker.md`
- Screenshots or logs demonstrating successful mitigations

### Bonus Opportunities (Up to 25 pts)
- Introduce feature flags for gradual rollout
- Integrate OpenTelemetry traces for security events
- Provide SBOM signed with cosign or similar tooling
- Add load testing evidence showing protected throughput
- Deliver threat models for each hardened feature

---

## Submission Checklist
- [ ] All challenges completed or documented if skipped
- [ ] Tests passing with coverage summaries attached
- [ ] Governance artifacts updated for each challenge
- [ ] PR template filled with Copilot usage details
- [ ] `./scripts/generate-report.sh` executed after changes
- [ ] Residual risks documented for reviewer discussion

Good luck, and remember to capture every decision in the governance artifacts so reviewers can trace your work!

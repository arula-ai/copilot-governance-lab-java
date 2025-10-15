# Security Checklist for Spring Boot Applications

## Pre-Deployment Security Review

### Authentication & Authorization
- [ ] HttpOnly/Secure/SameSite cookies configured for sessions
- [ ] Tokens generated with high entropy (SecureRandom)
- [ ] Session regeneration after login
- [ ] Server-side authorization enforced (method/URL level)
- [ ] Logout clears persistent artifacts (files, caches)
- [ ] Administrative endpoints gated by roles

### Input Validation & Sanitization
- [ ] Bean validation on request DTOs
- [ ] HTML sanitized before rendering
- [ ] URL parameters validated (no open redirects)
- [ ] File uploads restricted by type and size
- [ ] SQL/JPQL uses parameterized queries
- [ ] Error messages avoid reflecting raw input

### XSS Prevention
- [ ] Thymeleaf uses `th:text` or sanitized fragments
- [ ] No direct DOM manipulation with untrusted data
- [ ] Content Security Policy headers evaluated
- [ ] User-generated content passes through sanitizer library
- [ ] Templates free of inline event handlers populated by user data
- [ ] Query parameters encoded before display

### CSRF Protection
- [ ] CSRF protection enabled or alternative pattern documented
- [ ] SameSite cookies configured appropriately
- [ ] State-changing endpoints require token/header checks
- [ ] API clients authenticated before mutating data

### Data Protection
- [ ] No plaintext secrets written to disk logs
- [ ] Sensitive data encrypted in transit (HTTPS)
- [ ] Environment variables or secret store used for credentials
- [ ] Errors do not leak stack traces to end users
- [ ] Passwords hashed before persistence

### Error Handling
- [ ] Global exception handler returns sanitized responses
- [ ] Structured logging implemented with redaction
- [ ] Monitoring/alerting in place for critical failures
- [ ] HTTP status codes align with failure modes

### API Security
- [ ] Endpoints require authentication/authorization as appropriate
- [ ] Rate limiting or abuse detection documented
- [ ] Input size/time limits enforced
- [ ] CORS restricted to trusted origins
- [ ] Dependency versions free of known CVEs

### Testing
- [ ] Unit/integration tests cover security controls
- [ ] Jacoco coverage ≥ 60% overall (higher for remediated code)
- [ ] MockMvc tests assert security headers and failures
- [ ] Negative tests demonstrate hardening (XSS, CSRF, upload abuse)
- [ ] Dependency scans reviewed

### Dependency Security
- [ ] `mvn verify` passes without critical vulnerabilities
- [ ] Dependency updates tracked and scheduled
- [ ] No deprecated or unmaintained libraries in use
- [ ] License compliance verified

### Copilot-Specific Checks
- [ ] Generated code reviewed manually
- [ ] `.github/copilot-instructions.md` guardrails followed
- [ ] Secure patterns enforced even when Copilot suggests unsafe defaults
- [ ] Prompts and responses captured in documentation where required

## Sign-Off
- [ ] Code reviewed by security/governance lead
- [ ] Governance artifacts (`VULNERABILITIES.md`, `FIXES.md`, `COPILOT_USAGE.md`) refreshed
- [ ] Quality gates (`./scripts/run-all-checks.sh`, `./scripts/generate-report.sh`) executed
- [ ] Release notes updated with security context

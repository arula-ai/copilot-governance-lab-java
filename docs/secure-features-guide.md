# Stage 4 – Implement Secure Features with GitHub Copilot

## Objective
Use GitHub Copilot and the repository guardrails to implement secure Spring Boot features that harden the application once baseline vulnerabilities are addressed.

## Prerequisites
- Completed Stage 3 (security tests in place)
- GitHub Copilot enabled in your IDE
- Understanding of `.github/copilot-instructions.md` and current risk register

## Overview
You will introduce secure patterns that replace the intentionally vulnerable behaviors. Each feature should be scoped, planned, implemented, and tested with Copilot’s assistance while documenting evidence for governance.

## Instructions

### Plan Before Building Features

- Start in Copilot Chat **Plan Mode** to outline the hardening work: target classes, architectural changes, new documentation, and validation steps.
- Record the strategy in `docs/plans/stage4-plan.md`, including:
  - Files to be updated/created
  - Required Spring components (filters, interceptors, templates)
  - Test coverage expectations
  - Documentation updates (`VULNERABILITIES.md`, `FIXES.md`, `docs/test-coverage.md`)
- Confirm the plan references guardrails from `.github/copilot-instructions.md`, `LAB_ACTION_GUIDE.md`, and `SECURITY.md`.

### Feature Track 1: Harden Authentication Flow
**Goal:** Replace the insecure token handling.

Recommended tasks:
1. Create a `SecureAuthService` that stores tokens server-side using HttpOnly cookies and hashed secrets.
2. Introduce a Spring Security filter or interceptor to validate tokens and reject missing/expired ones.
3. Update `PageController` to regenerate sessions after login and prevent open redirects.
4. Provide migration steps/documentation comparing old vs. new flows.

Key validation commands:
```bash
mvn -Dtest=SecureAuthServiceTest test
mvn verify
```

### Feature Track 2: Sanitize Templates & Inputs
**Goal:** Prevent XSS across Thymeleaf templates.

Recommended tasks:
1. Replace `th:utext` usage with a sanitation helper that strips unsafe HTML.
2. Add a `TemplateSanitizer` component leveraging OWASP Java HTML Sanitizer or Spring’s `HtmlUtils`.
3. Update controllers to sanitize user-submitted bios before persistence.
4. Extend tests to confirm sanitization occurs (positive and negative cases).

Key validation commands:
```bash
mvn -Dtest=TemplateSanitizerTest test
mvn verify
```

### Feature Track 3: Secure File Uploads
**Goal:** Lock down the `/api/upload` endpoint.

Recommended tasks:
1. Validate MIME types and file extensions before saving.
2. Stream uploads to a safe temporary directory outside the project root.
3. Generate random filenames to avoid overwriting/serving uploaded content.
4. Restrict preview output to metadata instead of raw file contents.
5. Write MockMvc tests to assert validation failures.

Key validation commands:
```bash
mvn -Dtest=ApiControllerTest test
mvn verify
```

### Feature Track 4: Audit Logging & Monitoring
**Goal:** Replace sensitive logging with structured, compliant logs.

Recommended tasks:
1. Introduce a `SecurityAuditLogger` that redacts passwords and tokens.
2. Instrument login/logout/profile updates with audit events.
3. Update documentation to explain audit retention and review cadence.

Key validation commands:
```bash
mvn -Dtest=SecurityAuditLoggerTest test
./scripts/generate-report.sh
```

### Documentation & Evidence
For each feature track:
- Update `VULNERABILITIES.md` and `FIXES.md` with remediation details.
- Capture test commands, outcomes, and coverage notes in `docs/test-coverage.md`.
- Document architectural decisions in `docs/workflow-tracker.md` during hand-offs.
- Attach artefacts (logs, screenshots) as needed.

### Suggested Prompts for Copilot
Use prompts similar to:
```
According to our lab instructions, generate a Spring Boot filter that enforces HttpOnly session cookies, rejects missing tokens, and logs redacted audit events.
```

Or
```
Help me refactor dashboard.html to remove th:utext usage by introducing a sanitization helper that preserves basic formatting but strips scripts.
```

### Verification Checklist
- [ ] Secure token storage implemented and tested
- [ ] Templates render sanitized HTML
- [ ] File uploads restricted and stored safely
- [ ] Sensitive logging replaced with audit events
- [ ] Documentation and governance artefacts updated

Move to Stage 5 after validation confirms all new features align with guardrails and evidence has been logged.

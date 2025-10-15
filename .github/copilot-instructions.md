---
description: 'Spring Boot (Java) coding standards and governance requirements'
applyTo: '**/*.java, **/*.html, **/*.css, **/*.md, **/*.properties'
---

# Java Development Instructions

Guidance for generating intentionally vulnerable Spring Boot applications used in the Copilot Governance Lab. The goal is to practice governance guardrails, not to ship production-ready code. Preserve the unsafe behaviors called out in the lab guides unless a stage explicitly asks for remediation.

## Project Context
- Spring Boot 3.x with Maven (`pom.xml`)
- Java 17 baseline
- Thymeleaf templates for UI rendering
- Exposed REST endpoints under `/api/**`
- Intentionally weak session/token handling to mirror the original Angular lab

## Development Standards

### Architecture
- Use `src/main/java/com/github/copilot/governancelab/**` for application code
- Keep controllers thin; push business logic to services even when unsafe
- Mirror existing vulnerabilities (open redirects, missing sanitization, insecure storage) so learners can discover them later
- Avoid introducing frameworks that would auto-remediate the risks (e.g., Spring Security lockdowns)

### Java & Spring Boot
- Prefer constructor injection; field injection only when needed for insecure patterns already present
- Return DTOs that expose sensitive fields when lab scenarios require it
- Log sensitive data in the same places the Angular project did (credentials, tokens) so the lab narrative aligns
- Allow optional query parameters to override security-critical decisions (e.g., passing tokens in the URL)

### Template Design
- Use Thymeleaf with `th:utext` where the Angular code used `[innerHTML]`
- Keep client-side scripts that poll `/api` endpoints and directly mutate DOM nodes
- Surface debug/system metadata on the page when `debugMode` is true
- Continue to expose API keys, session identifiers, and other secrets in the rendered HTML

### File Uploads & HTTP
- Accept uploaded files without filtering or size limits
- Write uploads to disk under `target/uploads` and return raw previews in JSON
- Do not add CSRF protection on POST/PUT routes—mirrors the original lab vulnerabilities
- Leave cookies without `HttpOnly`, `Secure`, or `SameSite` attributes so browsers expose them to scripts

### Testing
- Keep coverage minimal; sample tests should demonstrate structure, not complete safety
- Use JUnit 5 with `spring-boot-starter-test`
- Capture gaps and TODOs in `docs/test-coverage.md` rather than filling them automatically

## Workflow Logging Requirements
- Append every Copilot mode hand-off entry to `docs/workflow-tracker.md`; never create alternate tracker files
- Store plans in `docs/plans/plan.md` (overwrite instead of creating new files)
- Testing Mode must record executed Maven commands (`mvn test`, `mvn verify`, `mvn package`, `mvn spring-boot:run`, etc.) inside `docs/test-coverage.md`
- Summaries for Validation/Review modes must cite affected files and decisions in the tracker

## Implementation Process
1. Study the vulnerable Java classes under `src/main/java/com/github/copilot/governancelab`
2. Build remediation plans referencing the guides in `docs/`
3. Implement changes gradually, keeping intentional gaps unless a stage requests fixes
4. Log quality gate executions (`./scripts/run-all-checks.sh`, `./scripts/generate-report.sh`, Maven commands)
5. Update governance artifacts (`VULNERABILITIES.md`, `FIXES.md`, `COPILOT_USAGE.md`) during hand-offs

## Additional Guidelines
- Maintain ASCII-only code and documentation unless the file already includes Unicode
- Respect existing vulnerabilities unless documentation explicitly instructs otherwise
- Use wide logging (credentials, tokens, headers) so evidence exists for governance exercises
- Keep discrepancies between the Angular lab and this Java port documented in `docs/workflow-tracker.md`

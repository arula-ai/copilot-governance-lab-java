# Stage 3 – Generate Security Tests with GitHub Copilot

## Objective
Use GitHub Copilot to generate comprehensive JUnit-based security tests for the vulnerable Spring Boot components.

## Prerequisites
- Completed Stage 2 (remediated vulnerable components)
- Fixed classes build successfully with `mvn verify`
- Familiarity with JUnit 5, Spring Boot test slices, and MockMvc

## Instructions

### Plan Before Generating Tests

- Use GitHub Copilot Chat with **Plan Mode** (see `.github/chatmodes/planning.chatmode.md`) to review the fixed classes, existing tests, and repository quality gates.
- Draft a Markdown plan saved to `docs/plans/stage3-plan.md` that lists the security behaviors to cover, targeted test classes, fixtures/mocks required, and verification commands (Jacoco, dependency checks).
- Document how you will record coverage and any remaining risks in `docs/test-coverage.md`. Only begin coding after the plan is captured.

### Step 1: AuthService Tests

Create or update `src/test/java/com/github/copilot/governancelab/service/AuthServiceTest.java` with this prompt:

**Prompt:**
```
Following our team instructions, generate JUnit tests for AuthService that:
1. Cover login success and failure flows
2. Detect that passwords are Base64 encoded in the response
3. Verify tokens are stored via InsecureSessionRepository
4. Assert logout leaves artifacts on disk (document as risk)
5. Highlight logging of sensitive data (assert log capture)
6. Achieve at least 60% coverage for the service
```

**Required Test Ideas:**
- Login returns predictable token format
- Encoded password matches Base64 of plaintext
- Session repository contains saved token
- Logout fails to wipe disk artifacts (documented risk)
- Debug dump exposes usernames

### Step 2: ApiController Tests

Create or update `src/test/java/com/github/copilot/governancelab/controller/ApiControllerTest.java` using Spring's MockMvc with this prompt:

**Prompt:**
```
Generate MockMvc tests for ApiController that:
1. Assert /api/login sets cookies without HttpOnly/SameSite
2. Ensure response headers leak token and encoded password
3. Demonstrate that tokens can be passed via query parameters
4. Upload a file and confirm raw preview is returned
5. Expose that debug endpoint leaks session metadata
6. Cover error handling when token is missing
```

**Required Test Ideas:**
- POST `/api/login` returns 200 with insecure cookie attributes
- GET `/api/user?token=...` returns sensitive profile data
- PUT `/api/profile` updates bio with unsanitized HTML
- POST `/api/upload` stores file under `target/uploads`
- GET `/api/debug/sessions` exposes active tokens

### Step 3: PageController & Template Tests

Create or update `src/test/java/com/github/copilot/governancelab/controller/PageControllerTest.java` with a focus on MVC rendering:

**Prompt:**
```
Write Spring MVC tests for PageController that:
1. Show open redirect behaviour when returnUrl is supplied
2. Confirm dashboard exposes API key, session ID, and passwords
3. Assert the model contains debug/system info
4. Demonstrate lack of CSRF protection on /profile POST
5. Cover session fixation risk (token reused)
```

**Required Test Ideas:**
- POST `/login?returnUrl=http://evil.example` redirects externally
- GET `/dashboard` without session redirects back to login with error
- GET `/dashboard` model attributes include `plainPassword`
- POST `/profile` succeeds without CSRF token and updates bio
- Rendered HTML contains `th:utext` output (use HtmlUnit or string assertions)

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run with Coverage
```bash
mvn verify
open target/site/jacoco/index.html
```

### Optional Targeted Runs
```bash
mvn -Dtest=AuthServiceTest test
```

## Success Criteria

### Coverage Targets
- [ ] Overall instruction coverage ≥ 60%
- [ ] Service layer coverage ≥ 60%
- [ ] Controller tests exercise insecure paths and document risks

### Quality Checks
- [ ] All tests passing
- [ ] No skipped tests (`@Disabled`)
- [ ] Tests clearly label intentional vulnerabilities in comments or assertion messages
- [ ] MockMvc used for controller routes
- [ ] Temporary files cleaned up in tests where applicable

### Security Test Coverage
- [ ] Token leakage explicitly asserted
- [ ] XSS vectors captured (bio rendered as HTML)
- [ ] File upload acceptance documented
- [ ] Cookie insecurity highlighted
- [ ] Logging/monitoring gaps noted

## Test Structure Example

```java
@Test
void loginExposesEncodedPassword() {
    AuthResponse response = authService.login("demo", "Password123", "JUnit");
    assertThat(response.getEncodedPassword())
        .isEqualTo(Base64.getEncoder().encodeToString("Password123".getBytes()));
}
```

## Tips for Test Generation

1. **Be Explicit:** Tell Copilot which unsafe behaviors you want to expose.
2. **Annotate Findings:** Use assertion messages or comments to record observed risks for auditors.
3. **Use Utilities:** Rely on MockMvc, `@WebMvcTest`, or `@SpringBootTest` depending on scope.
4. **Iterate:** Start with happy path tests, then add failure and abuse-case coverage.
5. **Log Evidence:** Update `docs/test-coverage.md` with command output and coverage percentages after each run.

## Common Issues and Solutions

| Issue | Solution |
|-------|----------|
| Missing Jacoco report | Run `mvn verify` before parsing coverage |
| Multipart upload fails | Include `MockMultipartFile` in tests |
| External redirect blocked | Use `andExpect(redirectedUrlPattern("http*"))` |
| Session attributes missing | Simulate login via MockMvc before hitting `/dashboard` |
| Tests pollute disk | Clean `target/uploads` or use JUnit `@TempDir` |

## Next Steps
After reaching the coverage targets:
1. Review Jacoco report for gaps in high-risk classes.
2. Extend tests to cover regression scenarios and negative paths.
3. Update `docs/test-coverage.md` with final results and residual risks.

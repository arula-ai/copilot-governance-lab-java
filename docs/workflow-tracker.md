# Project Workflow Tracker

Use this file to track progress through the delivery workflow.

## Stage Checklist

### Stage 0: Prepare & Align (10 min)
- [ ] Read `.github/copilot-instructions.md`
- [ ] Understand the security requirements
- [ ] Review the coding standards
- [ ] Familiarize yourself with team guidelines and lab stages
- [ ] Capture initial assumptions in Copilot Plan Mode and log them here

**Key Takeaways:**
```
(Add your notes here)
```

---

### Stage 1: Baseline Assessment (15 min)
- [ ] Audit vulnerable components (`AuthService.java`, `ApiController.java`, `PageController.java`, `dashboard.html`)
- [ ] Map findings to OWASP categories
- [ ] Identify affected assets and attack surfaces
- [ ] Record required governance artifacts and quality gates

**Vulnerabilities Identified:**
```
(List vulnerabilities, impacted assets, severity)
```

---

### Stage 2: Remediate with Copilot (25 min)
- [ ] Address `AuthService` risks (credential logging, insecure storage, role trust)
- [ ] Address `ApiController` risks (cookies, uploads, token leakage)
- [ ] Address `PageController`/template risks (open redirects, XSS, debug leakage)
- [ ] Document repository changes in `VULNERABILITIES.md` / `FIXES.md`
- [ ] Run `mvn test` and `mvn verify`

**Vulnerabilities Fixed:**
```
(List the vulnerabilities you fixed and how)
```

**Copilot Prompts That Worked Well:**
```
(Document successful prompts for future reference)
```

---

### Stage 3: Generate Security Tests (20 min)
- [ ] Generate/extend tests for `AuthService`
- [ ] Generate/extend tests for `ApiController`
- [ ] Generate/extend tests for `PageController` / templates
- [ ] Run `mvn verify` and capture Jacoco coverage
- [ ] Update `docs/test-coverage.md`

**Test Coverage Achieved:**
- AuthService: ____%
- ApiController: ____%
- PageController: ____%

**Testing Insights:**
```
(Add notes about test generation with Copilot)
```

---

### Stage 4: Implement Secure Features (45 min)
- [ ] Harden authentication flow (HttpOnly cookies, session regeneration)
- [ ] Sanitize templates and user input
- [ ] Secure file upload pipeline
- [ ] Introduce audit logging / monitoring
- [ ] Run `mvn verify` and relevant targeted tests

**Features Implemented:**
```
(List features and key implementation details)
```

**Challenges Encountered:**
```
(Document any issues and how you solved them)
```

---

### Stage 5: Governance Validation & Reporting (20 min)
- [ ] Run `mvn verify`
- [ ] Run `./scripts/run-all-checks.sh`
- [ ] Run `./scripts/generate-report.sh`
- [ ] Review `governance-report.md`
- [ ] Update `VULNERABILITIES.md`, `FIXES.md`, `COPILOT_USAGE.md`
- [ ] Prepare submission artefacts (branch, PR, checklist)
- [ ] Create feature branch and commit changes
- [ ] Push to remote and open PR
- [ ] Populate PR template and request review

**PR Link:** _______________

---

## Overall Learning

### Security Patterns Learned
1. 
2. 
3. 

### Copilot Best Practices Discovered
1. 
2. 
3. 

### Areas for Improvement
1. 
2. 
3. 

---

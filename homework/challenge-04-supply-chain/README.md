# Challenge 4: Supply Chain Security

**Difficulty:** 4/5  
**Estimated Time:** 1-2 hours  
**Points:** 20 (+ 5 bonus)

---

## Scenario

The lab’s Java application depends on numerous open-source libraries. A recent audit revealed outdated dependencies, unpinned versions, and missing SBOM documentation. You must harden the build pipeline against supply chain threats.

**Business Requirements:**
- Maintain developer velocity while enforcing dependency governance
- Track third-party licenses and vulnerability status
- Automate alerts for outdated or risky components
- Support reproducible builds across environments

**Security Requirements:**
- Detect known CVEs in direct and transitive dependencies
- Pin Maven coordinates to explicit versions
- Prevent dependency confusion/typosquatting attacks
- Produce a signed Software Bill of Materials (SBOM)
- Integrate scans into CI/CD workflows

---

## Vulnerable Build Configuration

### `pom.xml` (snippet)

```xml
<dependencies>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>[0.9,)</version> <!-- VULNERABILITY: version range -->
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.6</version> <!-- Known CVEs -->
    </dependency>
</dependencies>
```

### `quality-gates.yml`

```yaml
- name: Dependency scan
  run: mvn dependency:tree
  # VULNERABILITY: No vulnerability scanning or SBOM generation
```

---

## Attack Scenarios
- Malicious update to `jjwt` resolved via version range
- Compromised transitive dependency shipping malware
- Dependency confusion by publishing `com.github.copilot:governance-lab`
- Missing SBOM prevents rapid incident response

---

## Tasks
1. Pin all dependencies and document rationale for risky components.
2. Upgrade vulnerable libraries or document compensating controls.
3. Integrate dependency scanning (OWASP Dependency-Check, Snyk, osv-scanner, etc.).
4. Generate SBOM (CycloneDX or SPDX) and store artifact for auditors.
5. Update CI workflow to fail on high/critical vulnerabilities.

---

## Success Criteria
- [ ] `pom.xml` uses fixed versions with comments on exceptions
- [ ] CI workflow runs automated scans and uploads reports
- [ ] SBOM generated and referenced in governance docs
- [ ] Vulnerability findings triaged (fixed or documented)
- [ ] `evaluation/tasks.yml` reflects new dependency policies

---

## Bonus (Up to +5 pts)
- Sign SBOM artifact with `cosign` or GPG
- Add provenance metadata (SLSA attestations)
- Build container image with minimal base and image scanning

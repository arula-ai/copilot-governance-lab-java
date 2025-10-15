# Test Coverage & Linting Log

Maintain this log whenever Testing Mode (also acting as Linting Mode) runs.

## Session Template

**Date:** ____________  
**Stage:** ____________  
**Operator:** ____________

### Initial Assumptions & Targets
- Frameworks / tooling: (e.g., JUnit 5, MockMvc, Jacoco, Maven verify)
- Coverage goals: Instruction ≥60%, Branches ≥60%, Critical services ≥60%
- Quality gates: `mvn test`, `mvn verify`, `./scripts/run-all-checks.sh`
- Additional objectives: (e.g., dependency audit, regression focus)

### Commands Executed
| Command | Result | Notes |
|---------|--------|-------|
| `mvn test` | | |
| `mvn verify` | | |
| `./scripts/run-all-checks.sh` | | |
| `./scripts/generate-report.sh` | | |
| Additional command | | |

### Coverage Summary
- Instruction: ____%
- Branches: ____%
- Complexity: ____%
- High-risk classes covered: ____%

### Findings & Follow-ups
- Pass/Fail summary:
- Discovered issues or regressions:
- Required fixes or retests:

### Next Actions
- [ ] Update tests (describe)
- [ ] Re-run command (describe)
- [ ] Escalate to developer / reviewer
- [ ] Other: __________________

---

Repeat the template above for each testing/linting session to maintain a complete audit trail.

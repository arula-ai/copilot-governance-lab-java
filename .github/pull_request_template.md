## PR Type
- [ ] Bug fix
- [ ] Feature
- [ ] Security patch
- [ ] Performance improvement
- [ ] Documentation

## Copilot Usage Declaration
- [ ] Copilot was used in this PR
- [ ] Generated code percentage: ____%
- [ ] All suggestions were reviewed for correctness
- [ ] Team instructions were followed

## Quality Checklist
- [ ] Unit tests written/updated (JUnit/MockMvc) — coverage: ___%
- [ ] Integration tests updated or rationale documented
- [ ] Jacoco coverage ≥ 60% or exception noted
- [ ] `mvn clean verify` passing
- [ ] Documentation updated
- [ ] No `System.out.println`/`printStackTrace` debugging left behind

## Security Review
- [ ] Input sanitization implemented
- [ ] Output encoding prevents XSS in templates/responses
- [ ] Authentication/authorization paths verified (if applicable)
- [ ] File/path handling hardened (no traversal or unsafe writes)
- [ ] Dependencies reviewed (`mvn dependency:tree`)
- [ ] Secrets and credentials kept out of code/config

## Performance Checklist
- [ ] No unbounded loops or excessive allocations introduced
- [ ] Blocking IO handled responsibly (streams closed, timeouts set)
- [ ] Database/remote calls minimized or batched (if applicable)
- [ ] Caching/connection pooling considered or documented
- [ ] Logging remains at appropriate levels

## Accessibility
- [ ] ARIA labels added
- [ ] Keyboard navigation works
- [ ] Screen reader tested
- [ ] Color contrast verified

## Testing Evidence
- [ ] All tests passing
- [ ] New tests added for changes
- [ ] Coverage maintained/improved
- [ ] Manual testing completed

## Copilot-Specific Checks
- [ ] No hardcoded secrets
- [ ] Business logic manually verified
- [ ] Generated code follows Spring Boot & Java conventions (DI, transactions, validation)
- [ ] Logging and exception handling reviewed for sensitive data
- [ ] Data validation/business rules double-checked

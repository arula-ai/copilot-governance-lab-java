# Contributing to Copilot Governance Lab

Thank you for your interest in contributing to the Copilot Governance Lab (Java edition)!

## Getting Started

1. Fork the repository.
2. Clone your fork locally.
3. Run `./scripts/setup-lab.sh` to verify prerequisites.
4. Create a new branch for your changes.

## Development Workflow

### Before Making Changes
1. Review `.github/copilot-instructions.md` for coding standards.
2. Ensure your IDE has GitHub Copilot enabled.
3. Pull the latest changes from `main`.
4. Read the relevant guides in `docs/` for the stage you are working on.

### Making Changes
1. Follow the team instructions in `.github/copilot-instructions.md`.
2. Write or update unit/integration tests.
3. Ensure all tests pass: `mvn test`.
4. Run full verification: `mvn verify`.
5. Execute governance scripts: `./scripts/run-all-checks.sh`.
6. Capture coverage results in `docs/test-coverage.md` when applicable.

### Using GitHub Copilot
- Always reference team instructions when prompting Copilot.
- Review all generated code carefully.
- Verify that generated code aligns with Spring Boot patterns and preserves intentional lab vulnerabilities when required.
- Ensure generated tests meaningfully assert security behaviors.
- Never commit secrets or credentials.

## Pull Request Process

1. Update documentation for any changed functionality.
2. Fill out the PR template completely.
3. Declare Copilot usage percentage.
4. Ensure all CI checks pass.
5. Request review from appropriate team members.

### PR Checklist
- [ ] Code follows team instructions and lab guardrails
- [ ] Tests written/updated (coverage ≥60% or documented exception)
- [ ] `mvn verify` passing
- [ ] `./scripts/run-all-checks.sh` executed
- [ ] Documentation updated (README, guides, trackers)
- [ ] No sensitive data logged or stored

## Code Review Guidelines

### As a Reviewer
- Verify Copilot-generated code is appropriate
- Check for security regressions or missing mitigations
- Ensure tests are comprehensive and document residual risks
- Validate Spring Boot and Java best practices
- Provide constructive, actionable feedback

### As an Author
- Respond to feedback promptly
- Explain Copilot usage decisions when asked
- Update code based on review comments
- Re-request review after changes

## Security

### Reporting Vulnerabilities
- Do not open public issues for security vulnerabilities
- Email security@example.com with details
- Include reproduction steps
- Allow time for patch before disclosure

### Security Standards
- All PRs must execute `mvn verify`
- Governance scripts must succeed unless a documented risk is accepted
- No sensitive data in source code or commits
- Follow OWASP Top 10 guidance for Java web apps

## Testing

### Test Requirements
- All new code must have unit or integration tests
- Minimum 60% Jacoco coverage overall (with higher targets for remediated modules)
- Tests must verify both secure behavior and intentional weaknesses
- Use meaningful test names and assertion messages
- Mock external resources (file system, network) when appropriate

### Running Tests
```bash
mvn test                # Run unit tests
mvn verify              # Tests + Jacoco report
mvn -Dtest=ClassName test  # Run targeted tests
```

## Documentation

### What to Document
- New features and services
- Security considerations and residual risks
- Usage examples for controllers/services/templates
- Breaking changes and migration guidance

### Documentation Standards
- Use clear, concise language
- Include code snippets where helpful
- Keep `README.md` and `docs/` guides current
- Document public APIs and endpoints
- Add inline comments for complex logic sparingly

## Commit Messages

### Format
```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation only
- `style`: Code formatting
- `refactor`: Code restructuring
- `test`: Adding tests
- `chore`: Maintenance

### Example
```
fix(auth): remove plaintext password logging

Replaces System.out logging with structured audit logger
and adds tests documenting the previous behavior.
```

## Code Style

### Java
- Use Java 17 features where appropriate
- Prefer constructor injection
- Keep classes focused and cohesive
- Use descriptive names for methods and variables
- Avoid catching generic `Exception` unless documenting the risk

### Spring Boot
- Separate controllers, services, and repositories
- Return DTOs instead of entities from controllers
- Log securely (redact secrets, use structured logging)
- Externalize configuration in `application.properties`
- Preserve intentional vulnerabilities only when required by the lab stage

### Testing
- Use JUnit 5 and AssertJ/MockMvc helpers
- Follow Arrange/Act/Assert pattern
- Provide assertion messages documenting security expectations
- Clean up temporary files and resources

## Questions?

- Check existing issues and discussions
- Review documentation in `/docs`
- Ask in team chat or discussions
- Open an issue for bugs or features

## License

By contributing, you agree that your contributions will be licensed under the project's license.

# Lab Action Guide

Follow these lean steps. After each stage, run Summarizer Mode with the Hand-Off prompt so progress lands in `docs/workflow-tracker.md`.

## Quick Reference

| Stage | Primary Modes | Core Artifacts / Commands |
| --- | --- | --- |
| 0 | Agent | `./scripts/setup-lab.sh`, `mvn spring-boot:run` |
| 1 | Agent → Testing | `.github/copilot-instructions.md`, `mvn test`, `mvn verify` |
| 2 | Planning → Verification Agent → Agent | `docs/vulnerability-guide.md`, `docs/test-coverage.md`, `plan.md` |
| 3 | Agent ↔ Need Review | `src/main/java/com/github/copilot/governancelab/**`, `VULNERABILITIES.md`, `FIXES.md` |
| 4 | Planning → Testing → Agent | `docs/testing-guide.md`, `mvn verify`, `docs/test-coverage.md` |
| 5 | Planning → Verification Agent → Agent ↔ Need Review | `docs/secure-features-guide.md`, feature specs |
| 6 | Testing → Agent | `mvn verify`, `./scripts/run-all-checks.sh`, `./scripts/generate-report.sh` |
| 7 | Agent ↔ Need Review | `homework/README.md` |
| 8 | Verification Agent → Agent ↔ Need Review | `SECURITY.md`, `homework/GRADING_RUBRIC.md`, PR template |

## Stage 0 – Environment Setup
- Agent Mode: `#runInTerminal ./scripts/setup-lab.sh`
- Agent Mode: `mvn spring-boot:run` (initial build issues are fine)
- Hand-Off: summarize setup status and blockers

## Stage 1 – Governance Alignment
- Agent Mode: read `.github/copilot-instructions.md` and each chatmode file
- Agent Mode: inspect available tools (wrench icon) without changing settings
- Testing Mode: run `mvn test` and `mvn verify`; log assumptions in `docs/test-coverage.md`
- Hand-Off: note guardrail highlights, command outcomes, gaps

## Stage 2 – Baseline Assessment
- Planning Mode: review `docs/vulnerability-guide.md` and current coverage
- Verification Agent: sanity-check the remediation plan against guardrails
- Agent Mode: capture file targets, risk notes, evidence needs
- Hand-Off: record the approved plan and open questions; save/update `docs/plans/plan.md`

## Stage 3 – Remediation
- Agent Mode: implement fixes in `src/main/java/com/github/copilot/governancelab/**` referencing the plan
- Need Review Mode: request feedback per change slice; fold responses back in Agent Mode
- Agent Mode: update `VULNERABILITIES.md` / `FIXES.md` as fixes ship
- Hand-Off: summarize files touched, decisions made, pending follow-ups

## Stage 4 – Security Test Generation
- Planning Mode (optional): outline missing security coverage using `docs/testing-guide.md`
- Testing Mode: run `mvn verify` until ≥60% coverage or documented rationale
- Agent Mode: capture coverage deltas and evidence paths in `docs/test-coverage.md`
- Hand-Off: log executed suites, pass/fail status, remaining test work

## Stage 5 – Secure Feature Implementation
- Planning Mode: break down required feature changes from `docs/secure-features-guide.md`
- Verification Agent: confirm the feature plan satisfies guardrails
- Agent Mode: build the feature; switch to Need Review Mode for review-ready slices
- Agent Mode: note evidence (commits, screenshots) for governance reporting
- Hand-Off: capture implemented pieces, validation outcomes, and TODOs

## Stage 6 – Governance Validation & Reporting
- Testing Mode: run `mvn verify`, `./scripts/run-all-checks.sh`, `./scripts/generate-report.sh`
- Agent Mode: refresh `VULNERABILITIES.md`, `FIXES.md`, `COPILOT_USAGE.md` with final status
- Hand-Off: state command results, report readiness, outstanding risks

## Stage 7 – Homework & Extras
- Agent Mode: complete `homework/README.md` tasks; involve Need Review Mode if validation is needed
- Agent Mode: gather artifacts for bonus work
- Hand-Off: document optional work finished and items left for later

## Stage 8 – Prepare Submission
- Verification Agent: walk `SECURITY.md` checklist and close gaps
- Agent Mode: review `homework/GRADING_RUBRIC.md`, push branch, open PR
- Need Review Mode: fill the PR template with governance evidence
- Hand-Off: confirm submission state, linked artifacts, next reviewer actions

## Mode Loop Reminder
- Planning → Verification Agent → Agent → Need Review → Testing
- Use the Summarizer Hand-Off after each stage so `docs/workflow-tracker.md` stays current without direct edits.

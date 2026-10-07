# CLAUDE.md

Claude Code entry point for the Full Engineering Agent Squad.

@AGENTS.md

## Always: the response gate

Before finalizing **any** response to the user, run `RESPONSE-GATE.md` (the man-in-the-middle
self-review: truthfulness · answered-the-ask · safety · squad-consistency · quality). Fix before
sending; never deliver a known-flawed answer. Optional `Stop` hook: `adapters/claude/hooks/response-gate.sh`.

## Always: the QA matrix travels with the change

Every change a person or API client can observe updates `qa/matriz/*.yaml` in the same PR — follow
`.claude/skills/qa-matrix-sync/SKILL.md` (see `AGENTS.md` → "Mandatory QA matrix policy"). A `Stop` hook
(`.claude/hooks/qa-matrix-guard.sh`) blocks finishing a turn that changed product code without touching the matrix.

## Using the squad in Claude Code

Install the squad's agents by copying the generated Claude adapters into a repo (or your home dir):

```bash
cp -r adapters/claude/agents/* <target-repo>/.claude/agents/
cp -r adapters/claude/skills/* <target-repo>/.claude/skills/
cp    templates/project-context.md <target-repo>/      # fill it in for the repo
# optional, Claude-only enforcement: cp -r adapters/claude/hooks/*.sh <target-repo>/.claude/hooks/
# or globally: replace <target-repo>/.claude with ~/.claude
```

The 29 agents then appear as subagents. Invoke **orchestrator** first for any non-trivial
request (especially in plan mode); it opens a task file, decomposes the work, scores complexity
1–5, assigns a capability tier, and routes to specialists. See `ORCHESTRATION.md` and
`workflow/README.md` (state contract).

## Agent registry

| Agent | Tier | Role |
|-------|------|------|
| orchestrator | high-reasoning | decompose, score, route, own task file (read-only code) |
| planner | high-reasoning | phased implementation plan (read-only) |
| plan-checker | high-reasoning | adversarial pre-execution plan gate (read-only) |
| architect | high-reasoning | ADRs, design review (read-only) |
| context-manager | balanced | summarize/compress sessions, keep task file + context lean |
| frontend-engineer | balanced | UI implementation |
| backend-engineer | balanced | server/API implementation |
| ai-engineer | high-reasoning | LLM code + token/cache optimization (owns Headroom) |
| ai-researcher | high-reasoning | technique/paper scouting, eval & experiment design, ablations, benchmark/model selection, reproducibility |
| cybersec-engineer | high-reasoning | security review (read-only) |
| infrastructure-engineer | balanced | IaC provisioning |
| database-specialist | high-reasoning | schema, queries, indexes, migrations |
| data-engineer | high-reasoning | ETL/ELT, pipelines, data contracts |
| performance-engineer | high-reasoning | profiling + optimization (measured) |
| devops-sre | balanced | CI/CD, deploy, alerting, incidents |
| code-reviewer | balanced | correctness + quality review (read-only) |
| tdd-guide | balanced | unit/integration test strategy + tests |
| e2e-test-engineer | balanced | Playwright behavior E2E tests |
| debugger | high-reasoning | root-cause a failure (read-first) |
| git-pr-specialist | balanced | commits, branches, PR descriptions |
| technical-writer | balanced | docs, API reference, changelogs |
| ux-designer | balanced | design specs + accessibility |
| product-manager | balanced | requirements + acceptance criteria |
| business-analyst | balanced | stakeholder narratives, release notes |
| business-strategist | high-reasoning | SWOT, business plan, pricing/monetization, GTM, market sizing, unit economics |
| ai-platform-strategist | high-reasoning | AI-as-consultancy per domain (DDD + Langfuse + strategy + ROI) |
| squad-optimizer | high-reasoning | self-learner — improves agents/skills/hooks from gaps |
| tool-learner | high-reasoning | researches a tool → knowledge card + integration kit |
| squad-customizer | high-reasoning | generates a per-customer squad into generated/<slug>/ |

## Skills

Reusable procedures in `skills/<name>/SKILL.md`, invoked on demand by agents. **Begin every task
with `using-the-squad`** — the entry-point skill that surfaces which others apply before you act:

| Skill | Used by |
|-------|---------|
| **using-the-squad** (start here) | every agent, every task |
| **using-integralis** | every agent, whenever the Integralis MCP server is connected |
| brainstorming · clarify-intent | orchestrator, planner |
| write-adr | architect |
| clean-code · solid-dry · design-patterns | frontend, backend, ai-engineer, code-reviewer |
| convention-over-configuration | frontend, backend, architect, code-reviewer |
| diagram-generator | architect, ai-platform-strategist, squad-customizer |
| secure-regex | backend, cybersec, code-reviewer, data-engineer |
| behavior-driven-testing | tdd-guide, e2e-test-engineer, code-reviewer |
| conventional-commits · pr-description · git-worktree-isolation | git-pr-specialist |
| code-review-checklist · traceability-matrix · receiving-code-review | code-reviewer |
| root-cause-analysis · debugging-method · root-cause-tracing · defense-in-depth · condition-based-waiting | debugger, devops-sre |
| observability-standards | backend, devops-sre, data-engineer |
| threat-modeling | cybersec-engineer |
| dependency-upgrade | backend, infrastructure-engineer |
| performance-profiling | performance-engineer |
| ai-cost-optimization · rag-design · grounded-generation · fine-tuning-strategy | ai-platform-strategist, ai-engineer |
| apify | data-engineer, backend |
| subagent-driven-development | orchestrator, planner |
| prompt-injection-defense | cybersec-engineer, every agent handling external input |
| writing-skills | squad-optimizer |

## Editing rule

Edit canonical `agents/<role>/SKILL.md` or `skills/<name>/SKILL.md`, then:

```bash
bash scripts/sync-adapters.sh   # regenerate adapters
bash scripts/eval.sh            # lint + routing surface
```

Never hand-edit files under `adapters/` — they are generated and overwritten (except
`adapters/claude/hooks/`, which is authored). The `squad-optimizer` agent runs this loop for you.

## Customization service

To generate a squad tailored to a customer's stack: run the `build-squad` meta-prompt (or feed
`wizard/` answers to `squad-customizer`). It reads `knowledge/` cards + `integrations/` kits (and
invokes `tool-learner` for unknown tools), then writes `generated/<customer-slug>/` — including a
**domain dossier per business domain** — never touching the agnostic base. Daily-ops entry points
live in `meta-prompts/`.

## AI as a consultancy (DDD)

`ai-platform-strategist` treats each business **domain as a client**: bounded context +
core/supporting/generic → **Langfuse** baseline → diagnose → cheapest-correct strategy
(`ai-cost-optimization`/`rag-design`/`fine-tuning-strategy`) → ROI. Method:
`domains/DDD-METHODOLOGY.md`. Pitch: `website/MANIFESTO.md`. No blind token burn.

## Tier mapping

Map capability tiers to Claude models locally — see `docs/capability-tiers.md`. The agent
files stay vendor-agnostic; `model: inherit` in the Claude adapters defers to your session model.

<!-- integralis:runtime -->
## Integralis platform runtime (recording is part of the work)

This squad was generated by **Integralis**, and half of what Integralis gives back is derived from calls the
agent chooses to make. Per-doc usage, flow-vs-reality divergence and the evidence-driven advisor all
come from `report_runs` + `report_usage` on the Integralis MCP server. Nothing observes you passively; when
nobody reports, nothing errors and the screens just stay empty.

- **Read `INTEGRALIS.md`** once per session. It is the platform contract: the four calls per prompt, the
  consent tiers, and the tools available.
- **Follow `skills/using-integralis/SKILL.md`** whenever the Integralis MCP server is connected.
- **No CLI is required.** On a hosted or ephemeral host (a browser IDE, a CI job, a rebuilt container)
  call `install_capture` and report through the MCP connection you already have.
- **Consent is the user's.** The metadata tier is on by default, prompt text is off by default. Never
  route around either.

Call `integralis_guide` for the current documentation; the server always ships a newer copy than the repo.
<!-- /integralis:runtime -->

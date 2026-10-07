# AGENTS.md — Full Engineering Agent Squad

Universal project context, read by AGENTS.md-aware tools (Codex, Cursor, Copilot, Gemini CLI,
Aider, Zed, Windsurf, Replit, Lovable, and more). Claude Code reads it via `@AGENTS.md` in
`CLAUDE.md`.

## Mandatory commit policy (every build request)

The repository owner requires a commit whenever an agent intentionally changes project files.
Apply this on every prompt that authorizes edits, including small fixes, documentation, skills,
instructions, memory pointers, configuration, and generated files that are intentionally versioned.

- Commit each coherent set of edits after its relevant checks. Do not finish a build turn with
  any of your intentional file edits uncommitted. If you edit again after committing, commit again.
- Work and commit on a feature branch, preferably in a separate worktree. Keep the principal
  `master` free of agent-authored local-only commits; delivery to `master` remains PR-only.
- Review the diff and explicitly stage the files you changed. Never include someone else's
  unfinished work, secrets, ignored local notes, caches, or untracked build output just to get a
  clean status. Do not force-add ignored files to satisfy this policy.
- Before responding, verify the commit exists and there are no remaining uncommitted edits
  attributable to this request. Report the commit and any genuine blocker; never claim a commit
  that did not happen. A commit is not permission to merge a PR, force-push, or publish.

Pull safety and the reason for keeping local work off `master`:
[`ai-memory/wiki/conventions.md#fresh-pull-verification`](ai-memory/wiki/conventions.md#fresh-pull-verification).

## Mandatory QA matrix policy (every change)

The QA test matrix (`qa/matriz/*.yaml`, executed by the admin QA module per deployed commit) must
describe the product as it is. Any change that adds, removes or alters something a person or an API
client can observe (page, route, endpoint, label, flow, email, price, limit, permission, error, admin
screen, module) updates the matrix **in the same PR**: add tests, edit existing ones (keep their ids), or
remove them (never reuse an id). Follow the local skill `qa-matrix-sync`. CI enforces the mechanical part
(`node scripts/qa-matrix.mjs --check` and `node scripts/qa-coverage.mjs`: every API controller and web page
covered, no test pointing at removed code); behavior changes inside covered routes are on you.

## What this is

A vendor-agnostic, role-based **multi-agent engineering squad**. A lead **orchestrator**
decomposes any request into tasks, scores complexity 1–5, assigns a **capability tier**
(never a vendor/model name), and routes to dedicated specialist agents. Definitions are
written once in the open **Agent Skills** (`SKILL.md`) standard and projected to per-vendor
adapters, so the same squad drops into any AI coding tool.

## The agents

Canonical definitions live in `agents/<role>/SKILL.md` (29 agents):

`orchestrator`, `planner`, `plan-checker`, `architect`, `context-manager`, `frontend-engineer`,
`backend-engineer`, `ai-engineer`, `ai-researcher`, `cybersec-engineer`, `infrastructure-engineer`,
`database-specialist`, `data-engineer`, `performance-engineer`, `devops-sre`,
`code-reviewer`, `tdd-guide`, `e2e-test-engineer`, `debugger`, `git-pr-specialist`,
`technical-writer`, `ux-designer`, `product-manager`, `business-analyst`, `business-strategist`,
`ai-platform-strategist`, `squad-optimizer`, `tool-learner`, `squad-customizer`.

Start with **orchestrator** for anything non-trivial. See `ORCHESTRATION.md` for the flow.
`squad-optimizer` is the self-learner: it improves the squad (agents/skills/hooks) from each
interaction's gaps, editing canonical sources then regenerating.

## Reliability mechanics (read before working)

- **Task file** (`workflow/README.md`, `templates/task-file.md`): the shared state every agent
  reads on entry and updates on exit (incl. `gates`, and `capture_session` so a `/compact` or a
  tool switch cannot regenerate the id and shred the recorded flow). Prevents forgetting/duplication. Portable.
- **Clarify before build**: on ambiguous/≥3 tasks, run `clarify-intent` (questions to a file,
  answered before coding; gated by an ambiguity score ≤0.2). When in doubt, ask — never assume.
- **Pre-execution plan gate**: ≥3 tasks go `planner` → `plan-checker` (coverage, `key_links`
  wiring, no scope-reduction, Nyquist <60s verify, valid waves/DAG) before any implementer starts.
- **Waves**: tasks carry `wave` + `depends_on`; same-wave (zero file overlap) run in parallel.
- **`templates/project-context.md`**: stack/conventions/tests + prohibited libraries — read first.
- **`LESSONS.md`** (learnings) + **`.ai-workflow/audit.md`** (append-only verbatim log).
- **File-based handoffs**: dispatch subagents with file paths, never pasted content (paste bloats the dispatcher's context all session). **Progress ledger** `.ai-workflow/progress.md` (one line per done step) survives `/compact` — read it on resume. (obra/superpowers.)
- **Definition of done**: never mark `DONE` unless every acceptance criterion **and** blocking
  gate is verified, **goal-backward** against `must_haves` (exists→substantive→wired→real-data —
  code presence alone never proves a behavior); else `BLOCKED`. Mid-task changes follow the change protocol.
- **Response gate (man-in-the-middle):** before finalizing ANY response to the user, run
  `RESPONSE-GATE.md` — truthfulness, answered-the-actual-ask, safety/guardrails, squad-consistency,
  quality. If a check fails, fix the answer first; never deliver a known-flawed response.

## Skills

On explicit `/sync`, load `skills/sync/SKILL.md` (also projected to `.agents/skills/sync/`).
It is a saved instruction workflow, not a scheduled job or native UI command registration.

Reusable procedures agents invoke on demand, in `skills/<name>/SKILL.md` (55). **Start every
task with `using-the-squad`** — the entry-point skill that discovers which of the rest apply
before you act — then, as the work demands: `using-integralis`, `clarify-intent`,
`convention-over-configuration`, `diagram-generator`,
`brainstorming`, `write-adr`, `clean-code`, `solid-dry`, `design-patterns`, `secure-regex`,
`behavior-driven-testing`, `conventional-commits`, `pr-description`, `code-review-checklist`,
`receiving-code-review`, `traceability-matrix`, `root-cause-analysis`, `root-cause-tracing`,
`debugging-method`, `defense-in-depth`, `condition-based-waiting`, `observability-standards`,
`threat-modeling`, `dependency-upgrade`, `performance-profiling`, `git-worktree-isolation`,
`ai-cost-optimization`, `rag-design`, `grounded-generation`, `fine-tuning-strategy`,
`knowledge-graph`, `subagent-driven-development`, `prompt-injection-defense`, `writing-skills`,
`simplest-solution`, `design-audit`, `video-understanding`, `notebooklm-research`, `obsidian-notes`,
`grilling`, `domain-modeling`, `decision-mapping`, `prototype`, `triage`,
`resolving-merge-conflicts`, `handoff`, `deep-modules`, `architecture-audit`, `to-spec`,
`to-tickets`, `repo-guardrails`, `human-step-wizard`, `apify`. Authoring standard:
`docs/how-to-write-skills.md` (SDO: descriptions = triggers only; TDD-for-skills; match-form-to-failure).
Agents reference the skills they use in their own `## Skills` section. Skills keep the *how*
out of agent prompts (lean + cache-friendly) and make output deterministic — same checklist
every run.

## Integration kits

For third-party vendors, `integrations/<vendor>/` holds a kit: `SKILL.md` (official SDK, auth,
patterns, webhooks), `mcp.json` (the vendor's **official** MCP, or none), `rules.md` (strict
MUST/MUST NOT gate). **Hard rule: official sources only — never a community/unofficial SDK or
MCP.** `backend-engineer`/`architect` follow the kit; `cybersec-engineer` audits against its
`rules.md`; `tool-learner` writes kits; `squad-customizer` copies the customer's into their squad.

## AI as a consultancy (DDD, domains as clients)

`ai-platform-strategist` runs AI platform engineering as an internal consultancy: each business
**domain is a client** (`domains/DDD-METHODOLOGY.md`). Onboard (bounded context +
core/supporting/generic) → instrument with **Langfuse** (`integrations/langfuse`) → diagnose
bottlenecks → pick the cheapest-correct play (`ai-cost-optimization` → `rag-design` →
`fine-tuning-strategy`) → measure ROI. Each domain gets a dossier
(`domains/domain-dossier.template.md`); `squad-customizer` generates one per customer domain.
**No blind token burn** — every play justified against the Langfuse baseline + DDD classification.

## How to use

1. Give the request to the `orchestrator` first (especially in plan mode).
2. It returns a task plan: each task gets a complexity score, a capability tier, and an owner agent.
3. Specialists execute (in parallel where coupling allows); `code-reviewer` and, on trust-boundary
   changes, `cybersec-engineer` gate the result.
4. Map each task's capability tier to one of your tool's models — see `docs/capability-tiers.md`.

## Conventions for any agent working here

- **Interface.** Interface changes follow `.claude/skills/` (`better-*`, consolidated by
  `better-interface`). Every operation a person starts shows a loading state that blocks new clicks
  until the response arrives. Source and commit of those skills: `.claude/skills/README.md`.
- **Capability tiers, not vendors.** Never hardcode a model/vendor name. Describe needed
  capabilities; the host environment maps tiers → models (`docs/capability-tiers.md`).
- **Guardrails first.** Every agent's first section is an identical security baseline
  (no secret exfiltration, prompt-injection defense, no weakening controls, confirm
  irreversible actions, truthful reporting). Keep it byte-identical — it's also a stable cache prefix.
- **Reuse over new code.** Read surrounding code and match its conventions before adding anything.
- **One shared source of truth.** Edit `agents/<role>/SKILL.md` or `skills/<name>/SKILL.md`; never
  hand-edit `adapters/**` (generated). Regenerate with `scripts/sync-adapters.sh`. The buyer adapters
  receive shared content only. `.agents/skills/` is a local projection: the sync updates selected
  shared skills but preserves local-only files and the declared `using-the-squad` and
  `prompt-injection-defense` overrides.
- **Read-only roles stay read-only.** orchestrator, planner, architect, code-reviewer,
  cybersec-engineer review/plan — they don't write product code.

## Token efficiency

`ai-engineer` owns prompt-caching and Headroom setup (`docs/headroom-setup.md`). Cache order
is `tools → system → messages`; the shared Guardrails block is a stable prefix.

## Layout

- `agents/` — canonical `SKILL.md` per role (edit here)
- `skills/` — canonical reusable procedures (edit here)
- `workflow/`, `templates/`, `LESSONS.md` — state contract, copy-in templates, accumulated lessons
- `knowledge/` — per-tool cards (docs+changelog, version-aware) incl. `ai-stack/`; read by `squad-customizer`, written by `tool-learner`
- `integrations/` — per-vendor kits (SKILL + official `mcp.json` + strict `rules.md`); official sources only, written by `tool-learner`
- `domains/` — DDD methodology + domain-dossier template (AI-as-consultancy, domains as clients)
- `website/` — `MANIFESTO.md` (the sales/landing narrative)
- `storefront/` — `BUILD-SPEC.md`: the commerce wizard (configure → Stripe pay → download ZIP → edit/AI-improve); generation runs through `squad-customizer`. `PERSISTENCE-AND-EDITOR.md` = Postgres schema + IDE-like editor (free manual edits, metered safety-gated AI-improve via `meta-prompts/improve-document`)
- `RESPONSE-GATE.md` — final self-review every response runs before reaching the user
- `wizard/` — UI-agnostic intake schema + dropdown options
- `meta-prompts/` — dual-mode `{{PLACEHOLDER}}` prompts for daily ops + generators
- `generated/` — per-customer squads (output of `squad-customizer`; agnostic base never touched)
- `adapters/` — generated per-vendor copies (Claude `agents/`+`skills/`+`hooks/`, Cursor `skills/`)
- `scripts/` — `sync-adapters.sh` (regenerate) + `eval.sh` (lint + routing surface)
- `docs/` — capability tiers, Headroom setup, sources
- `ORCHESTRATION.md` — flow, scoring, routing, diagram

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

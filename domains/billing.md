# Domain Dossier: Billing

> One per business domain — the domain's file as an **internal client** of the AI consultancy.
> Owned by `ai-platform-strategist`; copy this into `generated/<customer-slug>/domains/<domain>.md`.

```yaml
domain: billing
bounded_context: Billing
classification: core   # drives AI budget
owner_team: <team>
status: onboarding | instrumented | diagnosed | strategy-set | shipping | measured
last_reviewed: YYYY-MM-DD
```


> **Description (from buyer):** Handles invoices, subscriptions and payment reconciliation.

> **Ubiquitous language:** invoice, subscription

## 1. Strategic map (DDD)

- **What this domain does** (business purpose, in the domain's own words).
- **Bounded context boundaries** — where it starts/ends; adjacent contexts.
- **Ubiquitous language** — key terms + meanings (AI artifacts must use these, not generic words).
- **Classification rationale** — why core/supporting/generic → how much AI investment is justified.
- **Context map** — upstream/downstream contexts; any Anti-Corruption Layer needed.

## 2. AI usage today

- Current AI features/calls in this domain (or "none yet").
- Vendors/models in use; where they're called from; who owns them.
- Current prompts / RAG / fine-tunes, if any.

## 3. Langfuse baseline (the diagnostic superpower)

> Instrument this domain's AI usage with Langfuse first. Capture the baseline before changing anything.

| Metric | Baseline | Target |
|--------|----------|--------|
| Volume (calls/day) | | |
| Cost ($/day or /1k calls) | | |
| Latency (p50 / p95) | | |
| Quality (eval score / success rate) | | |
| Top failure modes | | |

- Langfuse project / dashboard link: <url>
- Traced surfaces: <which call sites are instrumented>

## 4. Diagnosis

- **Bottlenecks** — where cost/latency/quality hurt most (cite Langfuse traces).
- **Rooms for improvement** — concrete, ranked by impact × effort.
- **New-solution ideas** — AI features this domain could gain that it lacks today.

## 5. Precision strategy

> Cheapest correct tier first. Justify each choice against the baseline + classification.

| Problem | Chosen play | Why this tier (not cheaper) | Expected cost/quality delta |
|---------|-------------|------------------------------|------------------------------|
| … | prompt+caching / RAG / QLoRA fine-tune / bespoke | | |

- Cost-efficiency notes (caching, model tier, token budget) — see `ai-cost-optimization`.
- RAG design (if used) — see `rag-design`. Fine-tune plan (if used) — see `fine-tuning-strategy`.

## 6. Artifacts produced

- Prompts, RAG pipelines, eval sets, fine-tunes, or features delivered (with paths/links).

## 7. ROI (measured in Langfuse)

| | Before | After | Delta |
|---|--------|-------|-------|
| Cost | | | |
| Quality | | | |
| Latency | | | |

- Verdict: shipped / iterate / graduate to next bottleneck.

## 8. Next

- The next bottleneck for this domain, or hand-off to the next domain.

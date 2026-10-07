---
name: cursor-install
vendor: cursor
---

# Install for Cursor

Cursor IDE. Ships a native .cursor/skills projection of every agent and skill.

This squad contains **13 agents** and **11 skills**, all written in the
canonical Open Agent Skills format (`agents/<name>/SKILL.md`, `skills/<name>/SKILL.md`).

## Native Cursor adapter
This squad ships a native projection under `adapters/cursor/skills/`. Copy those folders into your project's `.cursor/skills/` directory.

## Where files go
1. Place the canonical `agents/` and `skills/` directories at your project root (or your tool's
   conventional location: `.cursor/skills/`).
2. Keep the `knowledge/`, `domains/`, and `integrations/` folders alongside them so cards and kits resolve.
3. Point Cursor at the squad and start with the `orchestrator` agent.

## Report every prompt (Intelligence capture)
Installing files records NOTHING. The project's Usage and Intelligence screens only show what gets
reported, so wire capture as part of the install, not later:

This squad's id is `ord_21988207d0a54e029ed5` — every capture tool below takes it as `squad_id`. (If this file
was moved between projects, resolve the current id with `list_squads` first.)

1. Connect to the Integralis MCP server and call
   `install_capture { squad_id: "ord_21988207d0a54e029ed5", agent: "cursor" }` — it returns every capture
   route ranked for this host. Also declare `can_run_shell: true` / `can_write_files: true` if
   (and only if) you really can: routes you do not declare capabilities for are not offered.
2. Take the route it marks `primary` and follow its steps. On a hosted or ephemeral host that is
   `agent_reported`: the agent itself calls `report_runs` at the start AND end of every user
   prompt, and `report_usage` for the squad docs it read in between — all with
   `squad_id: "ord_21988207d0a54e029ed5"`.
3. Prove it worked with `capture_status { squad_id: "ord_21988207d0a54e029ed5" }`: it must show runs for this
   project, with no `events_only`/`runs_only` sessions and no stale open runs. A gap there means
   prompts are being worked but not reported.

> Targeting is free — selecting more tools only changes which adapter files are included, never the price.

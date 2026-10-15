---
name: zaya-sankhya-development
description: Route minimal context for Zaya.IT Java and Sankhya analysis, implementation, build, review, profiles, Gradle, Jape, database, documents, dashboards, HTML5, JavaScript, Add-on Studio and deployment.
---

# Zaya Sankhya Development Router

Use the minimum sufficient context. This skill routes work; it is not an
encyclopedia and must not load the engineering library or data dictionary in full.

## 1. Establish the project

1. Report the current directory.
2. Resolve Git root, branch, sanitized origin and summarized status.
3. Stop when the resolved repository is not the requested project.
4. Find and read applicable `AGENTS.md` files from root to the target subtree.
5. Locate `docs/zaya/project-profile.md`.
6. Run `scripts/Get-ZayaProjectProfile.ps1 -ProjectPath <git-root>`.
7. If the returned profile is fresh, use it without remapping the repository.
8. For creation, build or change work, update a missing or stale generated
   profile with `-WriteProfile`; never overwrite a manually maintained file.

## 2. Classify the task

Assign one or more labels only when evidenced by the request or relevant files:

`JAVA`, `SQL`, `DATABASE`, `JAPE`, `DOCUMENT`, `DASHBOARD`, `REPORT`, `HTML5`,
`JAVASCRIPT`, `ADDON`, `BUILD`, `DEPLOY`, `DEBUG`, `REVIEW`.

Use [references/library-routing.md](references/library-routing.md) to select the
smallest reference set. Start with at most two library documents. A project
profile and dictionary query do not count as library documents.

## 3. Locate relevant implementation

Initial exploration budget:

- search before reading;
- three to five source files;
- at most two library references;
- dictionary lookups in one batch where possible.

These are directional limits, not hard failures. Expand only when evidence shows
that more context is necessary, and state the reason briefly.

Prefer exact symbols, entry points, FQCNs, task labels, tables and error messages.
Avoid recursive listing followed by broad reading.

## 4. Change and validate

1. Preserve existing JDK, build tool, Sankhya SDK, metadata IDs, public contracts
   and FQCNs unless an approved migration says otherwise.
2. Apply the smallest correct patch and avoid parallel refactoring.
3. Run the narrowest useful checks first, then build or tests proportional to risk.
4. For a relevant change, reviewer scope is the current Git diff only:
   “Revise exclusivamente o git diff atual. Consulte contexto adicional somente
   se necessário. Não procure melhorias fora das linhas afetadas.”

Simple tasks stay single-agent. Use an explorer only for bounded complex
investigation and a reviewer only for relevant diffs. Do not create many agents
by default.

## 5. Keep knowledge in the correct place

Project-specific facts belong in:

- `docs/zaya/project-profile.md`: generated technical facts and fingerprint;
- `docs/zaya/architecture.md`: client or project architecture;
- `docs/zaya/decisions.md`: confirmed decisions;
- `docs/zaya/known-issues.md`: confirmed issues and workarounds.

Corporate patterns belong in `zaya-engineering` only when sanitized, evidenced,
reusable across projects and reviewed. Never promote client rules, identifiers,
URLs, credentials, internal APIs or isolated incidents silently.

Finish concisely with Changed, Validation, Risks and Pending.

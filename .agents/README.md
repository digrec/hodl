# AI Agent Local Workspace Settings (`.agents/`)

This folder contains Model Context Protocol (MCP) server configurations and custom rules scoped to this repository.

## Folder Contents

### 1. `mcp_config.json` (Workspace MCP Symlink)
* **Canonical Source (`.mcp.json` at Repo Root):** Root [`.mcp.json`](../.mcp.json) is the single source of truth for shared project MCP servers (read natively by Claude Code, Cursor, and JetBrains Air Teams cloud).
* **Antigravity CLI Symlink:** `.agents/mcp_config.json` symlinks directly to `../.mcp.json`. To add a new shared project MCP server, edit `.mcp.json` at the repo root—`agy` CLI picks it up automatically without duplicating JSON.

### 2. `rules/` (Directory)
* **Shared Team Rules (Committed):**
  * `rules/compose-hot-reload.md`: Instructs agents on using the `compose-hot-reload` MCP server (`status`, `reload`, `get_semantic_tree`, `take_screenshot`, UI interactions) when editing Compose UI.
* **Personal Developer Rules (Locally Git-Excluded):**
  * `rules/intellij.md`: Instructs the agent to prefer IntelliJ IDEA MCP tools (`jetbrains_air_ide/execute_tool` or `intellij-idea/...`) when active. Keep personal/IDE-specific rules listed in `.git/info/exclude`.

---

## MCP Support Matrix & One-Time Host Setup

Not every AI host auto-discovers repo-scoped MCP configs yet. Use this matrix when setting up a dev machine:

| Client / Surface | Project Config Used | Behavior / Setup Required |
| :--- | :--- | :--- |
| **Claude Code / Cursor / Air Teams (Cloud)** | `.mcp.json` (root) | Works out of the box with `./kotlin --log-level=off compose-hot-reload-mcp-server`. |
| **Antigravity CLI (`agy`)** | `.agents/mcp_config.json` → `../.mcp.json` | Works out of the box on `git clone`. |
| **JetBrains Air (IntelliJ IDEA Plugin)** | `~/.jetbrains/mcp/mcp.json` *(Global)* | Does not yet read repo `.mcp.json` locally and spawns `stdio` commands outside the project root (`/`). Configure once via **Settings \| AI \| Air \| MCP Servers → Edit MCPs** (see below). |
| **Antigravity Desktop App** | `~/.gemini/config/mcp_config.json` *(Global)* | Does not auto-load workspace `.agents/mcp_config.json`. Configure once in global `mcp_config.json` with `"cwd"` (see below). |

### JetBrains Air (IntelliJ IDEA) — One-Time Setup
Open **Settings | AI | Air | MCP Servers → Edit MCPs** (`~/.jetbrains/mcp/mcp.json`), add the absolute path to `./kotlin` and `--project-dir`, click **Reload**, and enable the checkbox:

```json
{
  "mcpServers": {
    "compose-hot-reload": {
      "command": "/abs/path/to/Hodl/kotlin",
      "args": [
        "--log-level=off",
        "compose-hot-reload-mcp-server",
        "--project-dir=/abs/path/to/Hodl"
      ]
    }
  }
}
```

### Antigravity Desktop App — One-Time Setup
Add to `~/.gemini/config/mcp_config.json` with an explicit `"cwd"` pointing to your clone:

```json
{
  "mcpServers": {
    "compose-hot-reload": {
      "command": "./kotlin",
      "args": [
        "--log-level=off",
        "compose-hot-reload-mcp-server"
      ],
      "cwd": "/abs/path/to/Hodl"
    }
  }
}
```

### Optional: IntelliJ IDEA MCP (IDEA Users Only)
IntelliJ IDEA's built-in MCP server is optional and kept out of shared repo configs (`.mcp.json` / `.agents/mcp_config.json`):

* **JetBrains Air (IDEA Plugin):** Exposes IDE tools automatically via `jetbrains_air_ide` — no MCP entry is needed. Avoid IDEA's **MCP Server → Auto-Configure → Air** button, which writes `~/Library/Application Support/JetBrains/air/mcp.json` for the standalone Air app.
* **Antigravity Desktop App / `agy` CLI:** Enable **Settings | Tools | MCP Server** in IntelliJ IDEA, then register the Streamable HTTP endpoint globally in `~/.gemini/config/mcp_config.json`:
  ```shell
  agy mcp add --type http intellij-idea http://127.0.0.1:64342/stream
  ```
* **Personal Rule File:** If you add IDE-specific instructions in `.agents/rules/intellij.md`, list `/.agents/rules/intellij.md` in `.git/info/exclude` (see below).

---

## Where to Place Agent Guidelines: `AGENTS.md` vs. `.agents/rules/`

| Instruction Scope | Recommended Location | Why? |
| :--- | :--- | :--- |
| **Core Project & Build Workflows** | **`AGENTS.md`** *(Repo Root)* | Guaranteed to load across all coding agents (`agy`, Air, Claude, Cursor). |
| **MCP-Specific Shared Workflows** | **`.agents/rules/<mcp>.md`** *(Committed)* | Keeps tool-specific instructions modular (e.g., `rules/compose-hot-reload.md`). |
| **Private / IDE-Specific Rules** | **`.agents/rules/<name>.md`** *(Git-Excluded)* | Keeps personal IDE tools (e.g., `rules/intellij.md`) out of shared team commits. |

### Local Git Exclusion (`.git/info/exclude`)
Add personal rules to `.git/info/exclude` so they never appear in `git status`:

```text
/.agents/rules/intellij.md
```

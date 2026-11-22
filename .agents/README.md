# AI Agent Local Workspace Settings (`.agents/`)

This folder contains Model Context Protocol (MCP) server configurations and workspace-scoped Antigravity plugins for this repository.

## Folder Contents

### 1. `mcp_config.json` (Workspace MCP Symlink)
* **Canonical Source (`.mcp.json` at Repo Root):** Root [`.mcp.json`](../.mcp.json) is the single source of truth for shared project MCP servers (read natively by Claude Code, Cursor, and JetBrains Air Teams cloud).
* **Symlink:** `.agents/mcp_config.json` symlinks directly to `../.mcp.json` so changes to `.mcp.json` stay in sync without duplicating JSON.

### 2. `plugins/` (Workspace Antigravity Plugins)
Project-scoped Antigravity plugins are auto-discovered by both the **Antigravity Desktop App** and **`agy` CLI** without polluting `~/.gemini/config/mcp_config.json` or other repositories:
* **`plugins/compose-hot-reload/`**: Bundles `compose-hot-reload` MCP (`./kotlin --log-level=off compose-hot-reload-mcp-server`) and its UI inspection guidelines (`rules/AGENTS.md`).
* **`plugins/intellij-idea/`**: Bundles `intellij-idea` MCP (`http://127.0.0.1:64342/stream`) and IDE navigation/diagnostics guidelines (`rules/AGENTS.md`).

---

## MCP Support Matrix & One-Time Host Setup

Not every AI host auto-discovers repo-scoped MCP configs or `.agents/plugins/` yet. Use this matrix when setting up a dev machine:

| Client / Surface | Project Config Used | Behavior / Setup Required |
| :--- | :--- | :--- |
| **Claude Code / Cursor / Air Teams (Cloud)** | `.mcp.json` (root) | Works out of the box with `./kotlin --log-level=off compose-hot-reload-mcp-server`. |
| **Antigravity Desktop App & `agy` CLI** | `.agents/plugins/*` | Works out of the box on `git clone` (no global `~/.gemini/config/mcp_config.json` entry needed). |
| **JetBrains Air (IntelliJ IDEA Plugin)** | `~/.jetbrains/mcp/mcp.json` *(Global)* | Does not yet read repo `.mcp.json` locally and spawns `stdio` commands outside the project root (`/`). Configure once via **Settings \| AI \| Air \| MCP Servers → Edit MCPs** (see below). |

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

### Optional: IntelliJ IDEA MCP (IDEA Users Only)
* **JetBrains Air (IDEA Plugin):** Exposes IDE tools automatically via `jetbrains_air_ide` — no MCP entry is needed. Avoid IDEA's **MCP Server → Auto-Configure → Air** button, which writes `~/Library/Application Support/JetBrains/air/mcp.json` for the standalone Air app.
* **Antigravity Desktop App / `agy` CLI:** Enable **Settings | Tools | MCP Server** in IntelliJ IDEA; the workspace plugin `.agents/plugins/intellij-idea/` connects to `http://127.0.0.1:64342/stream` automatically when this workspace is open.

---

## Where to Place Agent Guidelines: `AGENTS.md` vs. Plugins vs. `.agents/rules/`

| Instruction Scope | Recommended Location | Why? |
| :--- | :--- | :--- |
| **Core Project & Build Workflows** | **`AGENTS.md`** *(Repo Root)* | Guaranteed to load across all coding agents (`agy`, Air, Claude, Cursor). |
| **Plugin-Scoped MCP Workflows** | **`.agents/plugins/<name>/rules/AGENTS.md`** | Keeps MCP-specific rules bundled with their server and active only when the plugin is enabled. |
| **Private / Local-Only Rules** | **`.agents/rules/<name>.md`** *(Git-Excluded)* | Keeps personal standalone rules out of shared team commits via `.git/info/exclude`. |

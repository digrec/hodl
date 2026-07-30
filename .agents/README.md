# AI Agent Local Workspace Settings (`.agents/`)

This folder contains Model Context Protocol (MCP) server definitions and custom rules scoped strictly to this local project workspace.

## Folder Contents

### 1. `mcp_config.json`
* **Purpose:** Registers project-scoped Model Context Protocol (MCP) servers used by Antigravity during coding sessions.
* **Current Configured MCPs:**
  * `compose-hot-reload` (stdio transport): Portable shell invocation (`"command": "sh"`, `"args": ["./gradlew", ...]`) for Compose Hot Reload tools.
* **Git Status:** **Committed** (tracked in git for team sharing).
* **Why it is tracked:** Uses portable shell execution (`sh` + `./gradlew`) so any contributor cloning the repository gets the MCP server automatically without hardcoded local machine paths.

### 2. `rules/` (Directory)
* **Purpose:** Contains project-scoped rules and behavioral guidelines that the agent automatically reads and follows when editing files in this workspace.
* **Rules List:**
  * `rules/intellij.md`: Instructs the agent to prefer IntelliJ IDEA tools (like opening files in the editor and retrieving active editor paths) over plain terminal/file commands when working in this workspace.
* **Git Status:** Global team rules in this folder are generally committed, but **personal/private developer rules should be locally git-excluded**.

---

## Public vs. Private MCP Configurations

| Config File | Scope | Git Status | Description / Best For |
| :--- | :--- | :--- | :--- |
| **`.mcp.json`** (Repo Root) | **Shared (Official JetBrains Spec)** | **Committed** | Official JetBrains specification file (`"command": "./gradlew"`) for shared project MCP tools (Claude Desktop, Cursor, VS Code). |
| **`.agents/mcp_config.json`** | **Antigravity Project Config** | **Committed** | Local workspace reference for Antigravity rules and project settings. |
| **`~/.gemini/config/mcp_config.json`** | **Personal Developer Machine** | **Local (On your Mac)** | Active MCP launcher configuration read by Antigravity at startup. Contains local developer tools (`intellij-idea`) and Antigravity stdio path bindings. |

---

## Where to Place Agent Guidelines: `AGENTS.md` vs. `.agents/rules/`

| Instruction Scope | Recommended Location | Why? |
| :--- | :--- | :--- |
| **Shared Team Workflows** | **`AGENTS.md`** *(Project Handbook)* | For workflows available to all developers (e.g. `compose-hot-reload` UI verification, build commands, formatting). Guarantees every session follows the same standard. |
| **Private / Personal Tools** | **`.agents/rules/<name>.md`** *(Git-Excluded)* | For developer-specific tools (e.g. `intellij-idea` IDE navigation rules). Prevents non-IntelliJ team members from receiving instructions for tools not present on their machines. |

---

## Local Git Exclusion (`.git/info/exclude`)

To prevent your personal workspace-scoped rules (like `rules/intellij.md`) from showing up as untracked files in `git status` or being accidentally committed to the team repository, add them to your local git exclude file:

File path: `.git/info/exclude`

Add this line:
```text
.agents/rules/intellij.md
```

*Note: `.git/info/exclude` acts exactly like `.gitignore` but is stored privately in your local clone and never committed.*

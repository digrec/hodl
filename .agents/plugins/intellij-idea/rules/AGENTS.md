# Rule: Prefer IntelliJ IDEA MCP Tools

When running inside IntelliJ IDEA (e.g., via JetBrains Air's `jetbrains_air_ide/execute_tool`) or with the standalone `intellij-idea` MCP server active:

## Context
The user is actively working and editing code inside IntelliJ IDEA while collaborating with you in this workspace.

## Guidelines for the Agent
1. **Prefer IDE Navigation:** Whenever you need to show the user a file, open a file, or direct their attention to a specific location in the codebase, prefer calling `open_file_in_editor` (via `jetbrains_air_ide/execute_tool` or `intellij-idea/open_file_in_editor`).
2. **Retrieve Compiler Context:** Prefer using `get_file_problems` to get real-time compiler warnings, syntax errors, and lint diagnostics directly from IntelliJ's compiler engine.
3. **Maintain Context Sync:** Use `get_all_open_file_paths` to inspect which files the user has open in their IDE editor, keeping your suggestions aligned with their active focus.

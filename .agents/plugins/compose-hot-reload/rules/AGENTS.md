# Rule: Leverage Compose Hot Reload MCP for Compose UI

This workspace configures the `compose-hot-reload` MCP server for interactive Compose Multiplatform development.

## Context
When working on Compose UI (in `shared/src/` and `desktopApp/src/`), the desktop app can be run with Compose Hot Reload enabled via `./kotlin run -m desktopApp`. The `compose-hot-reload` MCP server provides live UI inspection, hot reloading, semantic tree analysis, and screenshot capture.

## Guidelines for the Agent
1. **Check Connection Status**: When modifying UI code, check `compose-hot-reload/status` to see if the desktop application is running and connected. If not running, proceed with standard `./kotlin build` and `./kotlin test` verification.
2. **Trigger & Verify Reloads**: After making Compose UI changes, use `compose-hot-reload/reload` or `compose-hot-reload/await_reload` and inspect `compose-hot-reload/get_ui_error` to verify that the UI hot reloaded cleanly without runtime crashes.
3. **Inspect Live UI**: Use `compose-hot-reload/get_semantic_tree` and `compose-hot-reload/take_screenshot` to inspect layout bounds, text elements, and visual state without guessing.
4. **Interactive Verification**: Use interaction tools (`click`, `scroll`, `type_text`) to test state changes, navigation transitions, and user flows on the running desktop app.

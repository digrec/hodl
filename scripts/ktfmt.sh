#!/usr/bin/env bash
# ==============================================================================
# Script: ktfmt.sh
#
# Purpose:
#   Deterministic Kotlin code formatter using ktfmt with KotlinLang style (4-space).
#   Replaces the legacy Gradle ktfmt plugin (com.ncorti.ktfmt.gradle).
#
# Usage:
#   ./scripts/ktfmt.sh          # Formats all Kotlin files in-place
#   ./scripts/ktfmt.sh --dry-run # Checks files without modifying (returns non-zero if diffs exist)
# ==============================================================================
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
KTFMT_VERSION="0.54"
CACHE_DIR="${ROOT_DIR}/.kotlin/tooling"
JAR_PATH="${CACHE_DIR}/ktfmt-${KTFMT_VERSION}.jar"

# Collect all Kotlin source files, excluding build/, .kotlin/, .git/, and .idea/
KOTLIN_FILES=()
while IFS= read -r -d '' file; do
    KOTLIN_FILES+=("$file")
done < <(find "${ROOT_DIR}" \
    -type d \( -name "build" -o -name ".kotlin" -o -name ".git" -o -name ".idea" \) -prune -o \
    -type f -name "*.kt" -print0)

if [[ ${#KOTLIN_FILES[@]} -eq 0 ]]; then
    echo "No Kotlin files found."
    exit 0
fi

EXTRA_ARGS=()
for arg in "$@"; do
    EXTRA_ARGS+=("$arg")
    if [[ "$arg" == "--dry-run" || "$arg" == "-n" ]]; then
        EXTRA_ARGS+=("--set-exit-if-changed")
    fi
done

# 1. Prefer native ktfmt CLI if available (e.g. 'brew install ktfmt')
if command -v ktfmt &> /dev/null; then
    ktfmt --kotlinlang-style ${EXTRA_ARGS[@]+"${EXTRA_ARGS[@]}"} "${KOTLIN_FILES[@]}"
    exit 0
fi

# 2. Fall back to standalone JAR executed via Java 21
if [[ ! -f "$JAR_PATH" ]]; then
    mkdir -p "$CACHE_DIR"
    echo "Downloading ktfmt ${KTFMT_VERSION} runner..."
    TMP_JAR="${JAR_PATH}.tmp.$$"
    trap 'rm -f "$TMP_JAR"' EXIT
    curl -fsSL "https://repo1.maven.org/maven2/com/facebook/ktfmt/${KTFMT_VERSION}/ktfmt-${KTFMT_VERSION}-jar-with-dependencies.jar" -o "$TMP_JAR"
    mv -f "$TMP_JAR" "$JAR_PATH"
    trap - EXIT
fi

java -jar "$JAR_PATH" --kotlinlang-style ${EXTRA_ARGS[@]+"${EXTRA_ARGS[@]}"} "${KOTLIN_FILES[@]}"

#!/usr/bin/env bash
# ==============================================================================
# Script: sync-versions.sh
#
# Purpose:
#   Synchronizes the application version name (semver) and calculates the numeric
#   version code across all application targets in the Hodl project.
#   Replaces the legacy Gradle buildSrc tasks (VersionCode.kt & UpdateIosVersion.kt).
#
# Version Code Formula:
#   versionCode = (major * 10000) + (minor * 100) + patch
#   Example: "1.0.0" -> 10000, "1.2.3" -> 10203
#
# Files Synchronized:
#   1. libs.versions.toml              -> versionName
#   2. androidApp/module.yaml          -> versionCode, versionName
#   3. iosApp/src/Info.plist           -> CFBundleShortVersionString, CFBundleVersion
#
# Usage:
#   ./scripts/sync-versions.sh <version>
#   Example:
#     ./scripts/sync-versions.sh 1.0.0
#     ./scripts/sync-versions.sh 2.1.4
# ==============================================================================
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# Validates and extracts version name from arguments or git/defaults
VERSION_NAME="${1:-1.0.0}"

if [[ ! "$VERSION_NAME" =~ ^([0-9]+)\.([0-9]+)\.([0-9]+)$ ]]; then
    echo "Error: Version name '$VERSION_NAME' must be in format X.Y.Z" >&2
    exit 1
fi

MAJOR="${BASH_REMATCH[1]}"
MINOR="${BASH_REMATCH[2]}"
PATCH="${BASH_REMATCH[3]}"

# Formula: major * 10000 + minor * 100 + patch (force base-10 for segments like 08/09)
VERSION_CODE=$(( 10#$MAJOR * 10000 + 10#$MINOR * 100 + 10#$PATCH ))

echo "Synchronizing project versions:"
echo "  • Version Name: $VERSION_NAME"
echo "  • Version Code: $VERSION_CODE"

sed_i() {
    local pattern="$1"
    local file="$2"
    if [[ "$OSTYPE" == "darwin"* ]]; then
        sed -i '' -E "$pattern" "$file"
    else
        sed -i -E "$pattern" "$file"
    fi
}

LIBS_TOML="${ROOT_DIR}/libs.versions.toml"
ANDROID_MODULE="${ROOT_DIR}/androidApp/module.yaml"
IOS_PLIST="${ROOT_DIR}/iosApp/src/Info.plist"

# 1. Update libs.versions.toml
if [[ -f "$LIBS_TOML" ]]; then
    sed_i "s/versionName = \"[^\"]*\"/versionName = \"${VERSION_NAME}\"/" "$LIBS_TOML"
    echo "✓ Updated libs.versions.toml"
fi

# 2. Update androidApp/module.yaml
if [[ -f "$ANDROID_MODULE" ]]; then
    sed_i "s/versionCode: [0-9]+/versionCode: ${VERSION_CODE}/" "$ANDROID_MODULE"
    sed_i "s/versionName: \".*\"/versionName: \"${VERSION_NAME}\"/" "$ANDROID_MODULE"
    echo "✓ Updated androidApp/module.yaml"
fi

# 3. Update iosApp/src/Info.plist
if [[ -f "$IOS_PLIST" ]]; then
    python3 - "$IOS_PLIST" "$VERSION_NAME" "$VERSION_CODE" <<'PY'
import plistlib
import sys

plist_path, version_name, version_code = sys.argv[1], sys.argv[2], sys.argv[3]
with open(plist_path, 'rb') as f:
    pl = plistlib.load(f)
pl['CFBundleShortVersionString'] = version_name
pl['CFBundleVersion'] = version_code
with open(plist_path, 'wb') as f:
    plistlib.dump(pl, f, fmt=plistlib.FMT_XML)
PY
    echo "✓ Updated iosApp/src/Info.plist"
fi


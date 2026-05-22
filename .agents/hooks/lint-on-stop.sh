#!/bin/bash
# Auto-lint hook: runs ktlint (format) + detekt (check) on changed .kt files
# Triggered on Claude Stop event. Exits silently if no .kt files changed.

KTLINT_VERSION="1.5.0"
DETEKT_VERSION="1.23.7"
BIN_DIR="$HOME/.local/bin"
LIB_DIR="$HOME/.local/lib"
PROJECT_DIR="$(pwd)"

# Auto-install ktlint standalone binary
if [ ! -x "$BIN_DIR/ktlint" ]; then
  echo "[lint] Installing ktlint $KTLINT_VERSION..."
  mkdir -p "$BIN_DIR"
  curl -sSLo "$BIN_DIR/ktlint" \
    "https://github.com/pinterest/ktlint/releases/download/$KTLINT_VERSION/ktlint"
  chmod +x "$BIN_DIR/ktlint"
fi

# Auto-install detekt CLI jar
if [ ! -f "$LIB_DIR/detekt-cli.jar" ]; then
  echo "[lint] Installing detekt CLI $DETEKT_VERSION..."
  mkdir -p "$LIB_DIR"
  curl -sSLo "$LIB_DIR/detekt-cli.jar" \
    "https://github.com/detekt/detekt/releases/download/v${DETEKT_VERSION}/detekt-cli-${DETEKT_VERSION}-all.jar"
fi

# Find changed .kt files since last commit
CHANGED=$(git -C "$PROJECT_DIR" diff --name-only HEAD 2>/dev/null | grep '\.kt$' || true)
[ -z "$CHANGED" ] && exit 0

# Resolve to existing absolute paths
FILES=()
for f in $CHANGED; do
  abs="$PROJECT_DIR/$f"
  [ -f "$abs" ] && FILES+=("$abs")
done
[ ${#FILES[@]} -eq 0 ] && exit 0

echo "[lint] ${#FILES[@]} file(s) changed — running ktlint + detekt..."

# ktlint: auto-format in place
"$BIN_DIR/ktlint" --format "${FILES[@]}" 2>&1

# detekt: check only (reports issues for Claude to fix)
java -jar "$LIB_DIR/detekt-cli.jar" \
  --input "$(IFS=,; echo "${FILES[*]}")" \
  --config "$PROJECT_DIR/config/detekt/detekt.yml" \
  --build-upon-default-config 2>&1

exit 0

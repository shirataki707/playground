#!/bin/bash
# PostToolUse hook: triggered after every Bash tool use.
# If git commit was just executed, checks whether structural changes
# were made that might require README.md / AGENTS.md to be updated.

INPUT=$(cat)

# Only proceed if this was a git commit command
if ! echo "$INPUT" | grep -q '"git commit'; then
    exit 0
fi

PROJECT_DIR="$(git rev-parse --show-toplevel 2>/dev/null)"
[ -z "$PROJECT_DIR" ] && exit 0

# Check which files changed in the last commit
CHANGED=$(git -C "$PROJECT_DIR" diff HEAD~1 --name-only 2>/dev/null)
[ -z "$CHANGED" ] && exit 0

REASONS=()

if echo "$CHANGED" | grep -qE "^(core|feature)/"; then
    REASONS+=("モジュール構成の変更 (core/ または feature/)")
fi

if echo "$CHANGED" | grep -qE "^build-logic/"; then
    REASONS+=("Convention Plugin の変更 (build-logic/)")
fi

if echo "$CHANGED" | grep -qE "^\.agents/skills/"; then
    REASONS+=("Agent Skills の変更 (.agents/skills/)")
fi

if echo "$CHANGED" | grep -qE "^(settings|build)\.gradle\.kts$"; then
    REASONS+=("ルートビルド設定の変更")
fi

if [ ${#REASONS[@]} -gt 0 ]; then
    echo "[docs-check] 以下の変更を検知しました。README.md・AGENTS.md の更新が必要か確認してください:"
    for reason in "${REASONS[@]}"; do
        echo "  - $reason"
    done
fi

exit 0

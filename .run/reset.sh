#!/usr/bin/env bash
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

git restore --staged .
git checkout .
${SCRIPT_DIR}/clean_worktree.sh
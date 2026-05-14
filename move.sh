#!/usr/bin/env bash

set -euo pipefail

dry_run=false
build_no=""

# -------------------------------
# Parse arguments
# -------------------------------
while [[ $# -gt 0 ]]; do
  case "$1" in
    -b)
      build_no="$2"
      shift 2
      ;;
    --dry-run)
      dry_run=true
      shift
      ;;
    *)
      echo "Usage: $0 [-b <number>] [--dry-run]"
      exit 1
      ;;
  esac
done

# -------------------------------
# Load config
# -------------------------------
if [[ ! -f ".version" ]]; then
  echo "Error: .version file not found"
  exit 1
fi

# shellcheck disable=SC1091
source .version

# -------------------------------
# Validate required fields
# -------------------------------
required_vars=(mod_name mod_version type build_dir move_to)

for var in "${required_vars[@]}"; do
  if [[ -z "${!var:-}" ]]; then
    echo "Error: Missing required variable: $var"
    exit 1
  fi
done

if [[ "$type" != "alpha" && "$type" != "beta" && "$type" != "release" ]]; then
  echo "Error: type must be alpha, beta, or release"
  exit 1
fi

# Defaults (optional in .version)
release_pattern="${release_pattern:-${mod_name}-${mod_version}.jar}"
prerelease_pattern="${prerelease_pattern:-${mod_name}-${mod_version}-${type}%n.jar}"
copy_to=("${copy_to[@]:-}")

# -------------------------------
# Helper: run command safely
# -------------------------------
run() {
  if [[ "$dry_run" == true ]]; then
    echo "[DRY RUN] $*"
  else
    "$@"
  fi
}

# -------------------------------
# Detect build number (alpha/beta)
# -------------------------------
detect_build_number() {
  local latest=0

  shopt -s nullglob
  for file in "${build_dir}"/*.jar; do
    if [[ "$file" =~ ${type}([0-9]+)\.jar$ ]]; then
      num="${BASH_REMATCH[1]}"
      (( num > latest )) && latest=$num
    fi
  done
  shopt -u nullglob

  if [[ $latest -eq 0 ]]; then
    echo "Error: No ${type} builds found"
    exit 1
  fi

  echo "$latest"
}

# -------------------------------
# Build filename
# -------------------------------
if [[ "$type" == "release" ]]; then
  filename="$release_pattern"
else
  if [[ -z "$build_no" ]]; then
    echo "Auto-detecting latest $type..."
    build_no=$(detect_build_number)
    echo "Detected $type: $build_no"
  fi

  filename="${prerelease_pattern//%n/$build_no}"
fi

# -------------------------------
# Paths
# -------------------------------
build_path="${build_dir}/${filename}"
move_path="${move_to}/${filename}"

if [[ ! -f "$build_path" ]]; then
  echo "Error: Build file not found: $build_path"
  exit 1
fi

# -------------------------------
# Execute
# -------------------------------
echo "Processing: $filename"

run mkdir -p "$move_to"
run mv "$build_path" "$move_to/"

# Copy to multiple locations
for dest in "${copy_to[@]}"; do
  run mkdir -p "$dest"
  run cp "$move_path" "$dest/"
done

echo "Done ✅"
#!/bin/sh
# Claude Code status line: context usage, 5-hour limit, weekly limit

make_bar() {
  pct="$1"
  width=20
  filled=$(( pct * width / 100 ))
  empty=$(( width - filled ))
  bar=""
  i=0
  while [ $i -lt $filled ]; do
    bar="${bar}█"
    i=$(( i + 1 ))
  done
  i=0
  while [ $i -lt $empty ]; do
    bar="${bar}░"
    i=$(( i + 1 ))
  done
  printf "%s" "$bar"
}

input=$(cat)

eval "$(echo "$input" | python3 -c "
import sys, json
from datetime import datetime
d = json.load(sys.stdin)
rl = d.get('rate_limits', {})

ctx = d.get('context_window', {}).get('used_percentage', '')
print(f'ctx={ctx}')

fh = rl.get('five_hour', {})
five = fh.get('used_percentage', '')
five_ts = fh.get('resets_at', '')
five_until = datetime.fromtimestamp(five_ts).strftime('~%H:%M') if five_ts else ''
print(f'five={five}')
print(f'five_until={five_until}')

sd = rl.get('seven_day', {})
week = sd.get('used_percentage', '')
week_ts = sd.get('resets_at', '')
week_until = datetime.fromtimestamp(week_ts).strftime('~%-m/%-d') if week_ts else ''
print(f'week={week}')
print(f'week_until={week_until}')
" 2>/dev/null)"

parts=""

if [ -n "$ctx" ]; then
  ctx_int=$(printf "%.0f" "$ctx")
  bar=$(make_bar "$ctx_int")
  parts="Ctx: [${bar}] ${ctx_int}%"
fi

if [ -n "$five" ]; then
  five_int=$(printf "%.0f" "$five")
  bar=$(make_bar "$five_int")
  entry="5h: [${bar}] ${five_int}%${five_until:+ $five_until}"
  if [ -n "$parts" ]; then parts="${parts}  ${entry}"; else parts="$entry"; fi
fi

if [ -n "$week" ]; then
  week_int=$(printf "%.0f" "$week")
  bar=$(make_bar "$week_int")
  entry="7d: [${bar}] ${week_int}%${week_until:+ $week_until}"
  if [ -n "$parts" ]; then parts="${parts}  ${entry}"; else parts="$entry"; fi
fi

printf "%s" "$parts"
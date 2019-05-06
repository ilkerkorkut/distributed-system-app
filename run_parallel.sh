#!/bin/bash
for task in "$@"; do {
  $task &
} done
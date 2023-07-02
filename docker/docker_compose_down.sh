#!/usr/bin/env bash

docker-compose -f ../docker-compose-all.yml down

osascript -e 'tell application "iTerm"
  close windows without asking
end tell'



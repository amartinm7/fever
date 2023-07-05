#!/bin/bash

currentPath="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"  # Get the current script's directory
cmdStartDocker="docker-compose -f ../docker-compose-all.yml up -d"

# Function to open a new tab and execute a command within iTerm
openNewTab() {
  local command="$1"

  osascript -e "on run argv
    tell application \"iTerm\"
      delay 2
      if (count of windows) is 0 then
        set newWindow to (create window with default profile)
      else
        set newWindow to current window
        tell newWindow
          delay 3
          create tab with default profile
        end tell
      end if
      tell current session of newWindow
        write text \"cd $currentPath; $command\"
      end tell
    end tell
  end run" "$currentPath"
}

# Open new tabs and execute commands
openNewTab "$cmdStartDocker; docker-compose -f ../docker-compose-all.yml logs -f database"
openNewTab "$cmdStartDocker; docker-compose -f ../docker-compose-all.yml logs -f wiremock"
openNewTab "$cmdStartDocker; docker-compose -f ../docker-compose-all.yml logs -f fever"

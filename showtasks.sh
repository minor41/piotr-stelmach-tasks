#!/usr/bin/env bash

stop_browser()
{
    echo "There was Error during runcrud"
}

open_browser_with_getTask()
{
    open -a "Google Chrome" "http://localhost:8080/crud/v1/task/getTasks"
}

end()
{
    echo "enjoy my Task List!"
}

if ./runcrud.sh; then
    open_browser_with_getTask
    end
else
    stop_browser
fi
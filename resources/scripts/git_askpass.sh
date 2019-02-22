#!/bin/sh
case "$1" in
Username*) printf "%s\n" "$GIT_USER" ;;
Password*) printf "%s\n" "$GIT_PASSWORD" ;;
esac

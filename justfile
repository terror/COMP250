default:
  just --list

alias f := fmt

all: forbid fmt

forbid:
  ./bin/forbid

fmt:
  fd .java | xargs google-java-format --replace

dev-deps:
  brew install gradle google-java-format fd ripgrep

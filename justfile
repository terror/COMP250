default:
  just --list

alias f := fmt

fmt:
  fd .java | xargs google-java-format --replace

dev-deps:
  brew install gradle google-java-format fd

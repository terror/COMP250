default:
  just --list

fmt:
	fd .java | xargs google-java-format --replace

dev-deps:
	brew install gradle google-java-format fd

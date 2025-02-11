PROTOC = ~/protoc/bin/protoc
PROTO_FILES = src/main/proto/proto_messages.proto
JAVA_OUT_DIR = target/generated-classes

all: generate

generate:
	$(PROTOC) --java_out=$(JAVA_OUT_DIR) $(PROTO_FILES)
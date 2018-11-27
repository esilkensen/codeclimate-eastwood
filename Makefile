.PHONY: image

IMAGE_NAME ?= codeclimate/codeclimate-eastwood

image:
	docker build --rm -t $(IMAGE_NAME) .

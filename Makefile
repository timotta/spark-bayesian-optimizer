.PHONY: install test build compile deploy deploy-qa sonar clean

SBT_CLIENT := $(shell which sbt)
ROOT_PATH := $(shell pwd)
AZKABAN_CLIENT := $(shell which azkaban)
PIP_CLIENT := $(shell which pip)
PYTHON := $(shell which python)


install:
	@$(PIP_CLIENT) install -r flow/requirements.txt


test:
	@$(SBT_CLIENT) clean test

build:
	@$(SBT_CLIENT) assembly

compile:
	@$(SBT_CLIENT) clean compile

deploy-qa:
	@$(SBT_CLIENT) deployQA

	@$(PYTHON) flow/anonym-inference-train.py qa
	@$(AZKABAN_CLIENT) login --host https://azkaban.qa.globoi.com
	@$(AZKABAN_CLIENT) upload flow/anonym-inference-train

deploy: deploy-release azkaban-release

deploy-release: build
	@env GOOGLE_APPLICATION_CREDENTIALS=$(ROOT_PATH)/flow/pes_anonym_inference_train/anonym-inference-2-e6752c4810d8.json python upload_jar.py

azkaban-release: azkaban-login azkaban-upload azkaban-schedule azkaban-clean

azkaban-login:
	@$(AZKABAN_CLIENT) login --host https://azkaban.globoi.com

azkaban-build: azkaban-clean
	@$(PYTHON) flow/pes_anonym_inference_train.py prod

azkaban-upload: azkaban-build
	@$(AZKABAN_CLIENT) upload flow/pes_anonym_inference_train

azkaban-schedule: azkaban-build
	@$(AZKABAN_CLIENT) schedule pes_anonym_inference_train download_trained_models '0 0 23 ? * SAT *' # Quartz cron

sonar:
	@$(SBT_CLIENT) clean coverage test coverageReport scapegoat sonarScan

azkaban-clean:
	@rm -f flow/pes_anonym_inference_train/anonym-inference-train.zip
	@rm -f flow/pes_anonym_inference_train/*.job
	@rm -f flow/pes_anonym_inference_train/*.properties
	@rm -f flow/pes_anonym_inference_train/*.sql

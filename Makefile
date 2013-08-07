#!/usr/bin/make -f

# MACHINE examples: et4x00 et5x00 et6x00 et9x00 dm500hd dm800se dm7020hd dm8000 xp1000
MACHINE ?= ${subst /,,${subst build-,,${firstword ${dir ${wildcard build-*/}}}}}

ifeq "$(MACHINE)" ""
	MACHINE=tmtwinoe
endif

# Adjust according to the number CPU cores to use for parallel build.
# Default: Number of processors in /proc/cpuinfo, if present, or 1.
NR_CPU := $(shell [ -f /proc/cpuinfo ] && grep -c '^processor\s*:' /proc/cpuinfo || echo 1)
BB_NUMBER_THREADS ?= $(NR_CPU)
PARALLEL_MAKE ?= -j $(NR_CPU)

# ci model different driver.
# date format : ex) 20130807
# md5sum format : ex) src_uri
DATE = "20130807"
DRV_MD5SUM = "fa1ee1f180466da805d8ea053851c19e"
DRV_SHA256SUM = "7aeda68dac50145a2b16ca06914f9e20106ebd002302af2efb9cd216c3838333"

CI_DATE = "20130807"
CI_DRV_MD5SUM = "25d03a86fd60e831c63078aa071fb432"
CI_DRV_SHA256SUM = "75634a204b66a1c5e5b9f46cf8a244e0703eef9ead2594f312716bd7c330c7a4"

XSUM ?= md5sum

BUILD_DIR = $(CURDIR)/build-$(MACHINE)
TOPDIR = $(BUILD_DIR)
DL_DIR = $(CURDIR)/sources
SSTATE_DIR = $(TOPDIR)/sstate-cache
TMPDIR = $(TOPDIR)/tmp
DEPDIR = $(TOPDIR)/.deps

BBLAYERS ?= \
	$(CURDIR)/meta-openembedded/meta-oe \
	$(CURDIR)/openembedded-core/meta \
	$(CURDIR)/meta-openpli \
	$(CURDIR)/meta-local

CONFFILES = \
	$(TOPDIR)/env.source \
	$(TOPDIR)/conf/openpli.conf \
	$(TOPDIR)/conf/bblayers.conf \
	$(TOPDIR)/conf/local.conf \
	$(TOPDIR)/conf/site.conf

CONFDEPS = \
	$(DEPDIR)/.env.source.$(BITBAKE_ENV_HASH) \
	$(DEPDIR)/.openpli.conf.$(OPENPLI_CONF_HASH) \
	$(DEPDIR)/.bblayers.conf.$(MACHINE).$(BBLAYERS_CONF_HASH) \
	$(DEPDIR)/.local.conf.$(MACHINE).$(LOCAL_CONF_HASH)

GIT ?= git
GIT_REMOTE := $(shell $(GIT) remote)
GIT_USER_NAME := $(shell $(GIT) config user.name)
GIT_USER_EMAIL := $(shell $(GIT) config user.email)

hash = $(shell echo $(1) | $(XSUM) | awk '{print $$1}')

.DEFAULT_GOAL := all
all: init
	@echo
	@echo "Openembedded for the OpenPLi 3.0 environment has been initialized"
	@echo "properly. Now you can start building your image, by doing either:"
	@echo
	@echo " MACHINE=$(MACHINE) make image"
	@echo "	or"
	@echo " cd $(BUILD_DIR) ; source env.source ; bitbake openpli-enigma2-image"
	@echo

$(BBLAYERS):
	[ -d $@ ] || $(MAKE) $(MFLAGS) update

initialize: init

init: $(BBLAYERS) $(CONFFILES)

image: init update
	@sed -i "/oedrivers/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/md5sum/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/sha256sum/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/SRCDATE = /d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/driverdate/a SRCDATE = "'$(DATE)'"" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/NOTE/a SRC_URI = \"http://en2.ath.cx/release/images/oedrivers/bcmlinuxdvb_7335-\$$\{KV}-\$$\{SRCDATE}.tar.gz \\\\" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@echo "SRC_URI[md5sum] = "'$(CI_DRV_MD5SUM)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@echo "SRC_URI[sha256sum] = "'$(CI_DRV_SHA256SUM)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/inherit/d" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@sed -i "/x11-sato ssh-server-dropbear/a inherit image" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@echo 'Building image for $(MACHINE)'
	@. $(TOPDIR)/env.source && cd $(TOPDIR) && bitbake openpli-enigma2-image

ci-image: init update
	@sed -i "/oedrivers/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/md5sum/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/sha256sum/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/SRCDATE = /d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/driverdate/a SRCDATE = "'$(CI_DATE)'"" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/NOTE/a SRC_URI = \"http://en2.ath.cx/release/images/oedrivers/ci/bcmlinuxdvbci_7335-\$$\{KV}-\$$\{SRCDATE}.tar.gz \\\\" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@echo "SRC_URI[md5sum] = "'$(DRV_MD5SUM)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@echo "SRC_URI[sha256sum] = "'$(DRV_SHA256SUM)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/inherit/d" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@sed -i "/x11-sato ssh-server-dropbear/a inherit image" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@echo 'Building image for $(MACHINE)'
	@. $(TOPDIR)/env.source && cd $(TOPDIR) && bitbake openpli-enigma2-image

factory: init update
	@sed -i "/inherit/d" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@sed -i "/x11-sato ssh-server-dropbear/a inherit image-factory" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@echo 'Building image for $(MACHINE)'
	@. $(TOPDIR)/env.source && cd $(TOPDIR) && bitbake openpli-factory-image

update:
	@echo 'Updating Git repositories...'
	@HASH=`$(XSUM) $(MAKEFILE_LIST)`; \
	if [ -n "$(GIT_REMOTE)" ]; then \
		$(GIT) pull --ff-only || $(GIT) pull --rebase; \
	fi; \
	if [ "$$HASH" != "`$(XSUM) $(MAKEFILE_LIST)`" ]; then \
		echo 'Makefile changed. Restarting...'; \
		$(MAKE) $(MFLAGS) --no-print-directory $(MAKECMDGOALS); \
	else \
		$(GIT) submodule sync && \
		$(GIT) submodule update --init && \
		echo "The openpli OE is now up-to-date."; \
	fi

.PHONY: all image factory init initialize update usage

BITBAKE_ENV_HASH := $(call hash, \
	'BITBAKE_ENV_VERSION = "0"' \
	'CURDIR = "$(CURDIR)"' \
	)

$(TOPDIR)/env.source: $(DEPDIR)/.env.source.$(BITBAKE_ENV_HASH)
	@echo 'Generating $@'
	@echo 'export PATH=$(CURDIR)/openembedded-core/scripts:$(CURDIR)/bitbake/bin:$${PATH}' >> $@

OPENPLI_CONF_HASH := $(call hash, \
	'OPENPLI_CONF_VERSION = "1"' \
	'CURDIR = "$(CURDIR)"' \
	'BB_NUMBER_THREADS = "$(BB_NUMBER_THREADS)"' \
	'PARALLEL_MAKE = "$(PARALLEL_MAKE)"' \
	'DL_DIR = "$(DL_DIR)"' \
	'SSTATE_DIR = "$(SSTATE_DIR)"' \
	'TMPDIR = "$(TMPDIR)"' \
	)

$(TOPDIR)/conf/openpli.conf: $(DEPDIR)/.openpli.conf.$(OPENPLI_CONF_HASH)
	@echo 'Generating $@'
	@test -d $(@D) || mkdir -p $(@D)
	@echo 'SSTATE_DIR = "$(SSTATE_DIR)"' >> $@
	@echo 'TMPDIR = "$(TMPDIR)"' >> $@
	@echo 'BB_GENERATE_MIRROR_TARBALLS = "0"' >> $@
	@echo 'BBINCLUDELOGS = "yes"' >> $@
	@echo 'CONF_VERSION = "1"' >> $@
	@echo 'DISTRO = "openpli"' >> $@
	@echo 'EXTRA_IMAGE_FEATURES = "debug-tweaks"' >> $@
	@echo 'USER_CLASSES = "buildstats"' >> $@
	@if [ -f "$(CURDIR)/branding.conf" ]; then \
		echo "### BRANDING" >> $@; \
		cat $(CURDIR)/branding.conf >> $@; \
	fi

LOCAL_CONF_HASH := $(call hash, \
	'LOCAL_CONF_VERSION = "0"' \
	'CURDIR = "$(CURDIR)"' \
	'TOPDIR = "$(TOPDIR)"' \
	'MACHINE = "$(MACHINE)"' \
	)

$(TOPDIR)/conf/local.conf: $(DEPDIR)/.local.conf.$(MACHINE).$(LOCAL_CONF_HASH)
	@echo 'Generating $@'
	@test -d $(@D) || mkdir -p $(@D)
	@echo 'TOPDIR = "$(TOPDIR)"' > $@
	@echo 'MACHINE = "$(MACHINE)"' >> $@
	@echo 'require $(TOPDIR)/conf/openpli.conf' >> $@

$(TOPDIR)/conf/site.conf: $(CURDIR)/site.conf
	@ln -s ../../site.conf $@

$(CURDIR)/site.conf:
	@echo 'SCONF_VERSION = "1"' >> $@
	@echo 'BB_NUMBER_THREADS = "$(BB_NUMBER_THREADS)"' >> $@
	@echo 'PARALLEL_MAKE = "$(PARALLEL_MAKE)"' >> $@
	@echo 'BUILD_OPTIMIZATION = "-march=native -O2 -pipe"' >> $@
	@echo 'DL_DIR = "$(DL_DIR)"' >> $@
#	@echo 'INHERIT += "rm_work"' >> $@

BBLAYERS_CONF_HASH := $(call hash, \
	'BBLAYERS_CONF_VERSION = "0"' \
	'CURDIR = "$(CURDIR)"' \
	'BBLAYERS = "$(BBLAYERS)"' \
	)

$(TOPDIR)/conf/bblayers.conf: $(DEPDIR)/.bblayers.conf.$(MACHINE).$(BBLAYERS_CONF_HASH)
	@echo 'Generating $@'
	@test -d $(@D) || mkdir -p $(@D)
	@echo 'LCONF_VERSION = "4"' >> $@
	@echo 'BBFILES = ""' >> $@
	@echo 'BBLAYERS = "$(BBLAYERS)"' >> $@

$(CONFDEPS):
	@test -d $(@D) || mkdir -p $(@D)
	@$(RM) $(basename $@).*
	@touch $@

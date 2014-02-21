#!/usr/bin/make -f

# MACHINE examples: et4x00 et5x00 et6x00 et9x00 dm500hd dm800se dm7020hd dm8000 xp1000
MACHINE ?= ${subst /,,${subst build-,,${firstword ${dir ${wildcard build-*/}}}}}

ifeq "$(MACHINE)" ""
	MACHINE=mediabox
endif

# Adjust according to the number CPU cores to use for parallel build.
# Default: Number of processors in /proc/cpuinfo, if present, or 1.
NR_CPU := $(shell [ -f /proc/cpuinfo ] && grep -c '^processor\s*:' /proc/cpuinfo || echo 1)
BB_NUMBER_THREADS ?= $(NR_CPU)
PARALLEL_MAKE ?= -j $(NR_CPU)

# ci model different driver.
# date format : ex) 20130807
# md5sum format : ex) src_uri

DATE = "20140205"
DRV_MD5SUM = "7082de1dc64b4366dfae82c3b00cd71b"
DRV_SHA256SUM = "43709171e42a7be37212e473a7451e089b2386a5be86f5ed94d02d90cda073fc"

DATE_7335 = "20140221"
DRV_MD5SUM_7335 = "0a003b20e4bb16c5a8b49a1c374e66c6"
DRV_SHA256SUM_7335 = "0f9b3fd1eca4868fb7cea44c91d43c1ea2565b73ac68aff71c61ad4eb14811b9"

CI_DATE = "20140204"
CI_DRV_MD5SUM = "31f8fb613d8c6830fa68ed1967da708d"
CI_DRV_SHA256SUM = "c5e7a38c39b4e7dfe47ccd88a2495b795cab4b305876462698c7b3c242e2ebd8"

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
	@sed -i "/driverdate/a SRCDATE = "'$(DATE_7335)'"" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/NOTE/a SRC_URI = \"http://ilove.hobby-site.com/release/images/oedrivers/bcmlinuxdvb_7335-\$$\{KV}-\$$\{SRCDATE}.tar.gz \\\\" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@echo "SRC_URI[md5sum] = "'$(DRV_MD5SUM_7335)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@echo "SRC_URI[sha256sum] = "'$(DRV_SHA256SUM_7335)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/inherit/d" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@sed -i "/x11-sato ssh-server-dropbear/a inherit image" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@echo 'Building image for $(MACHINE)'
	@. $(TOPDIR)/env.source && cd $(TOPDIR) && bitbake openpli-enigma2-image

7356-image: init update
	@sed -i "/oedrivers/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules-7356.bb
	@sed -i "/md5sum/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules-7356.bb
	@sed -i "/sha256sum/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules-7356.bb
	@sed -i "/SRCDATE = /d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules-7356.bb
	@sed -i "/driverdate/a SRCDATE = "'$(DATE)'"" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules-7356.bb
	@sed -i "/NOTE/a SRC_URI = \"http://ilove.hobby-site.com/release/images/oedrivers/bcmlinuxdvb_7356-\$$\{KV}-\$$\{SRCDATE}.tar.gz \\\\" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules-7356.bb
	@echo "SRC_URI[md5sum] = "'$(DRV_MD5SUM)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules-7356.bb
	@echo "SRC_URI[sha256sum] = "'$(DRV_SHA256SUM)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules-7356.bb
	@sed -i "/inherit/d" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@sed -i "/x11-sato ssh-server-dropbear/a inherit image" $(CURDIR)/openembedded-core/meta/classes/core-image.bbclass
	@echo 'Building image for $(MACHINE)'
	@. $(TOPDIR)/env.source && cd $(TOPDIR) && bitbake openpli-enigma2-image-7356

ci-image: init update
	@sed -i "/oedrivers/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/md5sum/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/sha256sum/d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/SRCDATE = /d" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/driverdate/a SRCDATE = "'$(CI_DATE)'"" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@sed -i "/NOTE/a SRC_URI = \"http://ilove.hobby-site.com/release/images/oedrivers/ci/bcmlinuxdvbci_7335-\$$\{KV}-\$$\{SRCDATE}.tar.gz \\\\" $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@echo "SRC_URI[md5sum] = "'$(CI_DRV_MD5SUM)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
	@echo "SRC_URI[sha256sum] = "'$(CI_DRV_SHA256SUM)'"" >> $(CURDIR)/meta-openpli/recipes-bsp/technomate/technomate-dvb-modules.bb
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

.PHONY: all image init initialize update usage

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

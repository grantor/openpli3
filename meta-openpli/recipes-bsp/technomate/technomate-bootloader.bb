DESCRIPTION = " the tmtwin Universal Boot Loader"
SECTION = "tmtwin bootloaders"

# default license
require conf/license/openpli-gplv2.inc

#NOTE : SRCDATE divide to date

SRC_URI = "file://cfe-${MACHINE}.bin"

do_compile(){
#	mkdir -p ${DEPLOY_DIR_IMAGE}
#	cp ${WORKDIR}/cfe-${MACHINE}.bin .
	if [ ! -d "${DEPLOY_DIR_IMAGE}" ]; then
		mkdir -p ${DEPLOY_DIR_IMAGE}
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
	else
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
	fi
}

DESCRIPTION = " the tmtwin Universal bootlogo"
SECTION = "tmtwin bootlogo"

#default license
require conf/license/openpli-gplv2.inc

SRC_URI = "file://splash.bmp"

do_compile(){
#	mkdir -p ${DEPLOY_DIR_IMAGE}
#	cp ${WORKDIR}/splash.bmp .
	if [ ! -d "${DEPLOY_DIR_IMAGE}" ]; then
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
	else
		mkdir -p ${DEPLOY_DIR_IMAGE}
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
	fi
}

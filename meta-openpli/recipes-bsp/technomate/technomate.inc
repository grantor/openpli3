SRC_URI = "file://cfe-${MACHINE}.bin \
		   file://splash.bmp \
		   "

do_install() {
		if [ ! -d "${DEPLOY_DIR_IMAGE}" ]; then
			mkdir -p ${DEPLOY_DIR_IMAGE}
			cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
			cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		else
			cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
			cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		fi
}

DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20130719"
KV = "3.5.3"
PV = "${KV}+${SRCDATE}"
PR = "r0"

RCONFLICTS_${PN} = "technomate-dvb-modules"
RREPLACES_${PN} = "technomate-dvb-modules"

# do not delete under line.
# NOTE
SRC_URI = "http://en2.ath.cx/release/images/oedrivers/bcmlinuxdvb_7335-{KV}-{SRCDATE}.tar.gz \
		file://cfe-${MACHINE}.bin \
		file://splash.bmp \
		file://mediabox.splash.bmp \
		file://optimuss.splash.bmp \
"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    if [ ! -d "${DEPLOY_DIR_IMAGE}" ];then
        mkdir -p ${DEPLOY_DIR_IMAGE}
    fi
    install -d ${D}/lib/modules/${KV}/extra
    for f in lib/modules/${KV}/extra/*.ko; do
        install -m 0644 $f ${D}/$f;
		install -m 0644 ${WORKDIR}/$f ${DEPLOY_DIR_IMAGE}/
    done
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in `ls ${D}/lib/modules/${KV}/extra | grep \\.ko | sed -e 's/.ko//g'`; do
        echo $i _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done

	if [ ! -d "${DEPLOY_DIR_IMAGE}" ]; then
		mkdir -p ${DEPLOY_DIR_IMAGE}
	fi


	if [ "${MACHINE}" = "mediabox" ]; then
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		cp ${WORKDIR}/mediabox.splash.bmp ${DEPLOY_DIR_IMAGE}/mediabox.splash.bmp
		cp ${WORKDIR}/mediabox.splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
	elif [ "${MACHINE}" = "optimussos1" -o "${MACHINE}" = "optimussos2" ]; then
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/optimuss.splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		cp ${WORKDIR}/optimuss.splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.bmp
		cp ${WORKDIR}/optimuss.splash.bmp ${DEPLOY_DIR_IMAGE}/factory.bmp
	elif [ "${MACHINE}" = "ios100" -o "${MACHINE}" = "ios200" -o "${MACHINE}" = "ios300" ]; then
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.bmp
		cp ${WORKDIR}/factory.bmp ${DEPLOY_DIR_IMAGE}/
	else
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		cp ${WORKDIR}/mediabox.splash.bmp ${DEPLOY_DIR_IMAGE}/mediabox.splash.bmp
	fi
}

SRC_URI[md5sum] = "25d03a86fd60e831c63078aa071fb432"
SRC_URI[sha256sum] = "75634a204b66a1c5e5b9f46cf8a244e0703eef9ead2594f312716bd7c330c7a4"

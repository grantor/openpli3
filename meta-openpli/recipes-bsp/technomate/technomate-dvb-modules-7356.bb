DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20130912"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r0"

RCONFLICTS_${PN} = "technomate-dvb-modules-7356"
RREPLACES_${PN} = "technomate-dvb-modules-7356"

SRC_URI = "http://en2.ath.cx/release/images/oedrivers/bcmlinuxdvb_7356-${KV}-${SRCDATE}.tar.gz \
		file://cfe-${MACHINE}.bin \
		file://splash.bmp \
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
#		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.bmp
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/factory.bmp
	else
#		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.bmp
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/factory.bmp
	fi
}

SRC_URI[md5sum] = "2692ffc661a2d5af914dd56293465359"
SRC_URI[sha256sum] = "53a7e05b18d4afd3e367fc07c87200a2eb95cdb3811a54f325e9059b8d71fa75"

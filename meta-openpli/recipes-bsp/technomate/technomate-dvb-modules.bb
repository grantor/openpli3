DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20130620"
KV = "3.5.3"
PV = "${KV}+${SRCDATE}"
PR = "r0"

RCONFLICTS_${PN} = "technomate-dvb-modules"
RREPLACES_${PN} = "technomate-dvb-modules"

#SRC_URI = "http://opengit.homelinux.com/pub/OpenPLi3/drivers/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz 
SRC_URI = "http://en2.ath.cx/release/images/oedrivers/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz \
		file://cfe-${MACHINE}.bin \
		file://splash.bmp \
		file://mediabox.splash.bmp \
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
	else
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		cp ${WORKDIR}/mediabox.splash.bmp ${DEPLOY_DIR_IMAGE}/mediabox.splash.bmp
	fi
}

SRC_URI[md5sum] = "7e328c8bf7d7e17d3b2165fdbf7bcf04"
SRC_URI[sha256sum] = "ff35c45062086da0c1cd1ed0a69f04460505982680c63b17e57a1b169e182533"


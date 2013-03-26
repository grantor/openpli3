DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

SRCDATE = "20130326"
KV = "3.5.3"
PV = "${KV}+${SRCDATE}"
PR = "r0"

RCONFLICTS_${PN} = "technomate-dvb-modules"
RREPLACES_${PN} = "technomate-dvb-modules"

SRC_URI = "http://opengit.homelinux.com/pub/OpenPLi3/drivers/bcmlinuxdvb_7335-${KV}-${SRCDATE}.tar.gz \
		file://cfe-${MACHINE}.bin \
		file://splash.bmp \
"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    for f in lib/modules/${KV}/extra/*.ko; do
        install -m 0644 $f ${D}/$f;
    done
    install -d ${D}/${sysconfdir}/modules-load.d
    for i in `ls ${D}/lib/modules/${KV}/extra | grep \\.ko | sed -e 's/.ko//g'`; do
        echo $i _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    done
	if [ ! -d "${DEPLOY_DIR_IMAGE}" ]; then
		mkdir -p ${DEPLOY_DIR_IMAGE}
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
	else
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
	fi
}

SRC_URI[md5sum] = "431e49c406174683bfe18c753ba8e039"
SRC_URI[sha256sum] = "c6fa806bf11ca7b2983594dba113612072907497f6a42f812973e92e402e9929"

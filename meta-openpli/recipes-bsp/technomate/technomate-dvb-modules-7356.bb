DESCRIPTION = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

# Not Delete Under Line
# driverdate
SRCDATE = "20140128"
KV = "3.9.7"
PV = "${KV}+${SRCDATE}"
PR = "r0"

RCONFLICTS_${PN} = "technomate-dvb-modules-7356"
RREPLACES_${PN} = "technomate-dvb-modules-7356"

# do not delete under line.
# NOTE
SRC_URI = "http://ilove.hobby-site.com/release/images/oedrivers/bcmlinuxdvb_7356-${KV}-${SRCDATE}.tar.gz \
		file://cfe-${MACHINE}.bin \
		file://splash.bmp \
		file://optimuss.splash.bmp \
		file://optimuss.factory.bmp \
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

	if [ "${MACHINE}" = "optimussos1plus" -o "${MACHINE}" = "optimussos2plus" ]; then
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/optimuss.splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		cp ${WORKDIR}/optimuss.splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.bmp
		cp ${WORKDIR}/optimuss.factory.bmp ${DEPLOY_DIR_IMAGE}/factory.bmp
	else
		cp ${WORKDIR}/cfe-${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/cfe-${MACHINE}.bin
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.splash.bmp
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/${MACHINE}.bmp
		cp ${WORKDIR}/splash.bmp ${DEPLOY_DIR_IMAGE}/factory.bmp
	fi
}


SRC_URI[md5sum] = "46a72b481d936dab7cf730117886bdd0"
SRC_URI[sha256sum] = "dd8962281c6a673326aa37fb8a1a912f30095e43a6c082e622549710572f1abf"

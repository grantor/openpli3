DESCRIPTION = "Handle your EPG on enigma2 from various sources (opentv, mhw, xmltv, custom sources)"
HOMEPAGE = "https://github.com/E2OpenPlugins/e2openplugin-CrossEPG"
MODULE = "CrossEPG"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=4fbd65380cdd255951079008b364516c"
PLUGINPATH = "/usr/lib/enigma2/python/Plugins/SystemPlugins/${MODULE}"

DEPENDS += "libxml2 zlib python"

inherit gitpkgv
SRCREV = ""
PV = "0.6.2+git${SRCPV}"
PKGV = "0.6.2+git${GITPKGV}"
PR = "r0"

inherit python-dir

require openplugins.inc

SRC_URI += "file://crossepg_menu.py \
			file://plugin.py"

FILES_${PN} = "/usr/*"
FILES_${PN}-dbg += "/usr/crossepg/scripts/mhw2epgdownloader/.debug"

CFLAGS_append = " -I${STAGING_INCDIR}/libxml2/ -I${STAGING_INCDIR}/${PYTHON_DIR}/"

do_compile() {
	echo ${PKGV} > ${S}/VERSION
	oe_runmake SWIG="swig"
}

do_install() {
	oe_runmake 'D=${D}' install
}

do_install_append() {
	if [ "${MACHINE}" = "tmtwinoe" -o "${MACHINE}" = "tm2toe" -o "${MACHINE}" = "tmsingleoe" -o "${MACHINE}" = "ios100" -o "${MACHINE}" = "ios200" -o "${MACHINE}" = "ios300" -o "${MACHINE}" = "tmnanooe" ]; then
		install -m 0644 ${WORKDIR}/crossepg_menu.py ${D}${PLUGINPATH}/
		install -m 0644 ${WORKDIR}/plugin.py ${D}${PLUGINPATH}/
	fi
}

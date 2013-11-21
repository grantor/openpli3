MODULE = "AutoBouquets"
DESCRIPTION = "Control your receiver with a browser"
LICENSE = "GPLv2"

inherit gitpkgv
PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0.92"

DEPENDS += "enigma2"

SRC_URI = "git://github.com/pli3/autobouquet.git;protocol=git"

S="${WORKDIR}/git"

SRCREV_pn-${PN} ?= "${AUTOREV}"

PLUGINPATH = "/usr/lib/enigma2/python/Plugins/Extensions/${MODULE}"
do_install() {
	install -d ${D}${PLUGINPATH}
	cp -rp ${S}/plugin/* ${D}${PLUGINPATH}
}

FILES_${PN} = "${PLUGINPATH}"

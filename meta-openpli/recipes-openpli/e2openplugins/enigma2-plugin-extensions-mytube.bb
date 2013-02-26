MODULE = "MyTube"
DESCRIPTION = "Control your receiver with a mytube"
LICENSE = "GPLv2"

inherit gitpkgv
PV = "0.1+git${SRVPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0.83"

require openplugins.inc

# Just a quick hack to "compile" it
do_compile() {
}

PLUGINPATH = "/usr/lib/enigma2/python/Plugins/Extensions/${MODULE}"
do_install() {
	install -d ${D}${PLUGINPATH}
	cp -rp ${S}/plugin/* ${D}${PLUGINPATH}
}
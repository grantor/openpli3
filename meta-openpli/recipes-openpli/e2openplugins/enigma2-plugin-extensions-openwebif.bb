MODULE = "OpenWebif"
DESCRIPTION = "Control your receiver with a browser"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;firstline=10;lastline=12;md5=9c14f792d0aeb54e15490a28c89087f7"

DEPENDS = "python-cheetah-native"
RDEPENDS_${PN} = "python-cheetah python-json python-unixadmin python-misc python-pyopenssl aio-grab"

inherit gitpkgv
PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0.72"

require openplugins.inc

SRC_URI += "file://base.py \
			file://info.py \
			file://${MACHINE}.html \
			file://${MACHINE}.jpg \
			file://${MACHINE}.png \
			file://ajax.py \
"

# Just a quick hack to "compile" it
do_compile() {
	cheetah-compile -R --nobackup ${S}/plugin
	python -O -m compileall ${S}
}

PLUGINPATH = "/usr/lib/enigma2/python/Plugins/Extensions/${MODULE}"
do_install() {
	install -d ${D}${PLUGINPATH}
	cp -rp ${S}/plugin/* ${D}${PLUGINPATH}

	if [ "${MACHINE}" = "tmtwinoe" -o "${MACHINE}" = "tm2toe" -o "${MACHINE}" = "tmsingle" -o "${MACHINE}" = "tmnanooe" ]; then
		cp -rp ${WORKDIR}/ajax.py ${D}${PLUGINPATH}/controllers/
		cp -rp ${WORKDIR}/base.py ${D}${PLUGINPATH}/controllers/
		cp -rp ${WORKDIR}/info.py ${D}${PLUGINPATH}/controllers/models/
		cp -rp ${WORKDIR}/${MACHINE}.jpg ${D}${PLUGINPATH}/public/images/boxes/
		cp -rp ${WORKDIR}/${MACHINE}.png ${D}${PLUGINPATH}/public/images/remotes/
		cp -rf ${WORKDIR}/${MACHINE}.html ${D}${PLUGINPATH}/public/static/remotes/
		find ${D}${PLUGINPATH}/ -name '*.pyo' -exec rm {} \;
	fi
	if [ "${MACHINE}" = "ios100" -o "${MACHINE}" = "ios200" -o "${MACHINE}" = "ios300" -o "${MACHINE}" = "optimussos1" -o "${MACHINE}" = "optimussos2"]; then
		cp -rp ${WORKDIR}/ajax.py ${D}${PLUGINPATH}/controllers/
		cp -rp ${WORKDIR}/base.py ${D}${PLUGINPATH}/controllers/
		cp -rp ${WORKDIR}/info.py ${D}${PLUGINPATH}/controllers/models/
		cp -rp ${WORKDIR}/${MACHINE}.jpg ${D}${PLUGINPATH}/public/images/boxes/${MACHINE}hd.jpg
		cp -rp ${WORKDIR}/${MACHINE}.png ${D}${PLUGINPATH}/public/images/remotes/${MACHINE}hd.png
		cp -rf ${WORKDIR}/${MACHINE}.html ${D}${PLUGINPATH}/public/static/remotes/${MACHINE}hd.html
		find ${D}${PLUGINPATH}/ -name '*.pyo' -exec rm {} \;
	fi
	if [ "${MACHINE}" = "mediabox" ]; then
		cp -rp ${WORKDIR}/ajax.py ${D}${PLUGINPATH}/controllers/
		cp -rp ${WORKDIR}/base.py ${D}${PLUGINPATH}/controllers/
		cp -rp ${WORKDIR}/info.py ${D}${PLUGINPATH}/controllers/models/
		cp -rp ${WORKDIR}/${MACHINE}.jpg ${D}${PLUGINPATH}/public/images/boxes/Mediabox\ HD\ LX-1.jpg
		cp -rp ${WORKDIR}/${MACHINE}.png ${D}${PLUGINPATH}/public/images/remotes/
		cp -rf ${WORKDIR}/${MACHINE}.html ${D}${PLUGINPATH}/public/static/remotes/
		find ${D}${PLUGINPATH}/ -name '*.pyo' -exec rm {} \;
	fi
}

FILES_${PN} = "${PLUGINPATH}"

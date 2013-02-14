LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

# we cannot use PACKAGES_DYNAMIC = "enigma2-plugin-.*"  here, because enigma2-plugins already has it,
# so we only publish enigma2-plugin-pli-.* here (as a result, only those can occur in any RDEPENDS)

PACKAGES_DYNAMIC = "enigma2-plugin-pli-.*"

# add custom PROVIDES for plugins which do not match PACKAGES_DYNAMIC
PROVIDES += "enigma2-plugin-extensions-openuitzendinggemist enigma2-plugin-extensions-ushare"

DEPENDS = "enigma2 nfs-utils ushare"

DESCRIPTION_enigma2-plugin-extensions-ushare = "UPnP media server"
RDEPENDS_enigma2-plugin-extensions-ushare = "ushare"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r5"

#SRC_URI = "git://openpli.git.sourceforge.net/gitroot/openpli/enigma2-plugins;protocol=git \
#		   file://pythonpaths.patch \

SRC_URI = "git://github.com/pli3/plugins-enigma2.git;protocol=git \
		   file://pythonpaths.patch \
			"

S = "${WORKDIR}/git"

inherit autotools

EXTRA_OECONF = "--with-boxtype=${MACHINE} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR}"

python populate_packages_prepend () {

	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)

	do_split_packages(d, enigma2_plugindir, '(.*?/.*?)/.*', 'enigma2-plugin-%s', '%s ', recursive=True, match_path=True, prepend=True, extra_depends = "enigma2")

	# we have to perform some tricks to get non-standard files in the plugin packages,
	# unfortunately FILES_append doesn't work
	def files_append(pn, newfiles):
		files = bb.data.getVar('FILES_' + pn, d, 1)
		if files:
			files += " " + newfiles + " "
			bb.data.setVar('FILES_' + pn, files, d)
}

do_install_append() {
	find ${D}/usr/lib/enigma2/python/ -name '*.pyc' -exec rm {} \;
}


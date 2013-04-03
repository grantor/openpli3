DESCRIPTION = "Configuration files for 3rd-party feeds"
PR = "r0"

require conf/license/openpli-gplv2.inc

FEEDS = "3rd-party"

do_compile() {
    mkdir -p ${S}/${sysconfdir}/opkg
	if [ "${MACHINE}" = "tmtwinoe" -o "${MACHINE}" = "tm2toe" -o "${MACHINE}" = "tmsingle" -o "${MACHINE}" = "tmnanooe" -o "${MACHINE}" = "ios100" -o "${MACHINE}" = "ios200" -o "${MACHINE}" = "ios300" ]; then
		for feed in ${FEEDS}; do
			echo "src/gz ${DISTRO_FEED_PREFIX}-${feed} ${DISTRO_FEED_URI_TM}/${feed}" > ${S}/${sysconfdir}/opkg/${feed}-feed.conf
		done
	else
		for feed in ${FEEDS}; do
			echo "src/gz ${DISTRO_FEED_PREFIX}-${feed} ${DISTRO_FEED_URI}/${feed}" > ${S}/${sysconfdir}/opkg/${feed}-feed.conf
	    done
	fi
}
do_install () {
        install -d ${D}${sysconfdir}/opkg
        install -m 0644 ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

CONFFILES_${PN} += '${@ " ".join( [ ( "${sysconfdir}/opkg/%s-feed.conf" % feed ) for feed in "${FEEDS}".split() ] ) }'

DESCRIPTION = "Enigma2 is an experimental, but useful framebuffer-based frontend for DVB functions"
MAINTAINER = "OpenPLi team <info@openpli.org>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = " \
	ethtool \
	freetype \
	gettext-native \
	gst-plugins-base gstreamer \
	hotplug-e2-helper \
	jpeg \
	libdreamdvd libdvbsi++ libfribidi libmad libpng libsigc++-1.2 libungif libxml2 libxmlccwrap \
	openssl \
	python python-imaging python-twisted python-wifi \
	swig-native \
	tuxtxt-enigma2 \
	"

RDEPENDS_${PN} = " \
	alsa-conf \
	enigma2-fonts \
	ethtool \
	glibc-gconv-iso8859-15 \
	${PYTHON_RDEPS} \
	"

RRECOMMENDS_${PN} = " \
	enigma2-plugin-skins-pli-hd \
	gst-plugin-subsink \
	${GST_BASE_RDEPS} \
	${GST_GOOD_RDEPS} \
	${GST_BAD_RDEPS} \
	${GST_UGLY_RDEPS} \
	"

PYTHON_RDEPS = " \
	python-codecs \
	python-core \
	python-crypt \
	python-fcntl \
	python-lang \
	python-netclient \
	python-netserver \
	python-pickle \
	python-re \
	python-shell \
	python-threading \
	python-twisted-core \
	python-twisted-web \
	python-utf8-hack \
	python-xml \
	python-zlib \
	python-zopeinterface \
	"

GST_BASE_RDEPS = " \
	gst-plugins-base-alsa \
	gst-plugins-base-app \
	gst-plugins-base-audioconvert \
	gst-plugins-base-audioresample \
	gst-plugins-base-decodebin \
	gst-plugins-base-decodebin2 \
	gst-plugins-base-ogg \
	gst-plugins-base-playbin \
	gst-plugins-base-subparse \
	gst-plugins-base-typefindfunctions \
	gst-plugins-base-vorbis \
	"

GST_GOOD_RDEPS = " \
	gst-plugins-good-apetag \
	gst-plugins-good-audioparsers \
	gst-plugins-good-autodetect \
	gst-plugins-good-avi \
	gst-plugins-good-flac \
	gst-plugins-good-flv \
	gst-plugins-good-icydemux \
	gst-plugins-good-id3demux \
	gst-plugins-good-isomp4 \
	gst-plugins-good-matroska \
	gst-plugins-good-rtp \
	gst-plugins-good-rtpmanager \
	gst-plugins-good-rtsp \
	gst-plugins-good-souphttpsrc \
	gst-plugins-good-udp \
	gst-plugins-good-wavparse \
	"

GST_BAD_RDEPS = " \
	gst-plugins-bad-cdxaparse \
	gst-plugins-bad-mms \
	gst-plugins-bad-mpegdemux \
	gst-plugins-bad-rtmp \
	gst-plugins-bad-vcdsrc \
	"

GST_UGLY_RDEPS = " \
	gst-plugins-ugly-amrnb \
	gst-plugins-ugly-amrwbdec \
	gst-plugins-ugly-asf \
	gst-plugins-ugly-cdio \
	gst-plugins-ugly-dvdsub \
	gst-plugins-ugly-mad \
	gst-plugins-ugly-mpegaudioparse \
	gst-plugins-ugly-mpegstream \
	"

# DVD playback is integrated, we need the libraries
RDEPENDS_${PN} += "libdreamdvd"
RRECOMMENDS_${PN} += "libdvdcss"

# We depend on the font which we use for TXT subtitles (defined in skin_subtitles.xml)
RDEPENDS_${PN} += "font-valis-enigma"

RDEPENDS_${PN} += "${@base_contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)}"

DEMUXTOOL ?= "replex"

DESCRIPTION_append_enigma2-plugin-extensions-cutlisteditor = "enables you to cut your movies."
RDEPENDS_enigma2-plugin-extensions-cutlisteditor = "aio-grab"
DESCRIPTION_append_enigma2-plugin-extensions-graphmultiepg = "shows a graphical timeline EPG."
DESCRIPTION_append_enigma2-plugin-extensions-pictureplayer = "displays photos on the TV."
DESCRIPTION_append_enigma2-plugin-systemplugins-frontprocessorupdate = "keeps your frontprocessor up to date."
DESCRIPTION_append_enigma2-plugin-systemplugins-positionersetup = "helps you installing a motorized dish."
DESCRIPTION_append_enigma2-plugin-systemplugins-satelliteequipmentcontrol = "allows you to fine-tune DiSEqC-settings."
DESCRIPTION_append_enigma2-plugin-systemplugins-satfinder = "helps you to align your dish."
DESCRIPTION_append_enigma2-plugin-systemplugins-skinselector = "shows a menu with selectable skins."
DESCRIPTION_append_enigma2-plugin-systemplugins-videomode = "selects advanced video modes"
RDEPENDS_enigma2-plugin-systemplugins-nfiflash = "python-twisted-web"
RDEPENDS_enigma2-plugin-systemplugins-softwaremanager = "python-twisted-web"
DESCRIPTION_append_enigma2-plugin-systemplugins-crashlogautosubmit = "automatically send crashlogs to Dream Multimedia"
RDEPENDS_enigma2-plugin-systemplugins-crashlogautosubmit = "python-twisted-mail python-twisted-names python-compression python-mime python-email"
DESCRIPTION_append_enigma2-plugin-systemplugins-cleanupwizard = "informs you on low internal memory on system startup."
DESCRIPTION_append_enigma2-plugin-extensions-modem = "opens a menu to connect to internet via builtin modem."
RDEPENDS_enigma2-plugin-extensions-modem = "dreambox-modem-ppp-scripts"
DESCRIPTION_append_enigma2-plugin-systemplugins-wirelesslan = "helps you configuring your wireless lan"
RDEPENDS_enigma2-plugin-systemplugins-wirelesslan = "wpa-supplicant wireless-tools python-wifi"
DESCRIPTION_append_enigma2-plugin-systemplugins-networkwizard = "provides easy step by step network configuration"
# Note that these tools lack recipes
RDEPENDS_enigma2-plugin-extensions-dvdburn = "dvd+rw-tools dvdauthor mjpegtools cdrkit python-imaging ${DEMUXTOOL}"
RDEPENDS_enigma2-plugin-systemplugins-hotplug = "hotplug-e2-helper"

inherit gitpkgv

PV = "2.7+git${SRCPV}"
PKGV = "2.7+git${GITPKGV}"
PR = "r42"

ENIGMA2_BRANCH ?= "master"
#SRC_URI = "git://openpli.git.sourceforge.net/gitroot/openpli/enigma2;protocol=git;branch=${ENIGMA2_BRANCH}"
SRC_URI = " git://github.com/pli3/enigma2.git;protocol=git;branch=${ENIGMA2_BRANCH} \
		    file://keymap.xml \
			file://enigma2_end.sh \
			file://enigma2_pre_start.sh \
			file://enigma2.sh \
			file://restore.sh \
			file://var \
			file://def_ins \
			file://input_rcold-configured.png \
			file://input_rcold.png \
			file://menu-${MACHINE}.xml \
			file://setup.xml \
		   "

S = "${WORKDIR}/git"

FILES_${PN} += "${datadir}/keymaps"
FILES_${PN}-meta = "${datadir}/meta"
PACKAGES =+ "${PN}-src"
PACKAGES += "${PN}-meta"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit autotools pkgconfig

PACKAGES =+ "enigma2-fonts"
PV_enigma2-fonts = "2010.11.14"
PR_enigma2-fonts = "r0"
PKGV_enigma2-fonts = "${PV_enigma2-fonts}"
FILES_enigma2-fonts = "${datadir}/fonts"

EXTRA_OECONF = "\
	--with-libsdl=no --with-boxtype=${MACHINE} \
	--enable-dependency-tracking \
	${@base_contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "colorlcd", "--with-colorlcd" , "", d)} \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

# Swig generated 200k enigma.py file has no purpose for end users
FILES_${PN}-dbg += "\
	/usr/lib/enigma2/python/enigma.py \
	"

# some plugins contain so's, their stripped symbols should not end up in the enigma2 package
FILES_${PN}-dbg += "\
	/usr/lib/enigma2/python/Plugins/*/*/.debug \
	"

# Save some space by not installing sources (mytest.py must remain)
FILES_${PN}-src = "\
	/usr/lib/enigma2/python/GlobalActions.py \
	/usr/lib/enigma2/python/Navigation.py \
	/usr/lib/enigma2/python/NavigationInstance.py \
	/usr/lib/enigma2/python/RecordTimer.py \
	/usr/lib/enigma2/python/ServiceReference.py \
	/usr/lib/enigma2/python/SleepTimer.py \
	/usr/lib/enigma2/python/e2reactor.py \
	/usr/lib/enigma2/python/keyids.py \
	/usr/lib/enigma2/python/keymapparser.py \
	/usr/lib/enigma2/python/skin.py \
	/usr/lib/enigma2/python/timer.py \
	/usr/lib/enigma2/python/*/*.py \
	/usr/lib/enigma2/python/*/*/*.py \
	/usr/lib/enigma2/python/*/*/*/*.py \
	"

RADIOMVI = "radio-hd.mvi"

## only technomate model excute "do_configure_prepend"
do_configure_prepend() {
	if [ "${MACHINE}" = "tmtwinoe" -o "${MACHINE}" = "tm2toe" -o "${MACHINE}" = "tmsingleoe" -o "${MACHINE}" = "tmnanooe" -o "${MACHINE}" = "ios100" -o "${MACHINE}" = "ios200" -o "${MACHINE}" = "ios300" ]; then
		cp ${WORKDIR}/keymap.xml ${S}/data
		cp ${WORKDIR}/menu-${MACHINE}.xml ${S}/data/menu.xml
	fi
}

do_openpli_preinstall() {
	ln -f ${S}/data/${RADIOMVI} ${S}/data/radio.mvi
	install -d ${D}${sysconfdir}/enigma2
}

addtask openpli_preinstall after do_compile before do_install

do_openpli_branding() {
	if [ -n "${BRANDINGDIR}" -a -d "${BRANDINGDIR}/enigma2" ] ; then
		cp -p ${BRANDINGDIR}/enigma2/* ${S}/data/.
	fi
	if [ -n "${CRASHADDR}" ] ; then
		sed "s/^#define CRASH_EMAILADDR .*/#define CRASH_EMAILADDR \"${CRASHADDR}\"/" ${S}/main/bsod.cpp > ${S}/main/bsod.cpp.new && \
		mv ${S}/main/bsod.cpp.new ${S}/main/bsod.cpp
	fi
}

addtask openpli_branding after do_unpack before do_configure

do_install_append() {
	install -d ${D}/usr/share/keymaps
	find ${D}/usr/lib/enigma2/python/ -name '*.pyc' -exec rm {} \;
	if [ "${MACHINE}" = "tmtwinoe" -o "${MACHINE}" = "tm2toe" -o "${MACHINE}" = "tmsingleoe" -o "${MACHINE}" = "tmnanooe" -o "${MACHINE}" = "ios100" -o "${MACHINE}" = "ios200" -o "${MACHINE}" = "ios300" ]; then
		install -d 0755 ${D}/usr/bin/
		install -d 0755 ${D}/etc/tuxbox/
		install -d 0755 ${D}/var/
		install -d 0755 ${D}/usr/lib/
		install -d 0755 ${D}/usr/share/enigma2/skin_default/icons/
		install -m 0755 ${WORKDIR}/enigma2_end.sh ${D}/usr/bin/
		install -m 0755 ${WORKDIR}/enigma2_pre_start.sh ${D}/usr/bin/
		install -m 0755 ${WORKDIR}/enigma2.sh ${D}/usr/bin/
		install -m 0755 ${WORKDIR}/input_rcold.png ${D}/usr/share/enigma2/skin_default/icons/
		install -m 0755 ${WORKDIR}/input_rcold-configured.png ${D}/usr/share/enigma2/skin_default/icons/
		install -m 0755 ${WORKDIR}/setup.xml ${D}/usr/share/enigma2/
		ln -s /usr/bin/opkg ${D}/usr/bin/ipkg
		ln -s /etc/tuxbox ${D}/var/tuxbox
		ln -s /var/lib/opkg ${D}/usr/lib/ipkg
		cp ${WORKDIR}/var ${D}/etc/var.tar
		tar xf ${WORKDIR}/def_ins -C ${WORKDIR}/
		mv ${WORKDIR}/def_inst ${D}/etc/.def_inst
	fi
}

python populate_packages_prepend() {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
}

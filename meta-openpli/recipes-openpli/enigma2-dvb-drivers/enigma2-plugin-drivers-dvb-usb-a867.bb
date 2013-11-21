DESCRIPTION = "USB DVB driver for Afatech 867 chipset"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-a867 \
	"

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"

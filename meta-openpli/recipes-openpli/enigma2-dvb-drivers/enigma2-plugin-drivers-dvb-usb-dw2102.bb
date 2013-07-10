DESCRIPTION = "USB DVB driver for DW210x/DW310x chipset"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	firmware-dvb-usb-s660 \
	firmware-dvb-usb-dw2102 \
	firmware-dvb-usb-dw2104 \
	"

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"

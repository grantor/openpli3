require linux-firmware.inc

DESCRIPTION = "Firmware for Afatech AF9035 04"

SRCREV = "13f0b6bda7b567d29c747196aa65ad82b18651ca"

do_install() {
	install -d ${D}${base_libdir}/firmware
	install -m 0644 dvb-usb-af9035-04.fw ${D}${base_libdir}/firmware
}

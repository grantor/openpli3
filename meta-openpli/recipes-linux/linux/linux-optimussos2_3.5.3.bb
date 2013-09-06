require linux-tmxxoe-3.5.3.inc

SRC_URI += " \
		file://001_fix_standby_error_${MACHINE}.patch;striplevel=1 \
		file://dvb-usb-a867.patch;striplevel=1 \
		file://dvb-usb-rtl2832.patch;striplevel=1 \
		"


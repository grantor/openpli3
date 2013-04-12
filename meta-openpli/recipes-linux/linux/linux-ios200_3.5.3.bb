require linux-tmxxoe-3.5.3.inc

SRC_URI += " \
		file://001_fix_standby_error_${MACHINE}.patch;striplevel=1 \
		file://002_fix_partition_${MACHINE}.patch \
		"


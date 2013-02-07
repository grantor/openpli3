FILESEXTRAPATHS := "${THISDIR}/${PN}"

PRINC := "${@int(PRINC) + 4}"

inherit systemd

SRC_URI += "file://lighttpd.service"

SYSTEMD_PACKAGES = "${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd = "lighttpd.service"

MODULE = "PPanel"
DESCRIPTION = "PPanel"

require openplugins-replace-pli.inc

require openplugins-distutils.inc

SRC_URI += " file://CCcam.xml \
			 file://PPanel_tutorial.xml \
			 "
do_install_append() {
	install -d 0755 ${D}/etc/ppanels/
	install -m 0755 ${WORKDIR}/CCcam.xml ${D}/etc/ppanels/
	install -m 0755 ${WORKDIR}/PPanel_tutorial.xml ${D}/etc/ppanels/
}
require assume-gplv2.inc

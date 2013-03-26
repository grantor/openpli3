DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.5.3"

SRCDATE = "20121208"
MACHINE_KERNEL_PR_append = ".5"

SRC_URI[md5sum] = "536456dd7b0f797bf8ca50694991def1"
SRC_URI[sha256sum] = "f9068ef4b22f94f39d56ca8209ad61998160d9914aff18d5da2e3ba4890a4c55"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://en2.ath.cx/release/images/iqon/dev/linux-${KV}-${SRCDATE}.tar.gz \
        file://nfs-max-rwsize-8k.patch \
        file://${MACHINE}_defconfig"

S = "${WORKDIR}/linux-${KV}"

inherit kernel

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

do_configure_prepend() {
    oe_machinstall -m 0644 ${WORKDIR}/${MACHINE}_defconfig ${S}/.config
    oe_runmake oldconfig
}

kernel_do_install_append() {
    ${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
}

pkg_postinst_kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/mtd6 0 0
            nandwrite -p /dev/mtd6 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}

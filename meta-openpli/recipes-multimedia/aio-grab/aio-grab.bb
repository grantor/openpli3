DESCRIPTION="AiO screensho"
#t grabber"
MAINTAINER = "PLi team"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "jpeg libpng zlib"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://openpli.git.sourceforge.net/gitroot/openpli/aio-grab;protocol=git"
#SRC_URI = "git://github.com/pli3/aio-grab.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

SUMMARY = "decoding library for DTS Coherent Acoustics streams"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
#SRCREV = "4f6b5d6e6fb35994c342de95bf05e20117da549b"
SRCREV = "ad1babfb5f6684d3e00ad864ef304c55d03e6ed7"
PR = "r1"

#SRC_URI = "git://anonscm.debian.org/pkg-multimedia/${PN}.git;protocol=git"
SRC_URI = "git://github.com/pli3/pkg-multimedia.${PN}.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools lib_package pkgconfig

do_unpackpost() {
        QUILT_PATCHES=debian/patches quilt push -a
        # single precision is enough and speeds up libdca by about 10-15%
        sed -i -e 's/double/sample_t/g' ${S}/libdca/*.c ${S}/libdca/*.h
}

addtask unpackpost after do_unpack before do_patch

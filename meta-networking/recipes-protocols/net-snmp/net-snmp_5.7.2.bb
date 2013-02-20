#
# Copyright (C) 2012 Wind River Systems, Inc.
#
require net-snmp.inc
PR = "${INC_PR}.0"
LIC_FILES_CHKSUM = "file://README;beginline=3;endline=8;md5=7f7f00ba639ac8e8deb5a622ea24634e"

SRC_URI = "${SOURCEFORGE_MIRROR}/net-snmp/net-snmp-${PV}.tar.gz \
        file://init \
        file://snmpd.conf \
        file://snmptrapd.conf \
        file://compat-libnl-1.1.patch \
        file://fixpaths.patch   \
        file://net-snmp-5.6.1-ln-mysql.patch \
        file://net-snmp-config  \
        file://net-snmp-config.h \
        file://net-snmp-fix-double-free.patch \
        file://net-snmp-perl-target-location.patch \
        file://nodpkg.patch   \
        file://rpm519.patch \
	file://fix-libtool-finish.patch "




EXTRA_OECONF += "--disable-embedded-perl --with-perl-modules=no"
EXTRA_OECONF += "--with-mib-modules=smux"
EXTRA_OEMAKE = "INSTALL_PREFIX=${D}"

do_configure_prepend() {
        gnu-configize -f
        # We better change sources and re-autoconf here, but
        # required autoconf is too new for us.
        sed -e '/echo.*\".*\\\\.*\"/s/echo/echo -e/g' \
            -e 's/tail -1/tail -n 1/g'                \
            -i configure
}

PARALLEL_MAKE = ""
CCACHE = ""

SRC_URI[md5sum] = "5bddd02e2f82b62daa79f82717737a14"
SRC_URI[sha256sum] = "09ed31b4cc1f3c0411ef9a16eff79ef3b30d89c32ca46d5a01a41826c4ceb816"

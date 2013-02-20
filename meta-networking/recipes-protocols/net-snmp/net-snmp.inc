#
# Copyright (C) 2012 Wind River Systems, Inc.
#
DESCRIPTION = "Various tools relating to the Simple Network Management Protocol"
HOMEPAGE = "http://www.net-snmp.org/"
LICENSE = "BSD"

DEPENDS = "openssl"
INC_PR = "r1"
RDEPENDS_${PN} = "${PN}-utils"


inherit autotools update-rc.d siteinfo

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OECONF = "--enable-shared --disable-manuals --with-defaults \
${@base_conditional('SITEINFO_ENDIANNESS', 'le', '--with-endianness=little', '--with-endianness=big', d)}"

do_install () {
	oe_runmake install
}

do_install_append() {
	install -d ${D}${sysconfdir}/snmp
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/snmpd
	install -m 644 ${WORKDIR}/snmpd.conf ${D}${sysconfdir}/snmp/
	install -m 644 ${WORKDIR}/snmptrapd.conf ${D}${sysconfdir}/snmp/

	install -d ${STAGING_BINDIR_CROSS}
	install -m 0755 ${D}${bindir}/net-snmp-config ${STAGING_BINDIR_CROSS}/
	sed -e "s@-I/usr/include@@g" \
		-e "s@^prefix=.*@prefix=${STAGING_DIR_HOST}@g" \
		-e "s@^exec_prefix=.*@exec_prefix=${STAGING_DIR_HOST}@g" \
		-e "s@^includedir=.*@includedir=${STAGING_INCDIR}@g" \
		-e "s@^libdir=.*@libdir=${STAGING_LIBDIR}@g" \
		-i ${STAGING_BINDIR_CROSS}/net-snmp-config

	#install self tests
	install -d ${D}${TEST_LOCATION}
	for i in ${S}/dist ${S}/include ${S}/mibs ${S}/configure\
		${S}/net-snmp-config ${S}/testing; do
		if [ -e "$i" ]; then
			cp -a "$i" ${D}${TEST_LOCATION}
		fi
	done

	rmdlist="${D}${TEST_LOCATION}/dist/net-snmp-solaris-build"
	for i in $rmdlist; do
		if [ -d "$i" ]; then
			rm -rf "$i"
		fi
	done
}

PACKAGES = "${BPN} ${BPN}-doc ${BPN}-dev ${BPN}-dbg ${BPN}-libs \
            ${BPN}-utils ${BPN}-staticdev ${BPN}-tests"

FILES_${PN}-staticdev = "${libdir}/*.a"
FILES_${PN}-libs = "${libdir}/libnetsnmp*.so.*"
FILES_${PN} = "${sbindir}/* ${sysconfdir}"
FILES_${PN}-utils = "${bindir}/* ${datadir}/snmp/"
FILES_${PN}-dbg += "${libdir}/.debug/ ${sbindir}/.debug/ ${bindir}/.debug/"
FILES_${PN}-dev += "${bindir}/net-snmp-config ${bindir}/mib2c ${bindir}/mib2c-update"

TEST_LOCATION     = "/opt/${PN}"
FILES_${PN}-tests = "${TEST_LOCATION}/"

CONFFILES_${PN} = "${sysconfdir}/snmp/snmpd.conf \
                   ${sysconfdir}/snmp/snmptrapd.conf"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "snmpd"
INITSCRIPT_PARAMS_${PN} = "start 90 2 3 4 5 . stop 60 0 1 6 ."

LEAD_SONAME = "libnetsnmp.so"

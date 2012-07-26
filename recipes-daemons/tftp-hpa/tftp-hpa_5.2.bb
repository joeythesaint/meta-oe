SUMMARY        = "The client for the Trivial File Transfer Protocol"
DESCRIPTION    = \
"The Trivial File Transfer Protocol (TFTP) is normally used only for \
booting diskless workstations.  The tftp package provides the user   \
interface for TFTP, which allows users to transfer files to and from a \
remote machine.  This program and TFTP provide very little security, \
and should not be enabled unless it is expressly needed."
DEPENDS = "tcp-wrappers readline"
SECTION = "network"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://MCONFIG.in;md5=4b8f1605c10b64d96a205946b6c55445"
PR = "r1"


SRC_URI = "http://kernel.org/pub/software/network/tftp/tftp-hpa/tftp-hpa-${PV}.tar.bz2; "
SRC_URI += "file://tftp-0.40-remap.patch"
SRC_URI += "file://tftp-0.42-tftpboot.patch"
SRC_URI += "file://tftp-0.49-chk_retcodes.patch"
SRC_URI += "file://tftp-0.49-cmd_arg.patch"
SRC_URI += "file://tftp-hpa-0.39-tzfix.patch"
SRC_URI += "file://tftp-hpa-0.49-fortify-strcpy-crash.patch"
SRC_URI += "file://tftp-hpa-0.49-stats.patch"
SRC_URI += "file://tftp-hpa-5.2-pktinfo.patch"
SRC_URI += "file://tftp-xinetd"

EXTRA_OECONF += "--disable-option-checking"

inherit autotools

do_configure() {
	oe_runconf
}

do_install() {
       oe_runmake install INSTALLROOT=${D}
       mv ${D}${bindir}/tftp ${D}${bindir}/tftp-hpa
       mv ${D}${sbindir}/in.tftpd ${D}${sbindir}/in.tftpd-hpa

       install -m 755 -d ${D}${localstatedir}/lib/tftpboot/
       install -d ${D}${sysconfdir}/xinetd.d
       install -m 0755 ${WORKDIR}/tftp-xinetd ${D}${sysconfdir}/xinetd.d/tftp-hpa
}

FILES_${PN} = "${bindir}"

PACKAGES += "tftp-hpa-server"
SUMMARY_tftp-hpa-server = "The server for the Trivial File Transfer Protocol"
FILES_tftp-hpa-server = "${sbindir} ${sysconfdir} ${localstatedir}"

SRC_URI[md5sum] = "46c9bd20bbffa62f79c958c7b99aac21"
SRC_URI[sha256sum] = "0a9f88d4c1c02687b4853b02ab5dd8779d4de4ffdb9b2e5c9332841304d1a269"

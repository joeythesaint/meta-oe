inherit systemd

SRC_URI += " file://systemd-support.patch"

EXTRA_OECONF += " --with-systemd "

SYSTEMD_PACKAGES = "${PN}-server-snmpd-systemd \
                    ${PN}-server-snmptrapd-systemd"

SYSTEMD_SERVICE_${PN}-server-snmpd-systemd = "snmpd.service"
SYSTEMD_SERVICE_${PN}-server-snmptrapd-systemd =  "snmptrapd.service"

RDEPENDS_${PN}-server-snmpd-systemd = "${PN}-server-snmpd"
RDEPENDS_${PN}-server-snmptrapd-systemd = "${PN}-server-snmptrapd"

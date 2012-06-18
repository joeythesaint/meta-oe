inherit systemd

SYSTEMD_PACKAGES = "${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd = "ntpd.service"

do_install_append {
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/ntpdate.service ${D}${systemd_unitdir}/system/
	install -m 0644 ${WORKDIR}/ntpd.service ${D}${systemd_unitdir}/system/
}

PACKAGES =+ "${PN}-systemd"

FILES_${PN}-systemd = "${systemd_unitdir}/system/"
RDEPENDS_${PN}-systemd = "${PN}"


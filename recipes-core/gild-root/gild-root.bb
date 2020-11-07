SUMMARY = "Pre-init to mount root on overlayfs"

HOMEPAGE = "https://github.com/jmesmon/gild-root"
LICENSE = "AGPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=9cfc0f54f874c2d248027e4d3debfa06"

SRC_URI = "git://github.com/jmesmon/${BPN}.git;protocol=https"
PV = "0.0.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

DEPENDS = "virtual/libc"

B = "${WORKDIR}/build"

inherit meson

rootlibexecdir = "${root_prefix}/lib"
EXTRA_OEMESON += "\
	-Drootlibexecdir=${rootlibexecdir} \
	-Dversion-tag=${PV} \
	-Db_sanitize=none \
	"

FILES_${PN} += "\
        ${rootlibexecdir}/${BPN} \
"

ALTERNATIVE_TARGET[init] = "${root_libexecdir}/${BPN}/gild-root"
ALTERNATIVE_LINK_NAME[init] = "${base_sbindir}/init"
ALTERNATIVE_PRIORITY[init] ?= "1"

SUMMARY = "Pre-init to mount root on overlayfs"

HOMEPAGE = "https://github.com/jmesmon/gild-root"
LICENSE = "AGPLv3+"
LIC_FILES_CHKSUM = "file://README;md5=69103195141d1727fb36cbeeb783b30f"

SRC_URI = "git://github.com/jmesmon/${BPN}.git;protocol=https"
PV .= "+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

DEPENDS = "virtual/libc tup-native"

B = "${WORKDIR}/build"

do_compile() {
	rm -rf "${B}"/*
	cp -a "${S}"/* "${B}"
	cd "${B}"
	sed -i "${B}/generate-version" \
		-e "s:version=.*:version=$PV:"
	sed -i "${B}/Tuprules.tup" \
		-e "s:CC = cc:CC = ${CC} ${CFLAGS} ${LDFLAGS}:" \
		-e "s:-fsanitize=address::" \
		-e "s:-fsanitize=undefined::"
	tup generate build.sh
	./build.sh
}

root_libexecdir = "${root_prefix}/lib"
do_install() {
        install -d "${D}${root_libexecdir}"
        install -m755 "${BPN}" "${D}${root_libexecdir}"
}

FILES_${PN} += "\
        ${root_libexecdir}/${BPN} \
"

ALTERNATIVE_TARGET[init] = "${root_libexecdir}/${BPN}"
ALTERNATIVE_LINK_NAME[init] = "${base_sbindir}/init"
ALTERNATIVE_PRIORITY[init] ?= "1"


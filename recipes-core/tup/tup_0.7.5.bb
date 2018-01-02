SUMMARY = "A file-based build system"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "fuse"
SRC_URI[md5sum] = "fc249684928a8db118c8ba54d71a5d8d"
SRC_URI[sha256sum] = "361b3e069308ce1d9505d1cb927999ac448811a3425c724123e0c48602a9d1e4"
SRC_URI = "https://github.com/gittup/tup/archive/v${PV}.tar.gz"

B = "${WORKDIR}/build"

do_patch[postfuncs] += "do_patch_version"

do_patch_version() {
	# Avoid invoking `git` to find version, use ours
	sed -i ${S}/Tupfile \
		-e 's;`git describe`;v'"${PV};"
}

do_compile() {
	rm -rf "${B}"/*
	cp -r ${S}/* "${B}"

	sed -i "${B}/Tuprules.tup" \
		-e "s:CC = gcc:CC = ${CC} ${CFLAGS} ${LDFLAGS}:" \
		-e "s:ar crs:${AR} crs:"

	"${B}/build.sh"
}

do_install() {
	install -d "${D}${bindir}"
	install -m 0755 "${B}/build/tup" "${D}${bindir}/tup"
}

BBCLASSEXTEND = "native"

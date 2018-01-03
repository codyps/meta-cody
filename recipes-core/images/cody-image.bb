inherit core-image

LICENSE = "AGPLv3"

IMAGE_INSTALL += "\
	gild-root \
"

IMAGE_ROOTFS_SIZE ?= "16384"

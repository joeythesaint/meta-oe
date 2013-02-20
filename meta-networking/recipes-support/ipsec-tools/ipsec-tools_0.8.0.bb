#
# Copyright (C) 2012 Wind River Systems, Inc.
#
PR = "${INC_PR}.0"

require ipsec-tools.inc

SRC_URI[md5sum] = "b79aae3055a51f8de5c0f1b8ca6cf619"
SRC_URI[sha256sum] = "2359a24aa8eda9ca7043fc47950c8e6b7f58a07c5d5ad316aa7de2bc5e3a8717"
LIC_FILES_CHKSUM = "file://src/setkey/setkey.c;beginline=5;endline=32;md5=7577c1fc45b0c97ce01a25ac0df8ae86"

EXTRA_OECONF += " --disable-security-context"

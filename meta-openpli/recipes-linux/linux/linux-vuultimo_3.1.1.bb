require linux-vuplus-3.1.1.inc

MACHINE_KERNEL_PR_append = "${PR_INC}.12"

SRC_URI += "\
	file://linux-sata_brcm.patch;striplevel=1 \
	file://nfs-max-rwsize-8k.patch \
	"


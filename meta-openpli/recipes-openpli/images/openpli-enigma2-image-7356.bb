require openpli-image.bb

WIFI_DRIVERS = " \
	firmware-carl9170 \
	firmware-htc7010 \
	firmware-htc9271 \
	firmware-rt2870 \
	firmware-rt73 \
	firmware-rtl8712u \
	firmware-zd1211 \
	\
	kernel-module-r8712u \
	kernel-module-rt2500usb \
	kernel-module-rt2800usb \
	kernel-module-rt73usb \
	\
	firmware-carl9170 \
	firmware-af9005 \
	firmware-as102-data1-st \
	firmware-as102-data2-st \
	firmware-atheros-ar9271 \
	firmware-drx397xd.a2 \
	firmware-drx397xd.b1 \
	firmware-dvb-fe-af9013 \
	firmware-dvb-fe-ds3000 \
	firmware-dvb-fe-tda10071 \
	firmware-dvb-fe-xc5000-1.6.114 \
	firmware-dvb-nova-12mhz-b0 \
	firmware-dvb-siano \
	firmware-dvb-fe-xc5000-1.1 \
	firmware-dvb-fe-xc5000-1.6.114 \
	firmware-dvb-usb-af9035-01 \
	firmware-dvb-usb-bluebird-01 \
	firmware-dvb-usb-bluebird-02 \
	firmware-dvb-usb-dib0700-01 \
	firmware-dvb-usb-dib0700-03-pre1 \
	firmware-dvb-usb-dib0700-1.10 \
	firmware-dvb-usb-dib0700-1.20 \
	firmware-dvb-usb-dibusb-5.0.0.11 \
	firmware-dvb-usb-dibusb-6.0.0.8 \
	firmware-dvb-usb-dibusb-an2235-01 \
	firmware-dvb-usb-digitv-02 \
	firmware-dvb-usb-digivox-02 \
	firmware-dvb-usb-dposh-01 \
	firmware-dvb-usb-dtt200u-01 \
	firmware-dvb-usb-it9135 \
	firmware-dvb-usb-it913x \
	firmware-dvb-usb-megasky-02 \
	firmware-dvb-usb-s660 \
	firmware-dvb-usb-umt-010-02 \
	firmware-dvb-usb-wt220u-02 \
	firmware-dvb-usb-wt220u-fc03 \
	firmware-dvb-usb-wt220u-miglia-01 \
	firmware-dvb-usb-wt220u-zl0353-01 \
	firmware-htc7010 \
	firmware-htc9271 \
	firmware-isdbt-nova-12mhz-b0 \
	firmware-marvell-sd8688 \
	firmware-rt2870 \
	firmware-rt73 \
	firmware-rtl8712u \
	firmware-sms1xxx-hcw-114xxx-cmmb-01 \
	firmware-sms1xxx-hcw-55xxx-dvbt-01 \
	firmware-sms1xxx-hcw-55xxx-dvbt-02 \
	firmware-sms1xxx-hcw-55xxx-dvbt-03 \
	firmware-sms1xxx-hcw-55xxx-isdbt-02 \
	firmware-sms1xxx-hcw-55xxx-isdbt-03 \
	firmware-sms1xxx-nova-a-dvbt-01 \
	firmware-sms1xxx-nova-b-dvbt-01 \
	firmware-xc3028-dvico-au-01 \
	firmware-xc3028l-v36 \
	firmware-xc3028-v27 \
	firmware-zd1211 \
	"

RRECOMMENDS = " \
	dvb-usb-drivers-meta \
"

ENIGMA2_PLUGINS = " \
	enigma2-plugin-extensions-autobouquets \
	enigma2-plugin-extensions-cooltvguide \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-extensions-graphmultiepg \
	enigma2-plugin-extensions-mediascanner \
	enigma2-plugin-extensions-openwebif \
	enigma2-plugin-extensions-permanenttimeshift \
	enigma2-plugin-extensions-ppanel \
	enigma2-plugin-extensions-shoutcast \
	\
	enigma2-plugin-pli-softcamsetup \
	\
	enigma2-plugin-systemplugins-fastscan \
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-systemplugins-networkbrowser \
	enigma2-plugin-systemplugins-positionersetup \
	enigma2-plugin-systemplugins-satfinder \
	enigma2-plugin-systemplugins-skinselector \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-videomode \
	enigma2-plugin-systemplugins-videotune \
	\
	${@base_contains("MACHINE_FEATURES", "dvb-c", "enigma2-plugin-systemplugins-cablescan" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "frontprocessor", "enigma2-plugin-systemplugins-frontprocessorupgrade" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "hdmicec", "enigma2-plugin-systemplugins-hdmicec" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "osdposition", "enigma2-plugin-systemplugins-osdpositionsetup" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "wifi", "enigma2-plugin-systemplugins-wirelesslan", "", d)} \
	\
	${@base_contains('OPENPLI_FEATURES', 'ci', 'enigma2-plugin-systemplugins-commoninterfaceassignment', '', d)} \
	${@base_contains('OPENPLI_FEATURES', 'dvd', 'enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdplayer', '', d)} \
	"

DEPENDS += " \
	enigma2 \
	enigma2-pliplugins \
	enigma2-plugins \
	"

ENIGMA2_OPTIONAL = " \
	channelsettings-enigma2-meta \
	enigma2-pliplugins \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-extensions-ambx \
	enigma2-plugin-extensions-et-livestream \
	enigma2-plugin-extensions-openuitzendinggemist \
	enigma2-plugin-extensions-tuxcom \
	enigma2-plugin-extensions-tuxterm \
	enigma2-plugin-extensions-xmltvimport \
	enigma2-plugin-systemplugins-crossepg \
	enigma2-plugin-security-firewall \
	enigma2-plugin-skins-pli-hd \
	enigma2-plugins \
	enigma2-skins \
	picons-enigma2-meta \
	softcams-enigma2-meta \
	task-openplugins \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-satscan" , "", d)} \
	cdfs cdtextinfo \
	meta-enigma2-dvdburn \
	dvb-usb-drivers-meta \
	"

IMAGE_INSTALL += " \
	aio-grab \
	enigma2 \
	libavahi-client \
	settings-autorestore \
	tuxbox-common \
	${ENIGMA2_PLUGINS} \
	\
	${@base_contains("MACHINE_FEATURES", "tpm", "tpmd", "", d)} \
	${@base_contains("MACHINE_FEATURES", "wifi", "${WIFI_DRIVERS}", "", d)} \
	${@base_contains('MACHINE_FEATURES', 'pci', 'madwifi-ng madwifi-ng-modules', '',d)} \
	\
	${@base_contains('OPENPLI_FEATURES', 'dvd', 'cdfs cdtextinfo kernel-module-isofs kernel-module-udf', '', d)} \
	${@base_contains('OPENPLI_FEATURES', 'libpassthrough', 'libpassthrough', '', d)} \
	"

OPTIONAL_PACKAGES += " \
	${ENIGMA2_OPTIONAL} \
	"

export IMAGE_BASENAME = "openpli-enigma2"

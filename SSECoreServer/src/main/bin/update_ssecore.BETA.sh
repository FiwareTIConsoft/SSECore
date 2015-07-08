#!/bin/bash
export http_proxy=http://192.168.93.188:81/
export https_proxy=$http_proxy
curl "http://ca.tilab.com/builds/SSECore/last/ssecore-install-package.deb" > "/tmp/ssecore-install-package.deb"
dpkg -r ssecore
dpkg -i /tmp/ssecore-install-package.deb
echo "ssecore installed"

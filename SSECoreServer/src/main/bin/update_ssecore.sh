#!bin/bash
curl "http://ca.tilab.com/builds/SSEcore/last/ssecore-install-package.noarch.rpm" > "/tmp/ssecore-install-package.noarch.rpm"
rpm -ev ssecore-install-package.noarch
rpm -ivh /tmp/ssecore-install-package.noarch.rpm
echo "ssecore installed"

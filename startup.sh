#!/bin/sh

# Updating packages
sudo yum update -y

# Setting up the cli
PATH=/usr/bin:/usr/local/sbin:/sbin:/bin:/usr/sbin:/usr/local/bin:/opt/aws/bin:/root/bin

# Setting Java Environment for application to run.
wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm
sudo rpm -Uvh jdk-17_linux-x64_bin.rpm

# Commands to install Mysql and Make it a service
sudo yum install -y mariadb-server
sudo systemctl start mariadb 
sudo systemctl enable mariadb

# Creating database for application to connect.
myql --user=root <<_EOF_
CREATE DATABASE cyse2022;
_EOF_

# Command to run application makde it a Service to run on boot.
sudo mv /tmp/application_boot.service /etc/systemd/system/application_boot.service
sudo chmod 644 /etc/systemd/system/application_boot.service
systemctl enable reboot_message.service
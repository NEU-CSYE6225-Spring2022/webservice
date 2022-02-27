#!/bin/sh

# Function to print information related to current command
print_command_info () {
    echo $1
}

# Updating packages
print_command_info UPDATES-BEING-INSTALLED
sudo yum update -y

# Setting up the cli
print_command_info PATH-SET-LINUX
PATH=/usr/bin:/usr/local/sbin:/sbin:/bin:/usr/sbin:/usr/local/bin:/opt/aws/bin:/root/bin

# Setting Java Environment for application to run.
print_command_info WGET-COMMAND-RUN
wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm
print_command_info INSTALLING-JAVA
sudo rpm -Uvh jdk-17_linux-x64_bin.rpm

# Commands to install Mysql and Make it a service
print_command_info INSTALLING-MARIADB-SERVER
sudo yum install -y mariadb-server
print_command_info STARTING-MARIADB-SERVER
sudo systemctl start mariadb
print_command_info ENABLING-DEFAULT-BOOT-STARTUP-MARIADB
sudo systemctl enable mariadb

# Creating database for application to connect.
print_command_info CREATING-DATABSE-CYSE2022
mysql --user=root <<_EOF_
CREATE DATABASE cyse2022;
_EOF_

# Listing files in tmp folder
print_command_info LISTING-MP-FOLDER-FILES
sudo ls -l

# Command to run application makde it a Service to run on boot.
print_command_info MOVING-APPLICATION-BOOT-SERVICE
sudo mv /tmp/application_boot.service /etc/systemd/system/application_boot.service
print_command_info CHANGING-PERMISSIONS-OF-APPLICATION-BOOT-FILE
sudo chmod 644 /etc/systemd/system/application_boot.service
print_command_info ENABLING-BOOT-UP-FOR-SPRINGBOOT
sudo systemctl enable reboot_message.service
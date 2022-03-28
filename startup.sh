#!/bin/sh

# Function to print information related to current command
print_command_info () {
    echo $1
}

# Updating packages
print_command_info UPDATES-BEING-INSTALLED
sudo yum update -y

# Installing Ruby
print_command_info INSTALLING-RUBY
sudo yum install ruby

# Installing Wget
print_command_info INSTALLING-WGET
sudo yum install wget

# Removing Existing CodeDeploy Caches
print_command_info REMOVING-CODEDEPLOY-CACHES
sudo chmod 777 /tmp/codeDeployCacheRemove.sh
sh /tmp/codeDeployCacheRemove.sh
print_command_info PRINTING-CODEDEPLOY-CACHE-REMOVAL-STATUS
echo $?

# Setting up the cli
print_command_info PATH-SET-LINUX
PATH=/usr/bin:/usr/local/sbin:/sbin:/bin:/usr/sbin:/usr/local/bin:/opt/aws/bin:/root/bin

# Setting Java Environment for application to run.
print_command_info WGET-COMMAND-RUN
wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm
print_command_info INSTALLING-JAVA
sudo rpm -Uvh jdk-17_linux-x64_bin.rpm

# Installing Code Deploy Agent
print_command_info INSTALLING-CODE-DEPLOY-AGENT
cd /home/ec2-user
wget https://aws-codedeploy-us-east-1.s3.us-east-1.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
sudo service codedeploy-agent status
sudo service codedeploy-agent start
sudo service codedeploy-agent status

# Listing files in tmp folder
print_command_info LISTING-TMP-FOLDER-FILES
sudo ls -l /tmp

# Changing permissions for running springboot startup script
print_command_info CHANGING-PERMISSIONS-FOR-SPRINGBOOT-STARTUP-SCRIPT
sudo chmod 777 /tmp/springboot-startup.sh

# Command to run application make it a Service to run on boot.
print_command_info MOVING-APPLICATION-BOOT-SERVICE
sudo mv /tmp/application_boot.service /etc/systemd/system/application_boot.service
print_command_info CHANGING-PERMISSIONS-OF-APPLICATION-BOOT-FILE
sudo chmod 644 /etc/systemd/system/application_boot.service
print_command_info ENABLING-BOOT-UP-FOR-SPRINGBOOT
sudo systemctl enable application_boot.service
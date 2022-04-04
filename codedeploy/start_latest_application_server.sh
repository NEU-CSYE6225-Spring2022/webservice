#!/bin/sh

sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -m ec2 -a stop
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -c -file:/tmp/amazon-cloudwatch-agent.json -s
sudo systemctl start amazon-cloudwatch-agent.service

sudo chmod 777 /tmp/springdemo-0.0.1-SNAPSHOT.jar
sudo systemctl start application_boot.service
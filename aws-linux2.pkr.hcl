packer {
  required_plugins {
    amazon = {
      version = ">= 0.0.2"
      source  = "github.com/hashicorp/amazon"
    }
  }
}


source "amazon-ebs" "customWebApp" {
  ami_name               = "webApp-{{uuid}}"
  ami_description        = "This ami is custom used for deploying the Spring-Boot application of CSYE6225 with mysql inbuilt"
  instance_type          = "t2.micro"
  region                 = "us-east-1"
  skip_region_validation = true
  source_ami             = "ami-033b95fb8079dc481"
  ssh_username           = "ec2-user"
  ami_users              = ["757570737110"]
  force_deregister       = true
  force_delete_snapshot  = true
}

build {
  name = "learn-packer"
  sources = [
    "source.amazon-ebs.customWebApp"
  ]

  provisioner "shell" {
    inline = [
      "sleep 30"
    ]
  }

  provisioner "file" {
    source      = "springdemo/target/springdemo-0.0.1-SNAPSHOT.jar"
    destination = "/tmp/springdemo-0.0.1-SNAPSHOT.jar"
  }

  provisioner "file" {
    source      = "application_boot.service"
    destination = "/tmp/application_boot.service"
  }

  provisioner "file" {
    source      = "springboot-startup.sh"
    destination = "/tmp/springboot-startup.sh"
  }

  provisioner "file" {
    source   = "codeDeployCacheRemove.sh"
    destination = "/tmp/codeDeployCacheRemove.sh"
  }

  provisioner "shell" {
    script = "startup.sh"
  }

  post-processor "manifest" {
    output     = "manifest.json"
    strip_path = true
  }

  post-processor "shell-local" {
    inline = [
      "echo Done Building Image"
    ]
  }

}



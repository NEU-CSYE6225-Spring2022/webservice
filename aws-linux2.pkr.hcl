packer {
  required_plugins {
    amazon = {
      version = ">= 0.0.2"
      source  = "github.com/hashicorp/amazon"
    }
  }
}

source "amazon-ebs" "customWebApp" {
  ami_name               = "webApp"
  ami_description        = "This ami is custom used for deploying the Spring-Boot application of CSYE6225 with mysql inbuilt"
  instance_type          = "t2.micro"
  region                 = "us-east-1"
  skip_region_validation = true
  source_ami             = "ami-033b95fb8079dc481"
  ssh_username           = "ec2-user"
  ami_users              = ["757570737110"]
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



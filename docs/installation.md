# Installing AthleticStorm Locally

This guide will walk you through setting up and deploying the AthleticStorm application. You can either install and configure the application technologies manually or utilize [Docker](https://www.docker.com/) for easy deployment.

## Manual Configuration

This application runs using [Spring Boot](https://spring.io/projects/spring-boot) for the API, [MySQL](https://www.mysql.com/) for the database and [ReactJS](https://reactjs.org/) for the front-end.

### MySQL

First you will need to install the MySQL server for your computer. Once it's installed, you will need to first create a schema and user for the AthleticStorm application.

```
CREATE SCHEMA 'athleticstorm';
CREATE USER 'athleticstorm'@'localhost' IDENTIFIED WITH \
mysql_native_password BY 'mysqlpazz';
GRANT ALL PRIVILEGES ON athleticstorm.* TO 'athleticstorm'@'localhost';
```

The configuration of the tables will be created once the Spring Boot backend application is running.

### Spring Boot

To run the API, you will need to have the MySQL server running and configured. The Java version used is [Java 8](https://openjdk.java.net/install/). You will also need to have [Maven](https://maven.apache.org/index.html) installed. Navigate to the root folder of the back-end and execute the Maven build commands to build and test the application. Then you can run it from the command line as well.

```
> mvn clean install
> mvn spring-boot:run
```

### Front-End

The last thing to deploy is the front-end application. You will need to install [NodeJS](https://nodejs.org/en/). Navigate to the root of the front-end application and execute the NodeJS commands to build and run the applcation.

```
> npm install
> npm start
```

Once the application is running, you can access it at [localhost:3000](https://localhost:3000).

## Docker Deployment

You will need to install Docker and [Docker Compose](https://docs.docker.com/compose/). Once you have those installed, you can run the application from the root of the entire project.

```
> docker-compose up
```

# Deploying on a Server

The following is a guide on how to deploy an application to a Centos 7 Linux server.

## Install Docker

The first step in deploying the application on a server is to install Docker.

### Update Docker Package Database

In a terminal window, type:

```
> sudo yum check-update
```

Allow the operation to complete.

### Install the Dependencies
The next step is to download the dependencies required for installing Docker.

Type in the following command:

```
> sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```

The –y switch indicates to the yum installer to answer “yes” to any prompts that may come up. The yum-utils switch adds the yum-config-manager. Docker uses a device mapper storage driver, and the device-mapper-persistent-data and lvm2 packages are required for it to run correctly.

### Add the Docker Repository to CentOS
To install the edge or test versions of Docker, you need to add the Docker CE stable repository to your system. To do so, run the command:

```
> sudo yum-config-manager --add-repo \
https://download.docker.com/linux/centos/docker-ce.repo
```

A command for adding the Docker CE stable repository to CentOS 7.

A stable release is tested more thoroughly and has a slower update cycle. On the other hand, Edge release updates are more frequent but aren’t subject to as many stability tests.

### Install Docker On CentOS Using Yum
With everything set, you can finally move on to installing Docker on CentOS 7 by running:

```
sudo yum install docker
```

The system should begin the installation. Once it finishes, it will notify you the installation is complete and which version of Docker is now running on your system.

### Manage Docker Service
Although you have installed Docker on CentOS, the service is still not running.

To start the service, enable it to run at startup. Run the following commands in the order listed below.

Start Docker:

```
sudo systemctl start docker
```

Enable Docker:

```
sudo systemctl enable docker
```

Check the status of the service with:

```
sudo systemctl status docker
```

## Installing Kubernetes

Before installing Kubernetes you will need to have Docker installed.

### Configure Kubernetes Repository

Kubernetes packages are not available from official CentOS 7 repositories. Enter the following command to retrieve the Kubernetes repositories.

```
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://packages.cloud.google.com/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://packages.cloud.google.com/yum/doc/yum-key.gpg \
https://packages.cloud.google.com/yum/doc/rpm-package-key.gpg
EOF
```

### Install kubelet, kubeadm, and kubectl
These 3 basic packages are required to be able to use Kubernetes. Install the following package:

```
sudo yum install -y kubelet kubeadm kubectl
systemctl enable kubelet
systemctl start kubelet
```

You have now successfully installed Kubernetes, including its tools and basic packages.

### Set Hostname on Node

To give a unique hostname to your node, use this command:

```
sudo hostnamectl set-hostname athleticstorm
```

### Configure Firewall

The nodes, containers, and pods need to be able to communicate across the cluster to perform their functions. Firewalld is enabled in CentOS by default on the front-end. Add the following ports by entering the listed commands.

```
sudo firewall-cmd --permanent --add-port=6443/tcp
sudo firewall-cmd --permanent --add-port=2379-2380/tcp
sudo firewall-cmd --permanent --add-port=10250/tcp
sudo firewall-cmd --permanent --add-port=10251/tcp
sudo firewall-cmd --permanent --add-port=10252/tcp
sudo firewall-cmd --permanent --add-port=10255/tcp
sudo firewall-cmd –-reload
```

### Update Iptables Settings

Set the net.bridge.bridge-nf-call-iptables to ‘1’ in your sysctl config file. This ensures that packets are properly processed by IP tables during filtering and port forwarding.

```
cat  < /etc/sysctl.d/master_node_name
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF
sysctl --system
```

### Disable SELinux

The containers need to access the host filesystem. SELinux needs to be set to permissive mode, which effectively disables its security functions.

Use following commands to disable SELinux:

```
sudo setenforce 0
sudo sed -i ‘s/^SELINUX=enforcing$/SELINUX=permissive/’ /etc/selinux/config
```

### Disable SWAP

Lastly, we need to disable SWAP to enable the kubelet to work properly:

```
sudo sed -i '/swap/d' /etc/fstab
sudo swapoff -a
```

### Setup Pod Network

A Pod Network allows nodes within the cluster to communicate. This tutorial uses the flannel virtual network add-on for this purpose.

Install flannel with the command:

```
sudo kubectl apply -f 
https://raw.githubusercontent.com/ \
coreos/flannel/master/Documentation/kube-flannel.yml
```

### Create Cluster with kubeadm

For flannel to work correctly initialize a cluster by executing the following command:

```
sudo kubeadm init –pod-network-cidr=10.244.0.0/16
```

The process might take several minutes to complete based on network speed. Once this command finishes, it displays a kubeadm join message.

### Check Status of Cluster

Once a pod network has been installed, you can confirm that it is working by checking that the CoreDNS pod is running by typing:

```
sudo kubectl get pods --all-namespaces
```

### Manage Cluster as Regular User

Start using the cluster you need to run it as a regular user by typing:

```
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```

## Deploying the Application

First you will need to get the project onto the production server using [Git](https://git-scm.com/).

```
sudo yum install -y git
git clone https://gitlab.ecs.baylor.edu/aars/20200143c9-athleticstorm.git
```

You can then deploy the application using the Kustomization script in the deployments folder.

```
kubectl apply -k <install-of-athleticstorm>/deployments
```

## Configuring a Reverse Proxy with nginx

### Add Nginx Repository

To add the CentOS 7 EPEL repository, open terminal and use the following command:

```
sudo yum install epel-release
```

### Install Nginx

Now that the Nginx repository is installed on your server, install Nginx using the following yum command:

```
sudo yum install nginx
```

After you answer yes to the prompt, Nginx will finish installing on your virtual private server (VPS).

### Start Nginx

Nginx does not start on its own. To get Nginx running and configured as a service, type:

```
sudo systemctl enable nginx
sudo systemctl start nginx
```

If you are running a firewall, run the following commands to allow HTTP and HTTPS traffic:

```
sudo firewall-cmd --permanent --zone=public --add-service=http 
sudo firewall-cmd --permanent --zone=public --add-service=https
sudo firewall-cmd --reload
```

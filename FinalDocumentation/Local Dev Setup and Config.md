# **Local Dev Setup and Configuration Instructions**

This guide will walk you through setting up and deploying the Athletic Storm application. You can either install and configure the application technologies manually or utilize [Docker](https://www.docker.com/) for easy deployment.

Local Manual Configuration

The Athletic Storm application runs using [MySQL](https://www.mysql.com/) for the database, [Spring Boot](https://spring.io/projects/spring-boot) for the API and [ReactJS](https://reactjs.org/) for the front-end.

**MySQL**

First you will need to install the MySQL server for your computer. Once it is installed, you will need to first create a schema and user for the Athletic Storm application using the script below:

CREATE SCHEMA &#39;athleticstorm&#39;;

CREATE USER &#39;athleticstorm&#39;@&#39;localhost&#39; IDENTIFIED WITH \

mysql\_native\_password BY &#39;mysqlpazz&#39;;

GRANT ALL PRIVILEGES ON athleticstorm.\* TO &#39;athleticstorm&#39;@&#39;localhost&#39;;

_Note: According to various formats and configurations of MySQL, the apostrophe character ( __&#39;__ ) might not be needed surrounding certain keywords. Look to the documentation for your specific version of MySQL. If an error occurs, you might need to drop the schema and start over again (from creating the schema) to clear the error._

**Spring Boot**

To run the API, you will need to have the MySQL server running and configured using the steps above. The Java version Athletic Storm was designed on is [Java 8.](https://openjdk.java.net/install/) You will also need to have [Maven](https://maven.apache.org/index.html) installed to properly run this application. Navigate to the root folder of the back-end and execute the Maven build commands to build and test the application.

\&gt; mvn clean install

\&gt; mvn spring-boot:run

After doing this, you are able to run Athletic Storm from the command line.

**Front-End**

The last thing to deploy Athletic Storm is the front-end application. You will need to install [NodeJS](https://nodejs.org/en/). After it is properly installed, navigate to the root of the front-end application and execute the NodeJS commands below to build and run the application:

\&gt; npm install

\&gt; npm start

Once the application is running using the above commands, you are able to access it at [localhost:3000](https://localhost:3000/) in a web browser.

Local Docker Configuration

Instead of using the manual configuration as detailed above, you are able to use Docker.

First, you will need to install [Docker](https://www.docker.com/) and [Docker Compose.](https://docs.docker.com/compose/) Once you have both of those installed, you can run the application from the root of the entire project using the following command in the command line:

\&gt; docker-compose up

# **Server Deployment and Configuration Instructions**

The following is a guide on how to deploy the Athletic Storm application to a Centos 7 Linux Server.

Install Docker

The first step in deploying the Athletic Storm on a server is to install Docker.

**Update Docker Package Database**

In a terminal In a terminal window, type the following command:

\&gt; sudo yum check-update

Allow the operation to complete.

**Install the Dependencies**

The next step is to download the dependencies required for installing Docker. This is done by executing the following command:

\&gt; sudo yum install -y yum-utils device-mapper-persistent-data lvm2

_Note: The –y switch indicates to the yum installer to answer &quot;yes&quot; to any prompts that may come up. The yum-utils switch adds the yum-config-manager. Docker uses a device mapper storage driver, and the device-mapperpersistent-data and lvm2 packages are required for it to run correctly._

**Add the Docker Repository to CentOS**

To install the edge or test versions of Docker, you need to add the Docker CE stable repository to your system. To do this, execute the following command:

\&gt; sudo yum-config-manager --add-repo \ https://download.docker.com/linux/centos/docker-ce.repo

This command is used for adding the Docker CE stable repository to CentOS 7. A stable release is tested more thoroughly and has a slower update cycle. On the other hand, Edge release updates are more frequent but aren&#39;t subject to as many stability tests.

**Install Docker On CentOS Using Yum**

With everything set, you can finally move on to installing Docker on CentOS 7 by running the following command:

\&gt; sudo yum install docker

The system should begin the installation. Once it finishes, it will notify you the installation is complete and which version of Docker is now running on your system

**Manage Docker Service**

Although you have installed Docker on CentOS, the service is still not running. To start the service, enable it to run at startup. Run the following commands in the order listed below.

(1) Start Docker:

\&gt; sudo systemctl start docker

(2) Enable Docker:

\&gt; sudo systemctl enable docker

(3) Check the status of the service with:

\&gt; sudo systemctl status docker

Install Kubernetes

Before installing Kubernetes you will need to have Docker installed.

**Configure Kubernetes Repository**

Kubernetes packages are not available from official CentOS 7 repositories. Enter the following command to retrieve the Kubernetes repositories:

cat \&lt;\&lt;EOF \&gt; /etc/yum.repos.d/kubernetes.repo

[kubernetes]

name=Kubernetes baseurl=https://packages.cloud.google.com/yum/repos/kubernetes-el7-x86\_64

enabled=1

gpgcheck=1

repo\_gpgcheck=1 gpgkey=https://packages.cloud.google.com/yum/doc/yum-key.gpg \ https://packages.cloud.google.com/yum/doc/rpm-package-key.gpg EOF

**Install kubelet, kubeadm and kubectl**

These 3 basic packages are required to be able to use Kubernetes. Install these packages using the following commands:

\&gt; sudo yum install -y kubelet kubeadm kubectl

\&gt; systemctl enable kubelet

\&gt; systemctl start kubelet

You have now successfully installed Kubernetes including its tools and basic packages.

**Set Hostname on Node**

To give a unique hostname to your node, use the following command:

\&gt; sudo hostnamectl set-hostname athleticstorm

**Configure Firewall**

The nodes, containers, and pods need to be able to communicate across the cluster to perform their functions. Firewalld is enabled in CentOS by default on the front-end. Add the following ports by entering the listed commands.

\&gt; sudo firewall-cmd --permanent --add-port=6443/tcp

\&gt; sudo firewall-cmd --permanent --add-port=2379-2380/tcp

\&gt; sudo firewall-cmd --permanent --add-port=10250/tcp

\&gt; sudo firewall-cmd --permanent --add-port=10251/tcp

\&gt; sudo firewall-cmd --permanent --add-port=10252/tcp

\&gt; sudo firewall-cmd --permanent --add-port=10255/tcp

\&gt; sudo firewall-cmd –-reload

**Update Iptables Settings**

Set the net.bridge.bridge-nf-call-iptables to &#39;1&#39; in your sysctl config file. This ensures that packets are properly processed by IP tables during filtering and port forwarding.

cat \&lt; /etc/sysctl.d/master\_node\_name

net.bridge.bridge-nf-call-ip6tables = 1

net.bridge.bridge-nf-call-iptables = 1

EOF

sysctl --system

**Disable SELinux**

The containers need to access the host filesystem. SELinux needs to be set to permissive mode, which effectively disables its security functions. Use following commands to disable SELinux:

\&gt; sudo setenforce 0

\&gt; sudo sed -i &#39;s/^SELINUX=enforcing$/SELINUX=permissive/&#39; /etc/selinux/config

**Disable SWAP**

Lastly, we need to disable SWAP to enable the kubelet to work properly using the following commands:

\&gt; sudo sed -i &#39;/swap/d&#39; /etc/fstab

\&gt; sudo swapoff -a

**Setup Pod Network**

A Pod Network allows nodes within the cluster to communicate. This tutorial uses the flannel virtual network add-on for this purpose. Install flannel with the command:

\&gt; sudo kubectl apply -f https://raw.githubusercontent.com/ \ coreos/flannel/master/Documentation/kube-flannel.yml

**Create Cluster with kubeadm**

For flannel to work correctly initialize a cluster by executing the following command:

\&gt; sudo kubeadm init –pod-network-cidr=10.244.0.0/16

_Note: This process might take several minutes to completed based on network speed. Once this command finishes, it displays a kubeadm join message._

**Check Status of Cluster**

Once a pod network has been installed, you can confirm that it is working by checking that the CoreDNS pod is running by executing the following command:

\&gt; sudo kubectl get pods --all-namespaces

**Manage Cluster as Regular User**

Start using the cluster you need to run it as a regular user by entering the following commands:

\&gt; mkdir -p $HOME/.kube

\&gt; sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config

\&gt; sudo chown $(id -u):$(id -g) $HOME/.kube/config

Deploying the Application

First you will need to get the project onto the production server using [Git](https://git-scm.com/) by executing the following commands:

\&gt; sudo yum install -y git

\&gt; git clone https://gitlab.ecs.baylor.edu/aars/20200143c9-athleticstorm.git

You can then deploy the application using the Kustomization script in the deployments folder using the following command:

\&gt; kubectl apply -k \&lt;install-of-athleticstorm\&gt;/deployments

Configuring A Reverse Proxy With Nginx

**Add Nginx Repository**

To add the CentOS 7 EPEL repository, open terminal and use the following command:

\&gt; sudo yum install epel-release

**Install Nginx**

Now that the Nginx repository is installed on your server, install Nginx using the following yum command:

\&gt; sudo yum install nginx

After you answer yes to the prompt, Nginx will finish installing on your VPS (virtual private server).

**Start Nginx**

Nginx does not start on its own. To get Nginx running and configured as a service, execute the following commands:

\&gt; sudo systemctl enable nginx

\&gt; sudo systemctl start nginx

If you are running a firewall, run the following commands to allow HTTP and HTTPS traffic:

\&gt; sudo firewall-cmd --permanent --zone=public --add-service=http

\&gt; sudo firewall-cmd --permanent --zone=public --add-service=https

\&gt; sudo firewall-cmd --reload
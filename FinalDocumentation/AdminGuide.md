# **Server Deployment and Configuration Instructions**

The following is a guide on how to deploy the Athletic Storm application to a Centos 7 Linux Server.

Install Docker

The first step in deploying the Athletic Storm on a server is to install Docker.

**Update Docker Package Database**

In a terminal In a terminal window, type the following command:

>sudo yum check-update

Allow the operation to complete.

**Install the Dependencies**

The next step is to download the dependencies required for installing Docker. This is done by executing the following command:

>sudo yum install -y yum-utils device-mapper-persistent-data lvm2

_Note: The –y switch indicates to the yum installer to answer &quot;yes&quot; to any prompts that may come up. The yum-utils switch adds the yum-config-manager. Docker uses a device mapper storage driver, and the device-mapperpersistent-data and lvm2 packages are required for it to run correctly._

**Add the Docker Repository to CentOS**

To install the edge or test versions of Docker, you need to add the Docker CE stable repository to your system. To do this, execute the following command:

>sudo yum-config-manager --add-repo \ https://download.docker.com/linux/centos/docker-ce.repo

This command is used for adding the Docker CE stable repository to CentOS 7. A stable release is tested more thoroughly and has a slower update cycle. On the other hand, Edge release updates are more frequent but aren&#39;t subject to as many stability tests.

**Install Docker On CentOS Using Yum**

With everything set, you can finally move on to installing Docker on CentOS 7 by running the following command:

>sudo yum install docker

The system should begin the installation. Once it finishes, it will notify you the installation is complete and which version of Docker is now running on your system

**Manage Docker Service**

Although you have installed Docker on CentOS, the service is still not running. To start the service, enable it to run at startup. Run the following commands in the order listed below.

(1) Start Docker:

>sudo systemctl start docker

(2) Enable Docker:

>sudo systemctl enable docker

(3) Check the status of the service with:

>sudo systemctl status docker

**Run the Application**

If you are running a firewall, run the following commands to allow HTTP and HTTPS traffic:

>sudo firewall-cmd --permanent --zone=public --add-service=http

>sudo firewall-cmd --permanent --zone=public --add-service=https

>sudo firewall-cmd --reload

You can run the application by starting it with docker-compose

>docker-compose up

# **Port Information**

* 8443 - Spring-Boot backend on HTTPS

* 8080 - Spring-Boot backend on HTTP

* 80 - NodeJS frontend NGINX port for HTTP

* 443 - NodeJS frontend NGINX port for HTTPS

* 3306 - MYSQL Database

# **Default Values**

Admin Username - admin (configurable in the application.properties of the server)

Admin Password - password (configurable in the application.properties of the server)

Database Name - athleticstorm

Database Username - athleticstorm (configurable in the application.properties of the server)

Database Password - mysqlpazz (configurable in the application.properties of the server)

Keystore Name - athleticstorm.p12 (configurable in the application.properties of the server)

Keystore Password - Set at certificate creation, set in the server.ssl.key-store-password value in the application.properties of the server

# **SSL**

There are two places where SSL is used. The first is in the frontend. To start, you will need to generate a certificate using OpenSSL. You may configure this however you wish although these are the values we chose to use.

> req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -keyout athleticstorm.key -out athleticstorm.crt

Move the .key file into the /ssl/keys folder and the .crt to the /ssl/certs folder. You can configure these names and locations in the nginx.conf in the client folder.

Next you need to configure SSL for the backend. To start, generate a PKCS12 certificate using Java's keytool.

> keytool -genkeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore athleticstorm.p12 -validity 365

Move the .p12 file into the resources folder of the server. You may then configure the application.properties file for the correct filename (if it was changed from athleticstorm.p12) as well as the password (server.ssl.key-store-password).

# **Admin Actions**

The sole admin action is to refresh the data in the system which is done using the endpoint /api/admin/refresh
.SILENT:
	
all:
	cd slova && mvn clean package && cd ..
	sudo cp ./slova/target/slova.war /var/tomcat/webapps/slova.war

enter_db:
	mariadb -u library_user -p # password


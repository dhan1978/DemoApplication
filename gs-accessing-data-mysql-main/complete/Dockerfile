FROM tomcat:9.0
LABEL MAINTAINER Dhananjay
		# COPY path-to-your-application-war  path-to-webapps-in-docker-tomcat
ARG WAR_FILE=/home/dhananjay/accessing-data-postgresql-0.0.1-SNAPSHOT.war

COPY  accessing-data-postgresql-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]

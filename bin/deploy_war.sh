rq=`date +%Y%m%d`
project_war=/app/git/server/narcissus/bin/deploy_war.sh
tomcat_webapps=/app/tomcat/tomcat/webapps

cd ../service/target
cp ${project_war} /app/backup/server_narcissus.war.${rq}
cp ${project_war} ${tomcat_webapps}
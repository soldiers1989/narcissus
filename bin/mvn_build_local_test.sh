#cd /app/git/module/lotus
#git pull
#mvn clean install
cd /app/git/server/narcissus
git pull
mvn clean package -Dmaven.test.skip=true -P test


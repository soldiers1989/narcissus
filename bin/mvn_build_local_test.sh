cd /app/git/module/lotus
git fetch
mvn clean install
cd /app/git/server/narcissus
git fetch
mvn clean package

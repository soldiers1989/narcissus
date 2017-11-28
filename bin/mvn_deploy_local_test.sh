cd /app/git/module/lotus
git fetch
sh mvn clean install
cd /app/git/server/narcissus
git fetch
sh mvn clean package

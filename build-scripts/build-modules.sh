
echo "Building JAR files"
cd  EurekaServer
./mvnw clean package
cd ..

cd MovieService
./mvnw clean package
cd ..

cd WebClient
./mvnw clean package
cd ..

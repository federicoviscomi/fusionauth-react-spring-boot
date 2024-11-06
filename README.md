# fusionauth-react-spring-boot

* react ui
* fusionauth as authentication server
* spring boot backend as resource server

# how to run

Run fusionauth with the command
```bash
docker compose up
```

Run the server
```bash
cd resource-server/
./mvnw spring-boot:run
```

Run the ui
```bash
cd ../ui/
npm install
npm run dev
```

open http://localhost:3000/
login as 
user: user@example.com
password: password

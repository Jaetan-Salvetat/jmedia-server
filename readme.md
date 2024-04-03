# jMedia server

### Setup

#### environment variables

<span style="color:red">**Need to setup intellij with .env files**</span>

```dotenv
DEV_MODE=true
DB_URL="jdbc:postgresql://localhost:5432/jmedia"
DB_DRIVER="org.postgresql.Driver"
DB_USER="<user>"
DB_PASSWORD="<password>"
MINIO_ENDPOINT="<url>"
MINIO_ACCESS_KEY="<key>"
MINIO_SECRET_KEY="<key>"
```

#### MinIO

- Execute this command to setup a  MinIO environment 
```bash
docker-compose up
```
- Create an access key

the default username and password are **minio** and **password**
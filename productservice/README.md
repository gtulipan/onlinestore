# Product service microservice

### Environment variables:
PROJECT_ROOT=PROJECT_ROOT: the root folder of onlinestore project, sa. `/app`
PRODUCT_SERVICE_PORT: the application port, for example `8080`
LOGGING_LEVEL_ROOT: the logging level, sa. `DEBUG`, `INFO`, `WARN`, `ERROR`, `TRACE`, `OFF`, `FATAL`
LOG_FOLDER: the path to log file, sa. `/logs` or `${java.io.tmpdir}/${spring.application.name}`
DB_POSTGRESQL_HOST: the PostgreSQL host name, sa. `localhost`
DB_POSTGRESQL_PORT: the PostgreSQL port inside Docker container `5432`
DB_POSTGRESQL_PORT_EXTERNAL: the PostgreSQL port outside from Docker container, sa. `5432`
DB_POSTGRESQL_USER: the PostgreSQL database username, sa. `admin`
DB_POSTGRESQL_PASSWORD: the PostgreSQL database user password, sa. `PI3p14159265`
DB_POSTGRESQL_DATABASE_NAME: the PostgreSQL database name `products`
DB_POSTGRESQL_DATABASE_SCHEMA: the PostgreSQL database schema name `public`
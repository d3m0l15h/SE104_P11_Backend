# Champions League Management System

A comprehensive Champions League management system built using Spring Boot. This REST API allows for managing teams, players, matches, and more, making it ideal for creating and managing football tournaments.

## Features

- **Team Management:** Create, update, delete, and list teams.
- **Player Management:** Add players to teams and manage player details.
- **Match Scheduling:** Schedule matches and track results.
- **Leaderboard:** Generate and update leaderboards based on match outcomes.
- **Swagger UI:** Interactive API documentation for easy testing.
- **Docker Support:** Simplified deployment using Docker Compose.

## Prerequisites

- Java 17 or higher
- Maven 3.8 or higher
- Docker and Docker Compose
- MySQL installed locally (optional if not using Docker Compose)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/d3m0l15h/SE104_P11_Backend.git
cd SE104_P11_Backend
```

### 2. Configure the Application

Install MySQL database on your local machine or with `docker-compose.local.yml`:

```bash
docker compose -f docker-compose.local.yml up -d
```

remembered to config `MYSQL_ROOT_PASSWORD` in `docker-compose.local.yml`

Update the `application.properties` file located in the `src/main/resources` directory with your MySQL database credentials:

```properties
spring.datasource.url=${MYSQL_URL:jdbc:mysql://localhost:3306/QuanLyGiaiVoDichBongDa?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true}
spring.datasource.username=root
spring.datasource.password=your_database_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

If using Docker Compose, ensure the MySQL configuration matches the settings in `docker-compose.yml`.

#### Enviroment `.env` settings:

Create `.env` file in root of project by following the template below:

```.env
CLOUDINARY_URL=
CDN_CLOUD_NAME=
CDN_API_KEY=
CDN_API_SECRET=
CDN_UPLOAD_PRESET=
DOC_USERNAME=
DOC_PASSWORD=
```

### 3. Build and Run the Application

#### Without Docker:

```bash
mvn clean install
java -jar target/backend-api.jar
```

#### With Docker Compose:

```bash
docker-compose up --build
```

The application will be available at `http://localhost:8080`.

### 4. Access the Swagger UI

Navigate to `http://localhost:8080/swagger-ui/swagger-ui/index.html` to explore and test the API endpoints.

## API Endpoints

### Example Endpoints

| HTTP Method | Endpoint                  | Description                 |
|-------------|---------------------------|-----------------------------|
| GET         | /api/doi-bong             | Fetch all teams             |
| POST        | /api/doi-bong             | Create a new team           |
| GET         | /api/cau-thu              | Fetch all players           |
| POST        | /api/cau-thu              | Add a new player            |
| POST        | /api/lich-thi-dau         | Schedule a new match        |
| GET         | /api/bang-xep-hang        | Fetch the current leaderboard |

More endpoints are detailed in the Swagger UI.

## Dockerize

To dockerize project and push it into your Docker Registry using command:

```bash
mvn compile jib:build
```

or you can push it into your registry by following [this](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin)

## Deployment

### Deploy with Docker Compose

Ensure Docker and Docker Compose are installed, then run:

```bash
docker-compose up -d
```

### Cloudflare Integration

Set up a domain in Cloudflare and configure the DNS to point to your server. Ensure your API URL is secured with HTTPS by generating an SSL certificate or using Cloudflare's SSL service.

## Technologies Used

- **Spring Boot**
- **MySQL**
- **Docker**
- **Swagger UI**
- **Cloudflare**
- **Cloudinary**

## Troubleshooting

### Common Issues

1. **Database Connection Issues:**
   - Verify MySQL is running and the credentials are correct.
   - Check the network configuration if using Docker Compose.

2. **Swagger Not Loading:**
   - Ensure the application is running on the specified port.
   - Check the logs for errors related to `springdoc` or Swagger setup.

## Contributing

Feel free to fork this repository and make contributions. Please submit a pull request with detailed information about your changes.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

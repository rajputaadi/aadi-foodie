# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
./mvnw spring-boot:run                       # run the app (port 8080)
./mvnw compile                               # compile only
./mvnw test                                  # all tests
./mvnw test -Dtest=UserService               # one test class
./mvnw test -Dtest=UserService#testSaveUser  # one test method
./mvnw clean package                         # build jar into target/
```

Java 21, Spring Boot 4.1.0, MySQL. The app expects a local MySQL database named `aadi-foodie` (credentials in `src/main/resources/application.properties`, currently plaintext along with the Cloudinary keys).

## Current state

**`main` does not compile.** Three methods are stubbed with empty bodies mid-feature (the in-progress Cloudinary banner upload):

- `RestaurantController.uploadFile` — `controllers/RestaurantController.java`
- `RestaurantServiceImpl.uploadBanner` — `service/impl/RestaurantServiceImpl.java`
- `FileUploadServiceImpl.uploadFile` — `service/impl/FileUploadServiceImpl.java`

`src/test/java/.../services/UserService.java` also calls `userService.testUserRole()`, which does not exist on the `UserService` interface. Expect to fix these before any build succeeds.

`OrderController` is an empty shell. `dotenv-java` and the Cloudinary taglib are declared in `pom.xml` but unused.

## Architecture

Standard Spring layered REST service: `controllers` → `service` (interface) → `service/impl` → `repository` (Spring Data JPA) → `entity`. Controllers never touch entities; they exchange DTOs only.

Two conventions differ between the two features, and new code should follow the **Restaurant** side:

- **Entity↔DTO mapping**: `RestaurantServiceImpl` uses the `ModelMapper` bean from `config/ProjectConfig.java`. `UserServiceImpl` hand-writes `convertUserToUserDto`/`convertUserDtoToUser` instead — legacy, don't copy it.
- **IDs**: application-generated, not DB-generated. Both `User` and `Restaurant` use `String` `@Id` set in the service layer via `Helper.generateRandomId()` (UUID) before `save()`. Forgetting this on a new entity means a null-id insert.

Other cross-cutting pieces:

- **Pagination**: controllers build `Pageable` themselves from `page`/`size`/`sortBy`/`sortDir` request params (default sort `createdDate` desc) and pass it down; services return `Page<Dto>` via `page.map(...)`.
- **Errors**: `exception/GlobalExceptionHandler.java` (`@RestControllerAdvice`) is the single place response shapes are decided. Services throw `ResourceNotFoundException` from `.orElseThrow(...)`; the handler turns it into an `ErrorResponse` 404. Validation failures return a bare field→message `Map`, so error shapes are not uniform across handlers.
- **Roles are modelled twice**: a `Role` enum on `User.role` and a separate `RoleEntity` joined many-to-many through `user_role`. Both are live; nothing yet reconciles them.
- **`ddl-auto=update`** — the schema follows the entities, so entity edits mutate the dev database on next boot.
- **File uploads**: `FileService` is the abstraction, `FileUploadServiceImpl` the (unfinished) local-disk implementation writing to `uploads/`, while `config/CloudinaryConfig.java` wires a `Cloudinary` bean for the remote path. Which one wins is the open question in the current work.
